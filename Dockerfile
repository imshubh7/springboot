# Use the official Maven image as the build image
FROM maven:3.8.4-openjdk-11-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the POM file to resolve dependencies
COPY pom.xml .

# Download dependencies and plugins to build cache
RUN mvn dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the JAR file
RUN mvn package -DskipTests

# Use the official OpenJDK 11 image as the runtime image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime image
COPY --from=builder /app/target/sdelab-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port that your application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
