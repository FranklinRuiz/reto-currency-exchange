# Etapa 1: Build
FROM openjdk:21-jdk-slim AS builder

# Actualizamos e instalamos Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /build

# Copiamos el archivo pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM openjdk:21-jdk-slim
WORKDIR /

# Copiamos el JAR generado desde la etapa de compilación
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
