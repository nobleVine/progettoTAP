FROM openjdk:8
COPY /target/negozio-redis-0.0.1-SNAPSHOT-all.jar appredis/appredis.jar
WORKDIR /appredis
CMD ["java", "-jar", "appredis.jar", "redis-host"]