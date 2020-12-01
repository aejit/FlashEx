# FROM maven:3.6.2-jdk-12 AS build
FROM prithivid/flashexdepends:latest AS build
COPY google-or-tools-lib/or-tools_Ubuntu-18.04-64bit_v7.4.7247 /usr/local/google-or-tools
RUN mvn install:install-file -Dfile=/usr/local/google-or-tools/lib/com.google.ortools.jar -DgroupId=com.google.ortools -DartifactId=com.google.ortools -Dversion=7.4.7 -Dpackaging=jar
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests=true

FROM gesellix/wait-for as waitfor

FROM openjdk:12-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
COPY --from=waitfor ./wait-for ./wait-for

EXPOSE 6020

ENTRYPOINT [ "./wait-for", "config-server:8888", "-t", "20", "--", "java", "-jar", "/usr/app/app.jar"]
# ENTRYPOINT ["java","-jar","/usr/app/app.jar"]