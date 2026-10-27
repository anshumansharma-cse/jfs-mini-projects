# Use a multi-stage build for a compact production image
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml; resolve dependencies first (layer caching)
COPY pom.xml .
COPY .mvn .mvn
RUN ls .mvn/wrapper/ 2>/dev/null || true

# Copy source and package
COPY src src
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the application jar
COPY --from=build /app/target/*.jar app.jar

# Prepare data directory for H2 file-mode persistence
RUN mkdir -p /app/data && chown -R appuser:appgroup /app/data

USER appuser
EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV JWT_SECRET=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
