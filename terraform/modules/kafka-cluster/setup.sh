#!/bin/bash
echo "Starting..."

publicdnslist="${publicdnslist}"

i=1
zk_connect="zookeeper.connect="
for host in $publicdnslist
do
    zk_connect+="$host:2181,"
    # Update Zookeeper config
  echo "server.$i=$host:2888:3888" >> /usr/local/zookeeper/conf/zoo.cfg
  ((i++))
done

# Remove the last comma
zk_connect="$${zk_connect%,}"

# Update Kafka config
sudo sed -i "s/zookeeper.connect=localhost:2181/$${zk_connect}/g" /usr/local/kafka/config/server.properties

# Start Zookeeper
sudo /usr/local/zookeeper/bin/zkServer.sh start

# Start Kafka Server
(sleep 30 && sudo /usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties)&


echo "Finished."