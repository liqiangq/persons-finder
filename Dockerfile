FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY src src

RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/PersonsFinder-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=10000
EXPOSE 10000

CMD ["java", "-jar", "/app/app.jar"]
