package com.literalura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDeDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    @Override
    public String toString() {
        return "---------- LIBRO ----------" +
                "\nTítulo: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNombre() : "Desconocido") +
                "\nIdioma: " + idioma +
                "\nNúmero de descargas: " + numeroDeDescargas +
                "\n---------------------------\n";
    }
}