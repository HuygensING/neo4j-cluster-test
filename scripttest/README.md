# Script tests

These scripts are a scripted version of: http://neo4j.com/docs/operations-manual/current/deployment/single-instance/docker/

## How to use
1. Run `noe4jclustertest.sh` (this will start a docker network and 3 containers)
1. Run `addnewtocluster.sh` (this will add a fourth container to the cluster.

After both scripts are run the following uri's are available:
* localhost:8475
* localhost:8476
* localhost:8477

All these uri's will show a Neo4j Cypher interface.

## Clean up
Run `removecluster.sh` to stop and remove the cluster from your docker server.

## Prerequisites
Both parts of this repository use docker make sure you have installed this on you computer.
For more information: https://www.docker.com/products/overview
