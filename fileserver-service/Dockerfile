# Set the base image
FROM openjdk:11-jdk-slim

# Add Maintainer Info
LABEL maintainer="rogerndutiye@gmail.com"

# Set the working directory
WORKDIR /app

# Copy the application jar file and all necessary files to the working directory
COPY target/fileserver-service-0.0.1-SNAPSHOT.jar /app/file-upload-service.jar
COPY src/main/resources/application.properties /app

# Expose the port that the application listens on
EXPOSE 8050

# Start the application
ENTRYPOINT ["java", "-jar", "file-upload-service.jar"]
