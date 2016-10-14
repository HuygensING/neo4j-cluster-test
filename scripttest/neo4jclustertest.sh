#!/bin/sh

docker network create --driver=bridge cluster
echo "create instance1"
docker run --publish=8475:7474 --publish=8685:7687 --volume=$PWD/neo4j1/data:/data --env=NEO4J_ha_serverId=1 --env=NEO4J_ha_host_coordination=instance1:5001 --env=NEO4J_ha_host_data=instance1:6001 --name=instance1 --hostname=instance1 --detach --net=cluster --env=NEO4J_ha_initialHosts=instance1:5001,instance2:5001,instance3:5001 --env=NEO4J_dbms_mode=HA neo4j:enterprise
echo "create instance2"
docker run --publish=8476:7474 --publish=8686:7687 --volume=$PWD/neo4j2/data:/data --env=NEO4J_ha_serverId=2 --env=NEO4J_ha_host_coordination=instance2:5001 --env=NEO4J_ha_host_data=instance2:6001 --name=instance2 --hostname=instance2 --detach --net=cluster --env=NEO4J_ha_initialHosts=instance1:5001,instance2:5001,instance3:5001 --env=NEO4J_dbms_mode=HA neo4j:enterprise
echo "create instance3"
docker run --publish=8477:7474 --publish=8687:7687 --volume=$PWD/neo4j3/data:/data --env=NEO4J_ha_serverId=3 --env=NEO4J_ha_host_coordination=instance3:5001 --env=NEO4J_ha_host_data=instance3:6001 --name=instance3 --hostname=instance3 --detach --net=cluster --env=NEO4J_ha_initialHosts=instance1:5001,instance2:5001,instance3:5001 --env=NEO4J_dbms_mode=HA neo4j:enterprise

