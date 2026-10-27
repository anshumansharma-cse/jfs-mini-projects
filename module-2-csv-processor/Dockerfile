FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY src ./src
COPY data ./data
RUN javac -d out src/config/*.java src/model/*.java src/processor/*.java src/report/*.java src/Main.java
CMD ["java", "-cp", "out", "Main"]
