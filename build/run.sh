#!/usr/bin/env bash
set -e
docker pull neo4j:3.1.0
docker run --detach --publish=7474:7474 --volume=$HOME/neo4j/data:/data --env=NEO4J_AUTH=neo4j/toto neo4j
# Wait up to 30s for Neo4j to become available
for i in {1..30}; do
    curl -s localhost:7474/db/data -o /dev/null && break
    sleep 1
done
mvn -T4 clean verify
