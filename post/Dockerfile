FROM openjdk:11

ARG JAR_FILE=build/libs/post-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} post-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["nohup", "java","-jar","post-0.0.1-SNAPSHOT.jar &"]