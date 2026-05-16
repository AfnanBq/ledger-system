# Step 1: Build stage
FROM eclipse-temurin:17 AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the JAR (clean + build)
RUN ./gradlew clean build

# Step 2: Run stage
FROM eclipse-temurin:17

# Create non-root user
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]