# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Install netcat
RUN apt-get update && apt-get install -y netcat

# Set the working directory inside the container
WORKDIR /app

# Copy the environment.properties file to the container
COPY src/main/resources/environment.properties /app/environment.properties

# Copy the wait-for-it.sh script to the container
COPY src/main/resources/wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Copy the compiled JAR file to the container
COPY target/*.jar app.jar

# Expose the port specified in the environment.properties file
EXPOSE 8082

# Set the environment variable for the active profile
ENV SPRING_PROFILES_ACTIVE=debug

# Run the Spring Boot application with the environment.properties file
CMD ["/app/wait-for-it.sh", "db", "3306", "--", "java", "-jar", "app.jar", "--spring.config.location=file:///app/environment.properties"]