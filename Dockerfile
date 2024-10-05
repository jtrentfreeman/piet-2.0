FROM openjdk:11

WORKDIR /app

COPY app/build/libs/piet-1.0.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]