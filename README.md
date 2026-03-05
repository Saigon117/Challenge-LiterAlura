# 📚 LiterAlura - Challenge Oracle Next Education + Alura

¡Bienvenido al proyecto LiterAlura! Esta es una aplicación de consola desarrollada en Java que gestiona un catálogo de libros interactuando con la API de Gutendex. El proyecto forma parte del programa de formación de Oracle Next Education (ONE) en conjunto con Alura Latam.

## 🚀 Funcionalidades Principales

* **Búsqueda Inteligente**: Busca libros por título consumiendo datos reales de la API Gutendex y los persiste automáticamente en una base de datos PostgreSQL.
* **Gestión de Autores**: Permite listar todos los autores registrados y filtrar autores vivos en años específicos mediante **Derived Queries**.
* **Estadísticas por Idioma**: Utiliza la **API de Streams** de Java para procesar y contar libros según el idioma seleccionado, brindando reportes precisos al usuario.
* **Persistencia Robusta**: Implementa relaciones `@ManyToOne` para vincular libros con sus respectivos autores, asegurando la integridad referencial en la base de datos.

## 🛠️ Tecnologías Utilizadas

* **Java 17**: Lenguaje de programación principal.
* **Spring Boot**: Framework para la gestión de dependencias y configuración.
* **Spring Data JPA**: Para la interacción fluida con la base de datos.
* **PostgreSQL**: Sistema de gestión de bases de datos relacional.
* **Jackson**: Biblioteca para el mapeo y conversión de datos JSON.
* **Git/GitHub**: Para el control de versiones y despliegue del código.

## 📋 Requisitos Previos

1.  Java JDK 17 o superior.
2.  PostgreSQL instalado y configurado.
3.  Una herramienta como IntelliJ IDEA o Eclipse.

## 🔧 Configuración

Asegúrate de configurar tu archivo `src/main/resources/application.properties` con las credenciales de tu base de datos local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena
spring.jpa.hibernate.ddl-auto=update
