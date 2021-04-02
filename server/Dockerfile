#
# Build stage
#
FROM gradle:jdk11 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle -P/home/repo/gradle -PGRADLE_REPO_DIR=/home/repo/gradle build copyDep > gradle_build.log

#
# Package stage
#
#FROM openjdk:15-oracle
FROM openjdk:11.0.10-jre-slim
ENV SPRING_PROFILE=dev
RUN mkdir /home/app
RUN mkdir /home/repo
COPY --from=build /home/gradle/src/build/libs/*jar /home/app/
COPY --from=build /home/repo/gradle /home/repo/gradle
COPY --from=build /home/gradle/src/gradle_build.log /home/app/
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/home/app/MovieLens-1.0-SNAPSHOT.jar"]