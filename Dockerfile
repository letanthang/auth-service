# Build stage
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy thin jar and its dependencies directory
COPY --from=build /app/target/auth-service-1.0.jar /app/app.jar
COPY --from=build /app/target/dependency/ /app/lib/

# Optionally copy resources-only classes if needed at runtime outside the jar
# (usually not needed because they are inside app.jar)
# COPY --from=build /app/target/classes/ /app/classes/

EXPOSE 8080

# Set classpath: app jar + all libs
ENTRYPOINT ["java","-cp","/app/app.jar:/app/lib/*","com.example.authservice.Main"]