# create image with mounted volume
docker run -it --name movielens -p80:8080 --mount type=bind,source=/Users/gourab/gradle/repo,target=/home/repo/gradle roygou/movielens:latest

docker rm test
docker rmi test

docker build --build-arg GRADLE_REPO_DIR=/Users/gourab/gradle/repo -t test .

docker run -d --name test -p80:8080 --mount type=bind,source=/Users/gourab/gradle/repo,target=/home/repo/gradle test

docker exec -it test /bin/bash


docker build -t roygou/movielens-build:latest -t roygou/movielens-build:1.0.0 -f Dockerfile_build .
docker build -t roygou/movielens-backend:latest -t roygou/movielens-backend:1.0.0 -f Dockerfile .