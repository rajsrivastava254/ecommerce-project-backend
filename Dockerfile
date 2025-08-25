FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY ecom-project/pom.xml ./
COPY ecom-project/src ./src
WORKDIR /app
RUN mvn -f pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar"]
