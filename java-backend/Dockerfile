FROM amazoncorretto:21-alpine-jdk AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

COPY src /app/src

RUN ["./gradlew", "build"]

RUN tree

FROM amazoncorretto:21-alpine AS runner

RUN apk add --no-cache tzdata
ENV TZ=Europe/Moscow

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app-runnable.jar

RUN tree

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/app-runnable.jar"]