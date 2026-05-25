FROM clojure:temurin-21-lein-alpine AS builder

WORKDIR /app
COPY project.clj .
RUN lein deps

COPY . .
RUN lein uberjar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/uberjar/tempus-fugit.jar app.jar

EXPOSE 3000
CMD ["java", "-jar", "app.jar"]