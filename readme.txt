ExamenPSP2
==========

Descripción del proyecto
------------------------
ExamenPSP2 es un proyecto de Java que simula estaciones meteorológicas inteligentes que envían información a través de MQTT. Los datos se almacenan en una base de datos Redis y se pueden visualizar a través de una interfaz de consola.

Requisitos del sistema
----------------------
- Java 18.0.2.1
- Maven
- Redis
- Eclipse Paho MQTT Java client

Instalación
-----------
1. Clona el repositorio de GitHub.
2. Navega hasta el directorio del proyecto.
3. Ejecuta `mvn clean install` para construir el proyecto.
4. Asegúrate de que Redis está instalado y en funcionamiento en tu máquina.
5. Asegúrate de que tienes un servidor MQTT en funcionamiento.

Uso
---
1. Ejecuta `MeteoStations.java` para iniciar las estaciones meteorológicas.
2. Ejecuta `MeteoServer.java` para iniciar el servidor que escucha los topics MQTT de las estaciones meteorológicas y almacena los datos en Redis.
3. Ejecuta `MeteoClient.java` para visualizar la información del sistema a través de la consola.

