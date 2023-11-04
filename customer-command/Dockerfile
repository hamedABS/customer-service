FROM eclipse-temurin:17.0.8.1_1-jre-focal as builder
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} customer-command.jar
RUN java -Djarmode=layertools -jar customer-command.jar extract
FROM eclipse-temurin:17.0.8.1_1-jre-focal
RUN useradd spring
USER spring
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./


ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
