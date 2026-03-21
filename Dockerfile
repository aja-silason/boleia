# Estágio de Build
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copia apenas os arquivos de configuração primeiro (otimiza o cache de dependências)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Dá permissão e baixa as dependências
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# Agora copia o código fonte e gera o JAR
COPY src ./src
RUN ./gradlew clean assemble -x test --no-daemon

# Estágio de Execução (Imagem final mais leve)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o JAR do estágio de build para a imagem final
# O wildcard * garante que ele pegue o arquivo mesmo que o nome mude levemente
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]