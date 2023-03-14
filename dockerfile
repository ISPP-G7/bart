FROM openjdk:11-jdk
ADD target/SpringBootPostgresCRUD-0.0.1-SNAPSHOT.jar /usr/share/app.jar
ENTRYPOINT ["java", "-jar", "/usr/share/app.jar"]