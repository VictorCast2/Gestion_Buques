# Etapa 1: Construcción de la app
FROM eclipse-temurin:21.0.3_9-jdk AS builder

LABEL authors="Víctor-José-Yubliam-Keyner"
WORKDIR /app

# Copiar archivos necesarios para preparar dependencias
COPY pom.xml mvnw ./
COPY .mvn/ .mvn/

# Otorgar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Descargar dependencias sin compilar
RUN ./mvnw dependency:go-offline

# Copiar el código fuente completo
COPY src ./src

# Construir el JAR sin ejecutar tests
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final para producción
FROM eclipse-temurin:21.0.3_9-jre

LABEL authors="Víctor-José-Yubliam-Keyner"
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=builder /app/target/buques-0.0.1-SNAPSHOT.jar ./buques.jar

# Exponer el puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "buques.jar"]