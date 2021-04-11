# Kafka-Nifi-Spring-Boot

Setup everything on Windows.

## Tech Stack

```
Read about 
1. springboot
2. Kafka + Zookeeper
3. Apache Nifi - https://www.youtube.com/playlist?list=PL55symSEWBbMBSnNW_Aboh2TpYkNIFMgb
```

## Installations

```
- Install Java.
- Configure path variable JAVA_HOME in properties.
    - https://mkyong.com/java/how-to-set-java_home-on-windows-10/
- Install intellij IDEA community free version
```

### Nifi

```
- Download zip apache nifi 
    - nifi-1.13.2-bin.zip ( asc, sha256, sha512 ) Download binary zip format.
- extract to any drive. For example - C:\nifi-1.13.2\bin
```

```bash
# Run Nifi

# Open cmd in the parent folder
bin\run-nifi.bat
http://localhost:8080/nifi/
```

### Kafka + Zookeeper

```
- Download zip for kafka 
    - https://kafka.apache.org/quickstart
```

```bash
# Run kafka

1. zookeeper server - bin\windows\zookeeper-server-start.bat config\zookeeper.properties
2. kafka - bin\windows\kafka-server-start.bat config\server.properties

# References - Not needed to be used
- In zookeeper folder server - bin\zkServer.cmd
- http://cloudurable.com/blog/kafka-tutorial-kafka-from-command-line/index.html
```

```
# Create Kafka Topics

## create - 
    bin\windows\kafka-topics.bat --create --zookeeper localhost:2181
     --replication-factor 1 --partitions 1 --topic topic1
    OR
    bin\windows\kafka-topics.bat --create --topic quickstart-events 
    --bootstrap-server localhost:9092

## see all topics - 
    bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
    OR
    bin\windows\kafka-topics.bat --describe --bootstrap-server localhost:9092

## deleting a topic
    - https://stackoverflow.com/a/33538299/8110072

# Below 2 commands needs to be run on 2 different terminals.

start sending data from a producer -
    bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic topic1
    OR
    bin\windows\kafka-console-producer.bat --topic topic1 --bootstrap-server localhost:9092

see consumer data - 
    bin\windows\kafka-console-consumer.bat --topic topic1 --from-beginning --bootstrap-server localhost:9092

At this point 4 terminals would be running.
1. zookeeper broker
2. kafka server
3. producer
4. consumer

```

```
# Debugging

1. Port number issue shows up frequently
- To fix the issue use the property admin.serverPort in the "zoo.cfg" file to set any different 
port: admin.serverPort=8081 but be sure that the port 8081 in the example is not used.

2. https://stackoverflow.com/questions/56465830/failed-to-construct-kafka-producer-with-springboot
```

## Reference Commands

```cmd
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-name

bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
```
