FROM openjdk:8-jre-alpine
COPY build/libs/firebase-checker-1.0.0.jar /firebase-checker.jar
CMD ["/usr/bin/java", "-jar", "/firebase-checker.jar"]
