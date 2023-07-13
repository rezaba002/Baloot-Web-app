# Use the official Maven image as the build environment
FROM maven:3.8.3-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Create a new image using AdoptOpenJDK as the base
FROM openjdk:17-jdk AS runtime

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/baloot6backend-0.0.1-SNAPSHOT.jar .

# Expose the port your application is listening on (change it if necessary)
EXPOSE 8080

# Set the entrypoint command to run your application
CMD ["java", "-jar", "baloot6backend-0.0.1-SNAPSHOT.jar"]