# MS - API REST PROGRAMS

## Requisitos
Antes de comenzar, asegúrate de tener instalado en tu sistema:

- Java Development Kit: [Descargar JDK 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- Apache Maven: [Descargar Apache Maven 3.9.3](https://maven.apache.org/download.cgi)
- Spring Boot: [Información sobre Spring Boot](https://spring.io/projects/spring-boot)

## Inicio Rápido

1. Clona este repositorio en tu máquina local:

   ```bash
   git clone https://gitlab.com/vallegrande/AS211S6/as211-programs-be.git

2. Abre el proyecto en tu entorno de desarrollo preferido. A continuación, se mencionan algunos IDE populares:

   - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
   - [Eclipse](https://www.eclipse.org/)
   - [Visual Studio Code](https://code.visualstudio.com/)

3. Ejecuta la aplicación Spring Boot desde tu IDE o mediante el siguiente comando en la línea de comandos:

   ```bash
   mvn spring-boot:run

## Endpoints del Controlador

El controlador `ProgramsController` proporciona los siguientes endpoints para gestionar programas:

- **GET /v1/programs/list**: Obtiene la lista de todos los programas disponibles. Los resultados se pueden entregar en formato JSON o en un formato de flujo de eventos.

- **GET /v1/programs/listI**: Obtiene la lista de programas inactivos. Los resultados se pueden entregar en formato JSON o en un formato de flujo de eventos.

- **GET /v1/programs/find/{id}**: Obtiene los detalles de un programa específico según su ID. Los resultados se pueden entregar en formato JSON o en un formato de flujo de eventos.

- **PUT /v1/programs/update/{id}**: Actualiza los detalles de un programa existente según su ID. Debes proporcionar el ID del programa que deseas actualizar y enviar los nuevos datos en el cuerpo de la solicitud en formato JSON. Los resultados se pueden entregar en formato JSON o en un formato de flujo de eventos.

- **POST /v1/programs/save**: Crea un nuevo programa. Debes enviar los datos del programa en el cuerpo de la solicitud en formato JSON. Los resultados se pueden entregar en formato JSON o en un formato de flujo de eventos.

- **DELETE /v1/programs/delete/{id}**: Elimina un programa según su ID. Debes especificar el ID del programa que deseas eliminar. Este endpoint no devuelve contenido en la respuesta.

- **PUT /v1/programs/restore/{id}**: Restaura un programa previamente inactivo según su ID. Debes especificar el ID del programa que deseas restaurar. Los resultados se pueden entregar en formato JSON o en un formato de flujo de eventos.

Asegúrate de utilizar los métodos HTTP correspondientes (GET, PUT, POST, DELETE) y proporcionar la información necesaria en el cuerpo de las solicitudes cuando sea necesario. Consulta la documentación de la API para obtener detalles adicionales sobre cómo utilizar estos endpoints.
