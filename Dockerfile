# Etapa 1: Construcción de la app
FROM eclipse-temurin:21.0.3_9-jdk AS builder

LABEL authors="Víctor-José-Yubliam-Keyner"
WORKDIR /app

# Copiar archivos necesarios para preparar dependencias
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# Descargar dependencias sin compilar
RUN ./mvnw dependency:go-offline

# Copiar el código fuente completo
COPY src ./src

# Construir el JAR sin ejecutar tests
RUN ./mvnw clean package -DskipTests

# Limpiar el caché de Maven para reducir el tamaño de la imagen
RUN rm -rf ~/.m2/repository

# Etapa 2: Imagen final
FROM eclipse-temurin:21.0.3_9-jre

LABEL authors="Víctor-José-Yubliam-Keyner"
WORKDIR /app

# Copiar el JAR desde la etapa builder
COPY --from=builder /app/target/buques-0.0.1-SNAPSHOT.jar ./buques.jar

# Exponer el puerto en el que Spring Boot escucha por defecto (8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "buques.jar"]