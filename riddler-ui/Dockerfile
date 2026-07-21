FROM eclipse-temurin:25.0.3_9-jre-ubi10-minimal as builder
RUN useradd -rm -d /extract -s /bin/bash app
USER app
WORKDIR extract
COPY target/*.jar /extract/
RUN mv /extract/*.jar application.jar
RUN echo ls /extract
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:25.0.3_9-jre-ubi10-minimal
RUN useradd -rm -d /application -s /bin/bash app
USER app
WORKDIR application

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE prod

COPY --from=builder extract/dependencies/ ./
COPY --from=builder extract/spring-boot-loader/ ./
COPY --from=builder extract/snapshot-dependencies/ ./
COPY --from=builder extract/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
