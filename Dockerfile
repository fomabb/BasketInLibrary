# Build target
FROM maven:3.8-amazoncorretto-21-al2023 AS builder
WORKDIR /app

# Копируем pom.xml и директорию с исходным кодом
COPY pom.xml ./pom.xml
COPY src ./src

# Собираем проект
RUN mvn clean package -Dmaven.test.skip=true

# Develop runtime target
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем скомпилированный JAR файл
COPY --from=builder /app/target/*.jar /app/*.jar

# Открываем порт и указываем команду запуска
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
