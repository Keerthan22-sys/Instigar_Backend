## Use a lighter base image for building
#FROM maven:3.9-eclipse-temurin-17-alpine AS build
#WORKDIR /app
#
## Copy and download dependencies separately for better caching
#COPY pom.xml .
#RUN mvn dependency:go-offline -B
#
## Copy source and build
#COPY src ./src
#RUN mvn clean package -DskipTests -B -q
#
## Runtime stage with optimizations
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#
## Copy the built jar
#COPY --from=build /app/target/*.jar app.jar
#
## Add a non-root user for security
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#
## Set Java options for faster startup and lower memory
#ENV JAVA_OPTS="-XX:+UseSerialGC -Xss512k -XX:MaxRAM=512m -Dspring.backgroundpreinitializer.ignore=true"
#
#EXPOSE 8080
#
## Use exec form and optimize startup
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=prod -Dserver.port=${PORT} -jar app.jar"]

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
ENV JAVA_OPTS="-Xmx400m -Xms256m"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=prod -jar app.jar"]

