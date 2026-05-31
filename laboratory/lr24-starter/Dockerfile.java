# Приклад Dockerfile для Java / Spring Boot (multi-stage).
# Скопіюй у корінь свого проекту під іменем "Dockerfile" і адаптуй версії/шляхи.
# Збірка: docker build -t <прізвище>-coursework:1.0 .
# Запуск: docker run -d -p 8080:8080 <прізвище>-coursework:1.0

# --- етап 1: збірка jar ---
FROM gradle:8-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon
# Maven-варіант:  RUN mvn -q clean package -DskipTests

# --- етап 2: легкий runtime (тільки JRE + готовий jar) ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
# Maven-варіант:  COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
