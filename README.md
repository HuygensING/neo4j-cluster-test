# Neo4j cluster test
This repository is the result of investigating the way a Neo4j cluster works.

The repository is split in two, [a Java application](./neo4j-cluster-test) and [test scripts](./scripttest). 
The tests scipts are a scripted version of: http://neo4j.com/docs/operations-manual/current/deployment/single-instance/docker/ 
The Java application is a DropWizard application with an embedded Neo4j included.

## Prerequisites
Both parts of this repository use docker make sure you have installed this on you computer. 
For more information: https://www.docker.com/products/overview

## Links
* http://neo4j.com/docs/stable/ha-configuration.html
