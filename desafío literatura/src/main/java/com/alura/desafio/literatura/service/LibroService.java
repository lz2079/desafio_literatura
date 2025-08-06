package com.alura.desafio.literatura.service;

import com.alura.desafio.literatura.entity.Author;
import com.alura.desafio.literatura.entity.Libro;
import com.alura.desafio.literatura.model.Book;
import com.alura.desafio.literatura.model.GutendexResponse;
import com.alura.desafio.literatura.repository.AuthorRepository;
import com.alura.desafio.literatura.repository.LibroRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LibroService {


    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConvierteDatos convierteDatos;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // --- Metodos del servicio --- opción 1

    public void buscarLibroPorTitulo(String titulo) {

        String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + tituloCodificado;
        System.out.println("Buscando libro en la API Gutendex: " + url);

        try {

            GutendexResponse response = this.obtenerDatosDeAPI(url, GutendexResponse.class);


            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {

                Book libroEncontradoAPI = response.getResults().get(0);
                System.out.println("\n--- Libro encontrado en la API ---");
                System.out.println("Título: " + libroEncontradoAPI.getTitulo());
                System.out.println("ID Gutendex: " + libroEncontradoAPI.getId());


                Optional<Libro> libroExistenteOpt = libroRepository.findByIdGutendex(libroEncontradoAPI.getId());

                if (libroExistenteOpt.isPresent()) {

                    System.out.println("¡El libro ya está registrado en la base de datos!");
                    Libro libroExistente = libroExistenteOpt.get();
                    System.out.println("ID en BD: " + libroExistente.getId());
                    System.out.println("Título en BD: " + libroExistente.getTitulo());

                } else {

                    System.out.println("El libro NO está en la base de datos. Procediendo a guardarlo...");
                    this.guardarLibroYAutores(libroEncontradoAPI);
                }

            } else {

                System.out.println("No se encontraron libros con el título: \"" + titulo + "\"");
            }

        } catch (Exception e) {

            System.err.println("Ocurrió un error inesperado durante la búsqueda: " + e.getMessage());
            e.printStackTrace(); // Para depuración, puedes eliminarlo después
        }
    }

    // --- Metodos del servicio --- opción 2
    @Transactional
    public List<Libro> listarLibrosRegistrados() {
        try {
            System.out.println("\n--- Listado de Libros Registrados ---");


            List<Libro> libros = libroRepository.findAll();


            if (libros == null || libros.isEmpty()) {
                System.out.println("No hay libros registrados en la base de datos.");
            } else {

                System.out.println("Total de libros encontrados: " + libros.size());
                System.out.println("-------------------------------------");
                for (Libro libro : libros) {

                    System.out.println("ID BD: " + libro.getId());
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("ID Gutendex: " + libro.getIdGutendex());


                    if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                        System.out.print("Autor(es): ");

                        List<String> nombresAutores = libro.getAutores().stream()
                                .map(Author::getNombre)
                                .toList(); // En Java 16+

                        System.out.println(String.join(", ", nombresAutores));
                    } else {
                        System.out.println("Autor(es): No especificados");
                    }

                    if (libro.getBookshelves() != null && !libro.getBookshelves().isEmpty()) {
                        System.out.println("Tema(s)/Bookshelf(es): " + String.join(", ", libro.getBookshelves()));
                    } else {
                        System.out.println("Tema(s)/Bookshelf(es): No especificados");
                    }

                    if (libro.getIdiomas() != null && !libro.getIdiomas().isEmpty()) {
                        System.out.println("Idioma(s): " + String.join(", ", libro.getIdiomas()));
                    } else {
                        System.out.println("Idioma(s): No especificados");
                    }


                    System.out.println("Descargas: " + libro.getNumeroDescargas());

                    System.out.println("-------------------------------------");
                }
            }


            return libros;

        } catch (Exception e) {

            System.err.println("Error al obtener la lista de libros registrados: " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    // --- Metodos del servicio --- opción 3
    public List<Author> listarAutoresRegistrados() {
        try {
            System.out.println("\n--- Listado de Autores Registrados ---");


            List<Author> autores = authorRepository.findAll();


            if (autores == null || autores.isEmpty()) {
                System.out.println("No hay autores registrados en la base de datos.");
            } else {

                System.out.println("Total de autores encontrados: " + autores.size());
                System.out.println("--------------------------------------");
                for (Author autor : autores) {
                    // Mostrar información del autor
                    System.out.println("ID BD: " + autor.getId());
                    System.out.println("Nombre: " + autor.getNombre());

                    // Mostrar fechas de nacimiento y fallecimiento (si están disponibles)
                    if (autor.getFechaNacimiento() != null) {
                        System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                    } else {
                        System.out.println("Fecha de Nacimiento: No especificada");
                    }

                    if (autor.getFechaFallecimiento() != null) {
                        System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                    } else {
                        System.out.println("Fecha de Fallecimiento: No especificada");
                    }

                    System.out.println("--------------------------------------");
                }
            }


            return autores;

        } catch (Exception e) {

            System.err.println("Error al obtener la lista de autores registrados: " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    // --- Metodos del servicio --- opción 4
    public List<Author> listarAutoresVivosEnAño(Integer año) {
        try {
            System.out.println("\n--- Listado de Autores Vivos en el Año " + año + " ---");


            if (año == null) {
                System.out.println("Error: El año proporcionado no puede ser nulo.");
                return Collections.emptyList();
            }


            List<Author> autoresVivos = authorRepository.findAutoresVivosEnAño(año);


            if (autoresVivos == null || autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + año + ".");
            } else {
                // --- PASO 4: Mostrar la lista de autores encontrados ---
                System.out.println("Total de autores encontrados: " + autoresVivos.size());
                System.out.println("-----------------------------------------------");
                for (Author autor : autoresVivos) {
                    // Mostrar información del autor
                    System.out.println("ID BD: " + autor.getId());
                    System.out.println("Nombre: " + autor.getNombre());


                    System.out.print("Fecha de Nacimiento: ");
                    if (autor.getFechaNacimiento() != null) {
                        System.out.println(autor.getFechaNacimiento());
                    } else {
                        System.out.println("No especificada");
                    }

                    System.out.print("Fecha de Fallecimiento: ");
                    if (autor.getFechaFallecimiento() != null) {
                        System.out.println(autor.getFechaFallecimiento());
                    } else {
                        System.out.println("No especificada (Posiblemente aún vivo)");
                    }

                    System.out.println("-----------------------------------------------");
                }
            }


            return autoresVivos;

        } catch (Exception e) {

            System.err.println("Error al obtener la lista de autores vivos en el año " + año + ": " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    // --- Metodos del servicio --- opción 5
    @Transactional
    public List<Libro> listarLibrosPorIdioma(String idioma) {
        try {
            System.out.println("\n--- Listado de Libros en el Idioma: " + idioma + " ---");


            if (idioma == null || idioma.trim().isEmpty()) {
                System.out.println("Error: El código de idioma proporcionado no puede ser nulo o vacío.");
                return Collections.emptyList();
            }

            idioma = idioma.trim();


            List<Libro> librosPorIdioma = libroRepository.findByIdiomasContaining(idioma);


            if (librosPorIdioma == null || librosPorIdioma.isEmpty()) {
                System.out.println("No se encontraron libros registrados en el idioma '" + idioma + "'.");
            } else {
                // --- PASO 4: Mostrar la lista de libros encontrados ---
                System.out.println("Total de libros encontrados en '" + idioma + "': " + librosPorIdioma.size());
                System.out.println("-----------------------------------------------");
                for (Libro libro : librosPorIdioma) {

                    System.out.println("ID BD: " + libro.getId());
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("ID Gutendex: " + libro.getIdGutendex());


                    if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                        System.out.print("Autor(es): ");
                        List<String> nombresAutores = libro.getAutores().stream()
                                .map(Author::getNombre)
                                .toList(); // Java 16+

                        System.out.println(String.join(", ", nombresAutores));
                    } else {
                        System.out.println("Autor(es): No especificados");
                    }


                    if (libro.getIdiomas() != null && !libro.getIdiomas().isEmpty()) {
                        System.out.println("Todos los Idioma(s) del libro: " + String.join(", ", libro.getIdiomas()));
                    } else {
                        System.out.println("Todos los Idioma(s) del libro: No especificados");
                    }


                    System.out.println("Descargas: " + libro.getNumeroDescargas());

                    System.out.println("-----------------------------------------------");
                }
            }


            return librosPorIdioma;

        } catch (Exception e) {

            System.err.println("Error al obtener la lista de libros en el idioma '" + idioma + "': " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    // --- Metodos del servicio --- opción 6
    @Transactional
    public List<Libro> listarLibrosPorTema(String tema) {
        try {
            System.out.println("\n--- Listado de Libros en el Tema/Bookshelf: " + tema + " ---");


            if (tema == null || tema.trim().isEmpty()) {
                System.out.println("Error: El nombre del tema proporcionado no puede ser nulo o vacío.");
                return Collections.emptyList();
            }

            tema = tema.trim();


            List<Libro> librosPorTema = libroRepository.findByBookshelvesContainingIgnoreCase(tema);


            if (librosPorTema == null || librosPorTema.isEmpty()) {
                System.out.println("No se encontraron libros registrados en el tema/bookshelf '" + tema + "'.");
            } else {

                System.out.println("Total de libros encontrados en '" + tema + "': " + librosPorTema.size());
                System.out.println("-----------------------------------------------");
                for (Libro libro : librosPorTema) {

                    System.out.println("ID BD: " + libro.getId());
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("ID Gutendex: " + libro.getIdGutendex());


                    if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                        System.out.print("Autor(es): ");
                        List<String> nombresAutores = libro.getAutores().stream()
                                .map(Author::getNombre)
                                .toList(); // Java 16+

                        System.out.println(String.join(", ", nombresAutores));
                    } else {
                        System.out.println("Autor(es): No especificados");
                    }


                    if (libro.getBookshelves() != null && !libro.getBookshelves().isEmpty()) {
                        System.out.println("Todos los Tema(s)/Bookshelf(es) del libro: " + String.join(", ", libro.getBookshelves()));
                    } else {
                        System.out.println("Todos los Tema(s)/Bookshelf(es) del libro: No especificados");
                    }


                    System.out.println("Descargas: " + libro.getNumeroDescargas());

                    System.out.println("-----------------------------------------------");
                }
            }


            return librosPorTema;

        } catch (Exception e) {

            System.err.println("Error al obtener la lista de libros en el tema '" + tema + "': " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    public void exhibirEstadisticasIdiomas() {
        try {
            System.out.println("\n--- Estadísticas de Libros por Idioma ---");
            System.out.println("Contando libros en idiomas específicos...");


            String idiomaEspaniol = "es";
            String idiomaIngles = "en";


            Long conteoEspaniol = libroRepository.countByIdiomasContaining(idiomaEspaniol);
            Long conteoIngles = libroRepository.countByIdiomasContaining(idiomaIngles);


            System.out.println("\nResultados:");
            System.out.println("----------------------------------------");
            System.out.println("Libros en Español (" + idiomaEspaniol + "): " + conteoEspaniol);
            System.out.println("Libros en Inglés (" + idiomaIngles + "): " + conteoIngles);
            System.out.println("----------------------------------------");
            System.out.println("Total de libros contados: " + (conteoEspaniol + conteoIngles));

        } catch (Exception e) {

            System.err.println("Error al obtener las estadísticas de idiomas: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private <T> T obtenerDatosDeAPI(String url, Class<T> clase) {
        try {

            String json = consumoAPI.obtenerDatos(url);
            System.out.println("JSON obtenido de la API: " + json);


            T datos = convierteDatos.obtenerDatos(json, clase);
            return datos;
        } catch (Exception e) {
            System.err.println("Error al obtener o convertir datos de la API: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    private void guardarLibroYAutores(Book bookAPI) {
        try {
            System.out.println("Iniciando el guardado del libro: " + bookAPI.getTitulo());


            Libro nuevoLibro = new Libro(bookAPI);
            System.out.println("  Entidad Libro creada a partir del modelo de API.");


            List<Author> autoresParaLibro = new ArrayList<>();


            if (bookAPI.getAutores() != null && !bookAPI.getAutores().isEmpty()) {
                System.out.println("  Procesando " + bookAPI.getAutores().size() + " autor(es) encontrado(s).");


                for (com.alura.desafio.literatura.model.Author authorAPI : bookAPI.getAutores()) {

                    Optional<Author> autorExistenteOpt = authorRepository.findByNombre(authorAPI.getNombre());

                    Author autorEntity;

                    if (autorExistenteOpt.isPresent()) {

                        autorEntity = autorExistenteOpt.get();
                        System.out.println("    Autor encontrado en BD: " + autorEntity.getNombre() + " (ID: " + autorEntity.getId() + ")");
                        autorEntity = authorRepository.save(autorEntity);
                        autorEntity = authorRepository.findById(autorEntity.getId()).orElse(autorEntity);
                    } else {

                        autorEntity = new Author(authorAPI);

                        autorEntity = authorRepository.save(autorEntity);
                        System.out.println("    Nuevo autor guardado en BD: " + autorEntity.getNombre() + " (ID: " + autorEntity.getId() + ")");
                        autorEntity = authorRepository.findById(autorEntity.getId()).orElse(autorEntity);
                    }


                    autoresParaLibro.add(autorEntity);
                }
            } else {
                System.out.println("  El libro no tiene autores asociados en la API.");
            }


            nuevoLibro.setAutores(autoresParaLibro);
            System.out.println("  Lista de autores asociada al libro.");


            Libro libroGuardado = libroRepository.save(nuevoLibro);
            System.out.println("\n--- ¡Libro guardado exitosamente en la base de datos! ---");
            System.out.println("  ID en BD: " + libroGuardado.getId());
            System.out.println("  Título: " + libroGuardado.getTitulo());
            System.out.println("  ID Gutendex: " + libroGuardado.getIdGutendex());
            if (libroGuardado.getBookshelves() != null && !libroGuardado.getBookshelves().isEmpty()) {
                System.out.println("  Tema(s)/Bookshelf(es): " + String.join(", ", libroGuardado.getBookshelves()));
            } else {
                System.out.println("  Tema(s)/Bookshelf(es): No especificados");
            }
            System.out.println("  Número de autores asociados: " + (libroGuardado.getAutores() != null ? libroGuardado.getAutores().size() : 0));


        } catch (Exception e) {

            System.err.println("Error al guardar el libro y autores: " + e.getMessage());
            e.printStackTrace();

        }
    }
}