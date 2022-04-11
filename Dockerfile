FROM openjdk:17
ADD target/Docker-Like.jar Docker-Like.jar
EXPOSE 3020
ENTRYPOINT ["java","-jar","Docker-Like.jar"]