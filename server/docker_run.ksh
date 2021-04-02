docker build -t roygou/movielens-build:latest -t roygou/movielens-build:1.0.4 -f Dockerfile_build .
docker build -t roygou/movielens-backend-single:latest -t roygou/movielens-backend-single:1.0.0 -f Dockerfile .
