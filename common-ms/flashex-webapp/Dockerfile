FROM node:latest as angular_build
COPY src /usr/app/src
COPY flashex-webapp /usr/app/frontend
COPY pom.xml /usr/app
WORKDIR /usr/app/frontend
RUN ls -la
RUN npm install -g @angular/cli
RUN npm install
RUN ls
RUN ng build

# FROM maven:3.6.2-jdk-12 AS build`
FROM prithivid/flashexdepends:latest AS build
COPY --from=angular_build /usr/app /usr/app
RUN rm -rf /usr/app/src/main/resources/static
RUN mkdir /usr/app/src/main/resources/static
COPY --from=angular_build /usr/app/frontend/dist/* /usr/app/src/main/resources/static
RUN ls /usr/app/src/main/resources/static
RUN mvn -f /usr/app/pom.xml clean package -DskipTests=true

FROM gesellix/wait-for as waitfor

FROM openjdk:12-alpine
COPY --from=build /usr/app/target/*.jar /usr/app/app.jar
COPY --from=waitfor ./wait-for ./wait-for

EXPOSE 4200

ENTRYPOINT [ "./wait-for", "config-server:8888", "-t", "20", "--", "java","-jar","usr/app/app.jar" ]
#ENTRYPOINT ["java","-jar","usr/app/app.jar"]
# COPY "${pwd}" /gateway/


#FROM maven:3.6.2-jdk-12 AS build
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#RUN mvn -f /usr/src/app/pom.xml dependency:go-offline
#RUN mvn -f /usr/src/app/pom.xml clean package
#
#FROM openjdk:12-alpine
#COPY wait-for/wait-for wait-for
#COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
#EXPOSE 8181
#
#ENTRYPOINT [ "./wait-for", "config-server:8888", "-t", "20", "--", "java", "-jar", "/usr/app/app.jar"]
