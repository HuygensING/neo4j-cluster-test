#!/bin/sh

echo "--stop servers--"
docker stop  instance4 instance3 instance2 instance1
echo "--remove servers--"
docker rm instance4 instance3 instance2 instance1
echo "--remove network--"
docker network  rm cluster
