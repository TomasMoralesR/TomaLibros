package com.challengealura.tomalibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
    private String languages;
    private double downloadCount;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.title = datosLibro.title();
        this.languages = datosLibro.languages().getFirst();
        this.downloadCount = datosLibro.downloadCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Autor getAuthor() {
        return autor;
    }

    public void setAuthor(Autor autor) {
        this.autor = autor;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(double downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        String autorNombre = (autor != null) ? autor.getName() : "N/A";
        return """
           Título: %s
           Autor: %s
           Idioma: %s
           Descargas: %2f
           -----------------
           """.formatted(title, autorNombre, languages, downloadCount);
    }
}
