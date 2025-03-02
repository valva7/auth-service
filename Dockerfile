FROM amazoncorretto:17
EXPOSE 8080
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]