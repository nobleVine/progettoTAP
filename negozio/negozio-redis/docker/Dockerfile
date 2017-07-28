FROM openjdk:8
ARG fatjar
COPY /maven/$fatjar /appredis/appredis.jar
WORKDIR /appredis
CMD ["java", "-jar", "appredis.jar", "redis-host"]