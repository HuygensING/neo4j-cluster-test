#!/bin/sh

docker run --publish=8478:7474 --publish=8688:7687 --volume=$PWD/neo4j4/data:/data --env=NEO4J_ha_serverId=4 --env=NEO4J_ha_host_coordination=instance4:5001 --env=NEO4J_ha_host_data=instance4:6001 --name=instance4 --hostname=instance4 --detach --net=cluster --env=NEO4J_ha_initialHosts=instance1:5001,instance2:5001,instance3:5001,instance4:5001 --env=NEO4J_dbms_mode=HA neo4j:enterprise

