# Bart - La plataforma de intercambio entre artistas y arrendadores de espacios

Bart es una aplicación que conecta a artistas y arrendadores de espacios para sesiones artísticas. Con Bart, los artistas pueden ofrecer sus servicios y los arrendadoras pueden publicar sus locales disponibles para sesiones artísticas.

## Instalación

Para instalar la aplicación, se requieren los siguientes elementos:

-   Java 11 o superior
-   PostgreSQL
-   Maven

A continuación se detalla el proceso de instalación:
 1.  Clonar el repositorio de GitHub en tu máquina local utilizando el siguiente comando:
 git clone https://github.com/ispp-g7/bart.git
 2.  Configurar la conexión a la base de datos PostgreSQL editando el archivo `application.properties` que se encuentra en la ruta `src/main/resources`. Allí se deben modificar las siguientes líneas:
 spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_basedatos
spring.datasource.username=nombre_usuario
spring.datasource.password=contraseña_usuario
3.  Compilar el proyecto utilizando el siguiente comando en la raíz del proyecto:
mvn package
4.  Ejecutar la aplicación con el siguiente comando:
java -jar target/bart-0.0.1-SNAPSHOT.jar
5.  La aplicación estará disponible en el navegador ingresando a la siguiente URL:
http://localhost:8080/
## Uso

Una vez instalada la aplicación, se pueden realizar las siguientes acciones:

-   Los artistas pueden ofrecer sus servicios de sesión artística completando el formulario disponible en la página de inicio.
-   Las arrendadoras de espacios pueden publicar sus locales disponibles para sesiones artísticas completando el formulario disponible en la sección "Locales disponibles".
-   Los usuarios pueden buscar sesiones artísticas y locales disponibles utilizando el buscador en la página principal.

## Contribución

Si deseas contribuir a Bart, puedes seguir los siguientes pasos:

1.  Realiza un fork del repositorio en GitHub.
2.  Clona el repositorio en tu máquina local.
3.  Realiza los cambios necesarios y haz commit a tus cambios en una nueva rama.
4.  Realiza un pull request desde tu rama hacia la rama `main` del repositorio original.## Créditos

Bart fue desarrollado por el equipo ISPP-G7 para el proyecto final del curso de Ingeniería de Software del programa Full Stack de Acámica.

## Contacto

Si tienes preguntas o comentarios sobre la aplicación, por favor contáctanos en nuestra página web:

[https://ispp-g7.github.io/](https://ispp-g7.github.io/)

¡Gracias por utilizar Bart!
