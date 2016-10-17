#!/bin/sh
echo "stop containers"
docker stop instance1 instance2 instance3
echo "remove containers"
docker rm instance1 instance2 instance3
echo "remove network"
docker network rm embed_neo4j_cluster
