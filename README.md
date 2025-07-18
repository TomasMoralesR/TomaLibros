# 📚 Catálogo de Libros - Challenge Alura

Este es un proyecto de catálogo de libros interactivo desarrollado en Java con Spring Boot. La aplicación funciona a través de la consola y permite a los usuarios buscar libros clásicos utilizando la API pública de **Gutendex**, guardarlos en una base de datos local y realizar diversas consultas sobre los datos almacenados.

-----

## ✨ Funcionalidades

La aplicación ofrece un menú con 5 opciones principales para interactuar con el catálogo:

  * 🔍 **Buscar Libro por Título:** Busca un libro en la API de Gutendex por su título y, si se encuentra, lo guarda en la base de datos local para futuras consultas.
  * 📖 **Listar Libros Registrados:** Muestra una lista completa de todos los libros que han sido guardados en la base de datos.
  * 🧑‍🎨 **Listar Autores Registrados:** Muestra una lista de todos los autores asociados a los libros guardados.
  * 🗓️ **Listar Autores Vivos por Año:** Permite al usuario ingresar un año específico y muestra una lista de autores que estaban vivos durante ese período.
  * 🌐 **Listar Libros por Idioma:** Muestra los libros registrados en un idioma específico (ej. español, inglés, francés).

-----

## 🛠️ Tecnologías Utilizadas

  * **Java 17+**
  * **Spring Boot**
  * **Spring Data JPA**
  * **PostgreSQL** (Base de Datos)
  * **Maven** (Gestor de Dependencias)
  * **API de Gutendex** (Fuente de Datos de Libros)

-----

## 🚀 Cómo Empezar

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### **Prerrequisitos**

  * Tener instalado **Java (JDK 17 o superior)**.
  * Tener instalado **Maven**.
  * Tener una instancia de **PostgreSQL** en ejecución.

### **Instalación y Configuración**

1.  **Clona el repositorio:**

    ```sh
    git clone https://github.com/TomasMoralesR/TomaLibros.git
    cd TomaLibros
    ```

2.  **Crea la base de datos:**

      * Abre tu cliente de PostgreSQL (como `psql` o pgAdmin).
      * Crea una nueva base de datos. Por ejemplo: `CREATE DATABASE catalogo_libros;`

3.  **Configura la conexión a la base de datos:**

      * Abre el archivo `src/main/resources/application.properties`.
      * Modifica las siguientes propiedades con tus credenciales de PostgreSQL:

    <!-- end list -->

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/catalogo_libros
    spring.datasource.username=tu_usuario_postgres
    spring.datasource.password=tu_contraseña_postgres
    spring.jpa.hibernate.ddl-auto=update
    ```

4.  **Ejecuta la aplicación:**

      * Abre una terminal en la raíz del proyecto.
      * Ejecuta el siguiente comando de Maven:

    <!-- end list -->

    ```sh
    mvn spring-boot:run
    ```

-----

## 💻 Uso

Una vez que la aplicación esté en ejecución, verás un menú interactivo en la consola. Simplemente ingresa el número de la opción que deseas ejecutar y presiona `Enter`. Sigue las instrucciones que aparecerán en pantalla para cada función.
