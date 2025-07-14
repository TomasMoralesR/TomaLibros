package com.challengealura.tomalibros.repository;

import com.challengealura.tomalibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitleContainsIgnoreCase(String busqueda);

    List<Libro> findByLanguagesIgnoreCase(String languages);
}
