#
# Build stage
#
FROM roygou/movielens-build:latest as build

#
# Package stage
#
FROM openjdk:15-oracle
RUN mkdir /home/app
RUN mkdir /home/repo
COPY --from=build /home/gradle/src/build/libs/*jar /home/app/
COPY --from=build /home/repo/gradle /home/repo/gradle
COPY --from=build /home/gradle/src/gradle_build.log /home/app/
ENTRYPOINT ["java", "-jar", "/home/app/MovieLens-1.0-SNAPSHOT.jar"]