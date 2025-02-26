# Use the official OpenJDK image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build context to the container
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Define the command to run the app
CMD ["java", "-jar", "app.jar"]
