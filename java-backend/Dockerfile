FROM gradle:9.1-jdk21-alpine AS builder

WORKDIR /app

COPY ./build.gradle.kts /app/
COPY ./settings.gradle.kts /app/

RUN gradle dependencies

COPY ./src /app/src

RUN ls
RUN tree

RUN ["gradle", "build", "-x", "test"]

RUN tree

FROM amazoncorretto:21-alpine AS runner

RUN apk add --no-cache tzdata curl
ENV TZ=Europe/Moscow

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app-runnable.jar

RUN tree

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/app-runnable.jar"]