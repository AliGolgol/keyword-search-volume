FROM maven:3.3-jdk-8 AS build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/app/target/amazon-search-volume.jar /usr/app/amazon-search-volume.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/app/amazon-search-volume.jar"]