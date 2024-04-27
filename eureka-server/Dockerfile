FROM maven:3.9.6-eclipse-temurin-11 as build
WORKDIR /opt/demo
COPY pom.xml .
COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17-alpine
EXPOSE 8761
WORKDIR /opt/app
COPY --from=build /opt/app/target/eureka.jar /opt/app
ENTRYPOINT ["java","-jar","/eureka.jar"]
