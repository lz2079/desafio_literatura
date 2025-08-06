package com.alura.desafio.literatura;

import com.alura.desafio.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DesafioLiteraturaApplication implements CommandLineRunner {


    @Autowired
    private LibroService libroService;


    private Scanner teclado = new Scanner(System.in);

    private boolean salir = false;

    public static void main(String[] args) {
        SpringApplication.run(DesafioLiteraturaApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- BIENVENIDO AL DESAFIO LITERATURA ---");


        while (!salir) {
            mostrarMenu();
            String opcion = teclado.nextLine();
            procesarOpcion(opcion);
        }

        System.out.println("¡Gracias por usar la aplicación. Hasta la proxima.");
        teclado.close();
    }


    private void mostrarMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1 - Buscar libro por título");
        System.out.println("2 - Listar libros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos en un determinado año");
        System.out.println("5 - Listar libros por idioma");
        System.out.println("6 - Listar libros por tema (bookshelves)");
        System.out.println("7 - Exhibir cantidad de libros en Español e Inglés");
        System.out.println("0 - Salir");
        System.out.print("Seleccione una opción: ");
    }


    private void procesarOpcion(String opcion) {
        switch (opcion) {
            case "1":
                // Funcionalidad 1: Buscar libro por título
                System.out.print("Ingrese el título del libro a buscar: ");
                String titulo = teclado.nextLine();
                if (titulo != null && !titulo.trim().isEmpty()) {
                    libroService.buscarLibroPorTitulo(titulo);
                } else {
                    System.out.println("El título no puede estar vacío.");
                }
                break;
            case "2":
                // Funcionalidad 2: Listar libros registrados
                libroService.listarLibrosRegistrados();

                break;
            case "3":
                // Funcionalidad 3: Listar autores registrados
                libroService.listarAutoresRegistrados();

                break;
            case "4":
                // Funcionalidad 4: Listar autores vivos en un determinado año
                System.out.print("Ingrese el año para buscar autores vivos: ");
                try {
                    int año = Integer.parseInt(teclado.nextLine());
                    libroService.listarAutoresVivosEnAño(año);

                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingrese un número válido para el año.");
                }
                break;
            case "5":
                // Funcionalidad 5: Listar libros por idioma
                System.out.print("Ingrese el código del idioma (por ejemplo, 'en' para inglés, 'es' para español): ");
                String idiomaIngresado = teclado.nextLine();
                if (idiomaIngresado != null && !idiomaIngresado.trim().isEmpty()) {
                    libroService.listarLibrosPorIdioma(idiomaIngresado);

                } else {
                    System.out.println("El código de idioma no puede estar vacío.");
                }
                break;
            case "6":
                // Funcionalidad 6: Listar libros por tema (bookshelves)
                System.out.print("Ingrese el tema a buscar: ");
                String tema = teclado.nextLine();
                if (tema != null && !tema.trim().isEmpty()) {
                    libroService.listarLibrosPorTema(tema);

                } else {
                    System.out.println("El tema no puede estar vacío.");
                }
                break;
            case "7":
                // Funcionalidad 7: Exhibir estadísticas de idiomas
                libroService.exhibirEstadisticasIdiomas();
                break;
            case "0":
                salir = true;
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción del menú.");
                break;
        }
    }
}