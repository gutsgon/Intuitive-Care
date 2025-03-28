# Estágio de build
FROM openjdk:22-slim AS build
WORKDIR /app

# Instalar Maven no container (caso não tenha)
RUN apt-get update && apt-get install -y maven

# Limpar o cache do apt-get para reduzir a imagem final
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Copiar os arquivos do projeto
COPY pom.xml .
COPY src/ ./src/

# Limpar o cache do Maven e forçar atualização das dependências
RUN mvn clean install -DskipTests

# Estágio de runtime
FROM openjdk:22-slim
WORKDIR /app

# Copiar o JAR compilado do estágio de build
COPY --from=build /app/target/webscraping-1.0.jar app.jar

# Expor a porta (caso precise acessar a aplicação)
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
