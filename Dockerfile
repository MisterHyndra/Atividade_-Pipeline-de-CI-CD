# Multi-stage Dockerfile para aplicação Spring Boot
# Estágio 1: Build da aplicação
FROM eclipse-temurin:17-jdk-alpine AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Compilar e empacotar a aplicação
RUN ./mvnw clean package -DskipTests

# Estágio 2: Runtime da aplicação
FROM eclipse-temurin:17-jre-alpine AS runtime

# Criar usuário não-root para segurança
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR da aplicação do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Alterar propriedade do arquivo para o usuário da aplicação
RUN chown appuser:appgroup app.jar

# Mudar para usuário não-root
USER appuser

# Expor porta da aplicação
EXPOSE 8080

# Configurar variáveis de ambiente
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
