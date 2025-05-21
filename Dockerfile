# Etapa 1: Construcción de la app
FROM eclipse-temurin:21.0.3_9-jdk AS builder

LABEL authors="Víctor-José-Yubliam-Keyner"

# Asegurar entorno en UTF-8
ENV LANG C.UTF-8

WORKDIR /app

# Copiar archivos necesarios para las dependencias
COPY pom.xml mvnw ./
COPY .mvn/ .mvn/

# Dar permisos al wrapper de Maven
RUN chmod +x mvnw

# Descargar dependencias en modo offline (más rápido, sin compilar)
RUN ./mvnw dependency:go-offline --batch-mode

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto sin ejecutar tests
RUN ./mvnw clean package -DskipTests --batch-mode

# Etapa 2: Imagen final de producción
FROM eclipse-temurin:21.0.3_9-jre

LABEL authors="Víctor-José-Yubliam-Keyner"

WORKDIR /app

# Copiar el JAR generado desde la etapa anterior
COPY --from=builder /app/target/buques-0.0.1-SNAPSHOT.jar ./buques.jar

# Exponer el puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "buques.jar"]