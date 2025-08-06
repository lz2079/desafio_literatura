# desafio_literatura
Este proyecto es una aplicación de consola desarrollada en Java como parte del desafío del programa **Oracle Next Education (ONE)** de Alura Latam.

# Desafío Literatura - Alura ONE

Este proyecto es una aplicación de consola desarrollada en Java como parte del desafío del programa **Oracle Next Education (ONE)** de Alura Latam. La aplicación permite buscar libros y autores en la API pública [Gutendex](https://gutendex.com/), almacenarlos en una base de datos PostgreSQL y realizar diversas consultas y búsquedas sobre los datos almacenados.

## 📋 Tabla de Contenidos

1.  [Descripción del Proyecto]
2.  [Diagrama de Arquitectura]
3.  [Funcionalidades]
4.  [Cómo Usarlo]
5.  [Obtención de Ayuda]
6.  [Autor]

## 📝 Descripción del Proyecto

La aplicación "Literatura" simula un catálogo de libros. Permite al usuario interactuar mediante un menú de consola para:
*   Buscar libros por título en la API de Gutendex.
*   Guardar los libros y sus autores en una base de datos local.
*   Listar todos los libros y autores almacenados.
*   Realizar búsquedas avanzadas por autor, año, idioma o tema (bookshelf).
*   Obtener estadísticas simples, como el conteo de libros por idioma.

El objetivo es demostrar habilidades en Java, Spring Boot, consumo de APIs REST, mapeo objeto-relacional (JPA/Hibernate) y el uso de bases de datos relacionales.

## 🏗️ Diagrama de Arquitectura

La aplicación sigue una arquitectura basada en capas, común en aplicaciones Spring Boot:

+--------------------+
|  Usuario (Consola) |
+--------------------+
          |
          | (Entrada/Salida)
          v
+-------------------------------+
|  DesafioLiteraturaApplication |
|  (CommandLineRunner)          |
+-------------------------------+
          |
          | (Llama a métodos del servicio)
          v
+-------------------------+
|     LibroService        |
|  (Lógica de Negocio)    |
+-------------------------+
          |
    +-----+-----+
    |           |
    v           v
+------------+ +--------------+
| ConsumoAPI | ConvierteDatos |
| (HTTP)     | (JSON <-> Java)|
+------------+ +--------------+
    |           |
    | (JSON)    | (Objetos Java)
    v           v
+--------------------------------------+
| Gutendex API (https://gutendex.com ) |
+--------------------------------------+ 

+-------------------------+
|     LibroService        |
|  (Lógica de Negocio)    |
+-------------------------+
              |
    	  +-----+----------------+
        |                      |
        v                      v
+----------------+   +----------------------+
| LibroRepository|   | AutorRepository      |
| (JPA)          |   | (JPA)                |
+----------------+   +----------------------+
          |
          | (Operaciones en BD)
          v
+---------------------------------+
|    Base de Datos (PostgreSQL)   |
|  Tablas: libros, autores,       |
|  libro_autor, libro_idiomas,    |
|  libro_temas                    |
+---------------------------------+ 

*(Nota: Las flechas representan la dirección principal del flujo de datos o dependencias)*

## ✨ Funcionalidades

La aplicación presenta un menú interactivo en la consola con las siguientes opciones:

1.  **Buscar libro por título:** Solicita un título, busca en la API Gutendex y guarda el libro (y sus autores) si no existe en la base de datos.
2.  **Listar libros registrados:** Muestra todos los libros almacenados en la base de datos.
3.  **Listar autores registrados:** Muestra todos los autores almacenados en la base de datos.
4.  **Listar autores vivos en un determinado año:** Permite ingresar un año y muestra los autores que estaban vivos en ese año.
5.  **Listar libros por idioma:** Permite ingresar un código de idioma (ej. 'en', 'es') y muestra los libros disponibles en ese idioma.
6.  **Listar libros por tema (bookshelves):** Permite ingresar un tema y muestra los libros asociados a ese tema. (Nota: La búsqueda es por coincidencia parcial e insensible a mayúsculas/minúsculas dentro de los `bookshelves` completos almacenados, p. ej., buscar "American Literature" encontrará "Category: American Literature").
7.  **Exhibir cantidad de libros en Español e Inglés:** Muestra estadísticas del conteo de libros en español ('es') e inglés ('en').
0.  **Salir:** Finaliza la aplicación.

## ▶️ Cómo Usarlo

### Prerrequisitos

*   **JDK 17** instalado y configurado.
*   **Maven** instalado y configurado.
*   **PostgreSQL** instalado y ejecutándose.
*   **PgAdmin 4** (opcional, pero útil para administrar la BD).
*   **IDE** como IntelliJ IDEA (recomendado).

### Pasos

1.  **Clonar o Descargar el Repositorio:**
    ```bash
    git clone <[(https://github.com/lz2079/desafio_literatura)]> o simplemente descarga el código fuente.
    ```
2.  **Configurar la Base de Datos:**
    *   Abre **PgAdmin**.
    *   Crea una nueva base de datos (por ejemplo, `literatura_db`).
    *   Anota el **nombre de usuario** y **contraseña** de tu servidor PostgreSQL.
3.  **Configurar `application.properties`:**
    *   Abre el archivo `src/main/resources/application.properties`.
    *   Asegúrate de que las siguientes propiedades estén configuradas correctamente:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_db
        spring.datasource.username=tu_usuario_postgres
        spring.datasource.password=tu_contraseña_postgres
        # (Las demás propiedades como hibernate.ddl-auto=update, show-sql, etc., también deben estar presentes)
        ```
4.  **Construir el Proyecto:**
    *   Desde la raíz del proyecto, ejecuta en la terminal:
        ```bash
        mvn clean install
        ```
5.  **Ejecutar la Aplicación:**
    *   Desde la raíz del proyecto, ejecuta:
        ```bash
        mvn spring-boot:run
        ```
    *   O ejecuta la clase principal `DesafioLiteraturaApplication` desde tu IDE.

### Interacción

Una vez iniciada la aplicación, sigue las instrucciones en la consola. Se mostrará un menú numerado. Ingresa el número correspondiente a la opción deseada y presiona `Enter`. Sigue las indicaciones para ingresar títulos, años, códigos de idioma o temas.

Ejemplo de interacción:

===== MENU PRINCIPAL =====
1 - Buscar libro por título
2 - Listar libros registrados
...
0 - Salir
Seleccione una opción: 1
Ingrese el título del libro a buscar: Don Quijote
...


## ❓ Obtención de Ayuda

Para obtener ayuda sobre este proyecto, puedes:

*   **Consultar este README.**
*   **Revisar los comentarios en el código fuente.**
*   **Contactar al autor directamente.**

## ✍️ Autor

*   **[Luis Alberto Zepeda Toro]** - [lz2079@hotmail.com - https://github.com/lz2079]
