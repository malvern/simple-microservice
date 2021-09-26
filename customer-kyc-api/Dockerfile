FROM openjdk:16-jdk-slim as builder
WORKDIR apps
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-backend-service.jar
RUN java -Djarmode=layertools -jar user-backend-service.jar extract
FROM openjdk:16-jdk-slim
WORKDIR apps
COPY --from=builder apps/dependencies/ ./
COPY --from=builder apps/spring-boot-loader/ ./
COPY --from=builder apps/snapshot-dependencies/ ./
COPY --from=builder apps/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
LABEL  developer=malvern