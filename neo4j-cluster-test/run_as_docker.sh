#!/bin/sh

docker build -t clustertest .
if [ $? -eq 0 ]; then
	docker run --detach --publish=9080:8080 --publish=9081:8081 clustertest
fi
