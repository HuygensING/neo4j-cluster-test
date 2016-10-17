# Neo4jClusterTest

## How to start the Neo4jClusterTest application

1. Run `run_cluster.sh` to build and deploy a cluster of the application.

This will start 3 docker contains with the names 'instance1', 'instance2' and 'instance3'.
'instance1' can be reached on 'localhost:8050'.
'instance2' can be reached on 'localhost:8060'.
'instance3' can be reached on 'localhost:8070'.

All 3 container have a database called testdatabase with a number.
'instance1' will use 'testdatabase1' as database.
 
### Resources
All three instances have the following (REST) resources available:
* GET /dbaccess/getAllVertices
* GET /dbaccess/getVertices/{name_of_vertex}
* GET /dbaccess/addVertex/{name_of_vertex}

## Prerequisites
Both parts of this repository use docker make sure you have installed this on you computer. 
For more information: https://www.docker.com/products/overview
