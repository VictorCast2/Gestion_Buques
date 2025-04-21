# Etapa 1: Compilar la app con Maven
FROM eclipse-temurin:21.0.3_9-jdk AS builder

# Establecer el directorio de trabajo
LABEL authors="Víctor-José-Yubliam-Keyner"
WORKDIR /app

# Copiar el archivo pom.xml, .mvn y mvnw
COPY ./pom.xml .
COPY ./.mvn .mvn
COPY ./mvnw .

# Descargar dependencias sin compilar
RUN ./mvnw dependency:go-offline

# Copiar el resto del código fuente
RUN mvn clean install -DskipTests

# Copiar el código fuente
COPY src ./src

# Construir el JAR de la aplicación
RUN mvn package -DskipTests && mvn clean

# Etapa 2: Imagen final
FROM eclipse-temurin:21.0.3_9-jre

# Establecer el directorio de trabajo
LABEL authors="Víctor-José-Yubliam-Keyner"
WORKDIR /app

# Copiar solo el JAR generado desde la etapa de construcción
COPY --from=build /app/target/buques-0.0.1-SNAPSHOT.jar /app/buques.jar

# Exponer el puerto en el que Spring Boot escucha por defecto (8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/buques.jar"]