# Use Eclipse Temurin JDK 24 base image
FROM eclipse-temurin:24-jdk-alpine

# Set environment variables
ENV APP_HOME=/app \
    JAVA_OPTS=""

# Create application directory
WORKDIR $APP_HOME

# Copy the jar file (adjust the filename if needed)
COPY target/tutornet-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]