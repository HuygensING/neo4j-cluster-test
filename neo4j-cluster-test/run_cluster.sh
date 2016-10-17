#!/bin/sh

echo "build image"
docker build -t clustertest .
if [ $? -eq 0 ]; then
	echo "create network"
	docker network create --driver=bridge embed_neo4j_cluster
	echo "create instance1"
	docker run --detach --net=embed_neo4j_cluster --publish=8050:8080 --publish=8051:8081 --volume=$PWD/testdatabase1:/testdatabase --name=instance1 --hostname=instance1 --env=DB_MODE=HA --env=SERVER_ID=1 --env=SERVER_NAME=instance1 --env=COORD_PORT=5001 --env=DATA_PORT=6001 --env=INIT_HOSTS=instance1:5001,instance2:5001,instance3:5001  clustertest
	echo "create instance2"
	docker run --detach --net=embed_neo4j_cluster --publish=8060:8080 --publish=8061:8081 --volume=$PWD/testdatabase2:/testdatabase --name=instance2 --hostname=instance2 --env=DB_MODE=HA --env=SERVER_ID=2 --env=SERVER_NAME=instance2 --env=COORD_PORT=5001 --env=DATA_PORT=6001 --env=INIT_HOSTS=instance1:5001,instance2:5001,instance3:5001  clustertest
	echo "create instance3"
	docker run --detach --net=embed_neo4j_cluster --publish=8070:8080 --publish=8071:8081 --volume=$PWD/testdatabase3:/testdatabase --name=instance3 --hostname=instance3 --env=DB_MODE=HA --env=SERVER_ID=3 --env=SERVER_NAME=instance3 --env=COORD_PORT=5001 --env=DATA_PORT=6001 --env=INIT_HOSTS=instance1:5001,instance2:5001,instance3:5001  clustertest
fi
