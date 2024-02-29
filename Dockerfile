FROM openjdk:21
ADD /target/MyBOt-1.0.0.jar backend.jar
ENTRYPOINT["java","-jar","backend.jar"]