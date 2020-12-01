# FROM maven:3.6.2-jdk-12 AS build
FROM prithivid/flashexdepends:latest AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests=true

FROM gesellix/wait-for as waitfor

FROM openjdk:12-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
COPY --from=waitfor ./wait-for ./wait-for

EXPOSE 7030

ENTRYPOINT [ "./wait-for", "config-server:8888", "-t", "20", "--", "java", "-jar", "/usr/app/app.jar"]
# ENTRYPOINT ["java","-jar","/usr/app/app.jar"]