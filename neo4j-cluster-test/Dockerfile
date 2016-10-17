FROM maven:3.3-jdk-8

WORKDIR /app/neo4j-cluster-test

COPY config.yml ./
COPY pom.xml ./
COPY src ./src
RUN mvn clean package

CMD ["./target/appassembler/bin/test_cluster", "server", "./config.yml"]

