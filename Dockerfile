# Estágio 1: Build
# Usamos uma imagem com Maven e JDK 25 para compilar o projeto
FROM maven:3-eclipse-temurin-25-alpine AS build
WORKDIR /app

# Copiamos apenas o pom.xml primeiro para baixar as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos o código fonte e geramos o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run
# Usamos uma imagem JRE (mais leve que o JDK) para rodar a aplicação
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Criamos um usuário não-root por segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos apenas o JAR gerado no estágio anterior
# O nome do JAR deve corresponder ao artifactId e version no seu pom.xml
COPY --from=build /app/target/DevFit-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta padrão do Spring Boot
EXPOSE 8080

# Comando de inicialização otimizado para containers
ENTRYPOINT ["java", "-jar", "app.jar"]