FROM amazoncorretto:21-alpine-jdk AS builder

WORKDIR /app

COPY gradle ./gradle
COPY gradlew gradlew

RUN chmod +x ./gradlew

COPY build.gradle.kts settings.gradle.kts ./

CMD ./gradlew dependencies

COPY src /app/src

RUN ["./gradlew", "build", "-x", "test"]

RUN tree

FROM amazoncorretto:21-alpine AS runner

RUN apk add --no-cache tzdata curl
ENV TZ=Europe/Moscow

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app-runnable.jar

RUN tree

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/app-runnable.jar"]