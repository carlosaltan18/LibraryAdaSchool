# Utiliza una imagen base con OpenJDK 21 y Gradle 8.1.1
FROM gradle-8.10.2-jdk21 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de tu proyecto al directorio de trabajo
COPY . .

# Da permisos de ejecución al wrapper de Gradle
RUN chmod +x gradlew

# Construye tu aplicación con Gradle usando el wrapper
RUN ./gradlew clean build --no-daemon

# Cambia a una imagen más ligera de OpenJDK 21 para la ejecución
FROM openjdk:21-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado por la fase de construcción
COPY --from=build /app/build/libs/biblioteca-0.0.1-SNAPSHOT.jar .

# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Define el comando de inicio de la aplicación
CMD ["java", "-jar", "biblioteca-0.0.1-SNAPSHOT.jar"]
