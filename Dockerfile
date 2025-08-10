FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests && ls -l /app/target

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/TechChallenge-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
