# Etapa 1: build do jar com Maven
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia primeiro o wrapper e o pom para aproveitar o cache das dependências
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw -B package -DskipTests

# Etapa 2: imagem final só com o JRE e o jar
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
