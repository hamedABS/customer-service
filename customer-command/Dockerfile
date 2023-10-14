FROM eclipse-temurin:17
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} customer-command.jar
ENTRYPOINT ["java","-jar","customer-command.jar"]
