FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Trocada a imagem antiga pela do Temurin atualizada
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app

COPY --from=build /app/target/duxusdesafio-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]