FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon || true

COPY src src

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine AS runner


RUN addgroup -S spring && adduser -S spring -G spring

USER spring
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]