#!/bin/bash
eval `ssh-agent -s`
cd checkly
git stash
git checkout .
git checkout master
git pull
(cd backend && mvn clean install)
(cd frontend && yarn install)
docker buildx build --platform linux/amd64 -t backend-checkly ./backend
docker buildx build --platform linux/amd64 -t nginx-checkly ./frontend
docker save backend-checkly -o backend-checkly.tar
docker save nginx-checkly -o nginx-checkly.tar
ssh $1@$2 'docker stop nginx-checkly backend-checkly postgres-checkly'
ssh $1@$2 'docker rm nginx-checkly backend-checkly postgres-checkly'
ssh $1@$2 'docker rmi nginx-checkly backend-checkly postgres:14.2-alpine'
scp './backend-checkly.tar' $1@$2:/home/$1/backend-checkly.tar
scp './nginx-checkly.tar' $1@$2:/home/$1/nginx-checkly.tar
scp './docker-compose.yml' $1@$2:/home/$1/docker-compose.yml
scp './backend/src/etc/prod/service.properties' $1@$2:/home/$1/backend/src/etc/prod/service.properties
scp './backend/src/etc/prod/hibernate.properties' $1@$2:/home/$1/backend/src/etc/prod/hibernate.properties
scp './frontend/nginx-config/nginx-prod.conf' $1@$2:/home/$1/frontend/nginx-config/nginx-prod.conf
ssh $1@$2 "docker load -i /home/$1/backend-checkly.tar"
ssh $1@$2 "docker load -i /home/$1/nginx-checkly.tar"
ssh $1@$2 'docker-compose up'
$SHELL
