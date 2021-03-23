#
# Build stage
#
FROM roygou/movielens:base-latest AS build
RUN rm -fr /home/gradle/src/*
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle -P/home/repo/gradle -PGRADLE_REPO_DIR=/home/repo/gradle build copyDep > gradle_build.log

#
# Package stage
#
FROM openjdk:15-oracle
RUN mkdir /home/app
RUN mkdir /home/gradle
COPY --from=build /home/gradle/src/build/libs/*jar /home/app/
COPY --from=build /home/repo/gradle /home/repo/gradle
#WORKDIR /home/app
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/MovieLens-1.0-SNAPSHOT.jar"]