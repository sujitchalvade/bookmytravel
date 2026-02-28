# Use official Tomcat 9 with Java 21 pre-installed
FROM tomcat:11.0.10-jdk21-temurin-noble

# Set maintainer label (optional but good practice)
LABEL maintainer="sujit.chalvade@example.com"

# Remove default ROOT app (optional, keeps container clean)
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Create a user for running the application
RUN useradd -m bookmyplan

# Copy your JAR file into the webapps directory
COPY ./target/bookmyplan*.jar /usr/local/tomcat/webapps/

# Expose the default Tomcat port
EXPOSE 8080

# Set the user to 'bookmyplan' for security
USER bookmyplan

# Default command to run Tomcat
CMD ["catalina.sh", "run"]