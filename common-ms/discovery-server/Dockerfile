# FROM maven:3.6.2-jdk-12 AS build
FROM prithivid/flashexdepends:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests=true

FROM openjdk:12-jdk
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
