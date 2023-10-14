FROM eclipse-temurin:17
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} customer-query.jar
ENTRYPOINT ["java","-jar","customer-query.jar"]
