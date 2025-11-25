# Build stage: Compile and package the app
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
# Copy Maven files first for better caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
# Copy source code
COPY src ./src
# Build the JAR (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Runtime stage: Run the app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Optional: Set PORT for Render (though Render handles it)
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]