FROM openjdk:8
ARG fatjar
COPY /maven/$fatjar apporient/apporient.jar
WORKDIR /apporient
CMD ["java", "-jar", "apporient.jar"]