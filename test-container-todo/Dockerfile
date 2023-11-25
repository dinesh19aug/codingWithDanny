FROM arm32v7/eclipse-temurin:17-jammy
WORKDIR /app
COPY build/libs/test-container-todo-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","test-container-todo-0.0.1-SNAPSHOT.jar"]


