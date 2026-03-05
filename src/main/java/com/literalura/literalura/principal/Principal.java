package com.literalura.literalura.principal;

import com.literalura.literalura.model.*;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibroRepository;
import com.literalura.literalura.service.ConsumoAPI;
import com.literalura.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepo;
    private AutorRepository autorRepo;

    public Principal(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***************************************************
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ***************************************************
                    Elija la opción a través de su número:
                    """;
            System.out.println(menu);

            try {
                if (lectura.hasNextInt()) {
                    opcion = lectura.nextInt();
                    lectura.nextLine(); // Limpiar el buffer

                    switch (opcion) {
                        case 1 -> buscarLibroPorTitulo();
                        case 2 -> mostrarLibrosRegistrados();
                        case 3 -> mostrarAutoresRegistrados();
                        case 4 -> autoresVivosEnDeterminadoAnio();
                        case 5 -> buscarLibrosPorIdioma();
                        case 0 -> System.out.println("Cerrando la aplicación...");
                        default -> System.out.println("Opción inválida. Intente de nuevo.");
                    }
                } else {
                    System.out.println("Error: Debes ingresar un número entero.");
                    lectura.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = lectura.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (!datos.resultadoLibros().isEmpty()) {
            DatosLibro datosLibro = datos.resultadoLibros().get(0);

            // Manejo de Autor para evitar duplicados y cumplir relación por ID
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Autor autor = autorRepo.findByNombreContainsIgnoreCase(datosAutor.nombre())
                    .orElseGet(() -> autorRepo.save(new Autor(datosAutor)));

            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);

            try {
                libroRepo.save(libro);
                System.out.println("\n----- LIBRO ENCONTRADO Y GUARDADO -----");
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("\nAviso: Este libro ya se encuentra registrado.");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepo.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
        } else {
            System.out.println("\n--- LIBROS REGISTRADOS ---");
            libros.forEach(System.out::println);
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepo.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
        } else {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            autores.forEach(System.out::println);
        }
    }

    private void autoresVivosEnDeterminadoAnio() {
        System.out.println("Ingrese el año para buscar autores que estaban vivos:");
        var entrada = lectura.nextLine(); // Usamos nextLine para capturar todo tipo de entrada y validar

        try {
            var anio = Integer.parseInt(entrada); // Intentamos convertir a número para lidiar con valores inválidos

            // Validación de rango lógico (opcional pero recomendada para robustez)
            if (anio < 0 || anio > 2026) {
                System.out.println("Error: Por favor, ingrese un año válido.");
                return;
            }

            // Llamada a la Derived Query (pasando el año para ambos parámetros: nacimiento y fallecimiento)
            List<Autor> autores = autorRepo.findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(anio, anio);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                System.out.println("\n--- AUTORES VIVOS EN EL AÑO " + anio + " ---");
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            // Manejo de valores inválidos: si el usuario ingresa letras o símbolos
            System.out.println("Error: Debe ingresar un año en formato numérico (ejemplo: 1980).");
        }
    }
    private void buscarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        var idioma = lectura.nextLine();
        List<Libro> libros = libroRepo.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            // --- AQUÍ SE CUMPLE EL REQUERIMIENTO DE ESTADÍSTICAS Y STREAMS ---
            long cantidad = libros.stream().count();

            System.out.println("\n--- ESTADÍSTICAS POR IDIOMA ---");
            System.out.println("Idioma buscado: " + idioma);
            System.out.println("Cantidad de libros encontrados: " + cantidad);
            System.out.println("---------------------------------");

            libros.forEach(System.out::println);
        }
    }
}