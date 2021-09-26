#!/usr/bin/env bash

function mavenBuild() {
   echo multiple module maven build started ...
   mvn clean install
   status=$?
   if [ $status -ne 0 ]
     then
         echo maven build failed......
         exit 1
   fi
   echo Multiple module maven build completed with success
}
function dockerBuildAndStart() {
   echo Building containers and Starting them
   # shellcheck disable=SC2164
   cd infrastructure/docker/
   docker-compose kill || echo "No docker containers are running"
   docker-compose up -d
    if [ $status -ne 0 ]
       then
         echo Failed to start applications
    fi
    echo Applications have been successfully deployed on Docker
}
mavenBuild
dockerBuildAndStart
echo Opening your browser with swagger-api
sleep 40
URL="http://localhost:8000/user/swagger-ui.html"; xdg-open $URL || sensible-browser $URL || x-www-browser $URL || gnome-open $URL || open $URL


