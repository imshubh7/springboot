# Use the official OpenJDK 11 image as the base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/sdelab-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your application will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
