FROM openjdk:11

WORKDIR /app

COPY app/build/libs/piet-1.0.jar /app/app.jar

ADD  app/src/main/resources/* /app/src/main/resources/
COPY app/src/main/resources/* /app/src/main/resources/

ENTRYPOINT ["java", "-jar", "app.jar"]