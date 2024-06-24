FROM maven:openjdk AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/HotelBooking-0.0.1-SNAPSHOT.jar HotelBooking.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","HotelBooking.jar"]