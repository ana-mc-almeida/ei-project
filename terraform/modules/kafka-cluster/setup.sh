#!/bin/bash
echo "Starting..."

privatednslist="${privatednslist}"
kafka_started_flag="/tmp/kafka_started"

i=1
zk_connect="zookeeper.connect="
for host in $privatednslist
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

# Install nc
sudo yum install nc -y

echo "Starting Zookeeper..."
sudo /usr/local/zookeeper/bin/zkServer.sh start

echo "Waiting for Zookeeper to become active..."
while ! nc -z localhost 2181; do
  echo "Waiting for Zookeeper to become active..."
  sleep 2
done

# wait for all Zookeeper nodes to start
for host in $privatednslist; do
  while ! nc -z $host 2181; do
    echo "Waiting for Zookeeper on $host..."
    sleep 2
  done
done

# give time for leader election to happen
echo "All Zookeeper nodes are up. Waiting for leader election..."
sleep 15

# get the zookeeper mode
zookeeper_mode=$(sudo /usr/local/zookeeper/bin/zkServer.sh status)

if echo "$zookeeper_mode" | grep -q "Mode: leader"; then
  echo "This node is the Zookeeper LEADER. Starting Kafka..."
  sudo /usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties
  touch $kafka_started_flag
else
  echo "This node is a Zookeeper FOLLOWER. Waiting for Kafka leader to start..."

  # Wait for Kafka leader to start
  sleep 15

  echo "Starting Kafka on follower node..."
  sudo /usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties
  touch $kafka_started_flag
fi


echo "Finished."