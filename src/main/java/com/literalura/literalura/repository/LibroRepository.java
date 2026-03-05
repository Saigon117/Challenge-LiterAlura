package com.literalura.literalura.repository;

import com.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Consulta para buscar libros por idioma
    List<Libro> findByIdioma(String idioma);
}