package com.challengealura.tomalibros.principal;

import com.challengealura.tomalibros.model.*;
import com.challengealura.tomalibros.repository.AutorRepository;
import com.challengealura.tomalibros.repository.LibroRepository;
import com.challengealura.tomalibros.service.ConsumoAPI;
import com.challengealura.tomalibros.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumeAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversion = new ConvierteDatos();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void showMenu() {
        //Menu de usuario
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
                    
                    Bienvenido a TomaLibros, a continuación seleccione una opción
                    
                    1 - Buscar libro.
                    2 - Lista de libros registrados.
                    3 - Lista de autores registrados.
                    4 - Lista de autores vivos por año.
                    5 - Lista de libros por idioma.
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        buscarYGuardarLibro();
                        break;
                    case 2:
                        listaDeLibros();
                        break;
                    case 3:
                        listaDeAutores();
                        break;
                    case 4:
                        listaDeAutoresVivosPorAnio();
                        break;
                    case 5:
                        listaDeLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación");
                        break;
                    default:
                        System.out.println("Opcion inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa una opción válida");
            }
        }
    }

    private Optional<DatosLibro> getDatosLibro(String nombreLibro) {
        try {
            var json = consumeAPI.obtainData(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
            var datosBusqueda = conversion.obtainData(json, ListaDeLibros.class);
            return datosBusqueda.results().stream().findFirst();
        } catch (Exception e) {
            System.out.println("Error al consultar la API : " + e.getMessage());
            return Optional.empty();
        }
    }

    private void buscarYGuardarLibro() {
        System.out.println("Ingrese el nombre del libro a buscar: ");
        var busqueda = scanner.nextLine();

        Optional<DatosLibro> libroEncontrado = getDatosLibro(busqueda);

        if (libroEncontrado.isPresent()) {
            DatosLibro libroDTO = libroEncontrado.get();

            Optional<Libro> libroExistente = libroRepository.findByTitleContainsIgnoreCase(libroDTO.title());
            if (libroExistente.isPresent()) {
                System.out.println("El libro " + libroExistente.get().getTitle() + "ya está registrado en la base de datos");
                return;
            }

            DatosAutor autorDTO = libroDTO.authors().getFirst();
            Autor autorEntity;

            Optional<Autor> autorExistente = autorRepository.findByNameContainingIgnoreCase(autorDTO.name());

            if (autorExistente.isPresent()) {
                autorEntity = autorExistente.get();
            } else {
                autorEntity = new Autor(autorDTO);
                autorRepository.save(autorEntity);
            }

            Libro nuevoLibro = new Libro(libroDTO);
            nuevoLibro.setAuthor(autorEntity);
            libroRepository.save(nuevoLibro);
            System.out.println("\n¡Libro guardado!");
            System.out.println(nuevoLibro);
        } else {
            System.out.println("No se ha encontrado ningún libro con este título, intenta nuevamente.");
        }
    }

    private void listaDeLibros() {
        //Obtener los libros del repositorio
        libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("\n--- LISTA DE LIBROS REGISTRADOS ---");
            libros.forEach(System.out::println);
        }
    }

    private void listaDeAutores() {
        //Obtener los autores del repositorio
        autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
        } else {
            System.out.println("\n--- LISTA DE AUTORES REGISTRADOS ---");
            autores.forEach(System.out::println);
        }
    }

    private void listaDeAutoresVivosPorAnio() {
        System.out.print("Ingresa el año para buscar autores vivos: ");
        try {
            var anio = Integer.parseInt(scanner.nextLine());
            List<Autor> autoresVivos = autorRepository.findAutoresAliveInYear(anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio + ".");
            } else {
                System.out.println("\n--- AUTORES VIVOS EN EL AÑO " + anio + " ---");
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Por favor, ingresa un año en formato numérico (ej: 1850).");
        }
    }

    private void listaDeLibrosPorIdioma() {
        System.out.println("""
                Escriba el idioma para buscar los libros:
                'es' para libros en español
                'en' para libros en inglés
                """);
        var idiomaSeleccionado = scanner.nextLine();

        //Consulta al repositorio usando derived query en LibroRepository
        List<Libro> librosEncontrados = libroRepository.findByLanguagesIgnoreCase(idiomaSeleccionado);

        if (librosEncontrados.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idiomaSeleccionado + "'.");
        } else {
            System.out.println("\n--- LIBROS EN IDIOMA: " + idiomaSeleccionado.toUpperCase() + " ---");
            librosEncontrados.forEach(System.out::println);
            long cantidad = librosEncontrados.stream().count();
            System.out.println("Se encontraron " + cantidad + " libros en este idioma.");
        }
    }
}