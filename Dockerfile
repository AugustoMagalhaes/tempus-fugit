FROM openjdk:8-alpine

COPY target/uberjar/tempus-fugit.jar /tempus-fugit/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/tempus-fugit/app.jar"]
