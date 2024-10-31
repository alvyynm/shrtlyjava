# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file to the container
COPY target/url-shortener-1.2.0.jar app.jar

# Expose the port that the Spring Boot app runs on (default is 8080)
EXPOSE 8080

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
