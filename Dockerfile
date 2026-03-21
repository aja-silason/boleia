FROM gradle:8.10-jdk23

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./

COPY gradle ./gradle

RUN chmod +x gradlew

RUN ./gradlew dependencies || true
