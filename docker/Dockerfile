FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY mvnw ./
COPY .mvn ./.mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

WORKDIR /app

COPY --from=builder /app/target/lunchtech-1.0-SNAPSHOT.jar /app/lunchtech.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "lunchtech.jar"]