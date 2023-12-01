# Use the official Maven image as the build image
FROM maven AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the POM file to resolve dependencies
COPY pom.xml .

# Download dependencies and plugins to build cache
RUN mvn dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the JAR file
RUN mvn clean install

# Use the official OpenJDK 11 image as the runtime image
FROM openjdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime image
COPY --from=builder /app/target/sdelab-0.0.1-SNAPSHOT.jar ./app.jar

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"

# Command to run the application
CMD ["java", "-jar", "app.jar"]
