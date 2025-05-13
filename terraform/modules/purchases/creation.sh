#!/bin/bash
echo "Starting..."

echo "DEBUG: kafka_brokers='${kafka_brokers}'" >> /tmp/debug.log
echo "DEBUG: rds_address='${rds_address}'" >> /tmp/debug.log
echo "DEBUG: rds_port='${rds_port}'" >> /tmp/debug.log
echo "DEBUG: db_username='${db_username}'" >> /tmp/debug.log
echo "DEBUG: db_password='${db_password}'" >> /tmp/debug.log
echo "DEBUG: docker_username='${docker_username}'" >> /tmp/debug.log
echo "DEBUG: docker_password='${docker_password}'" >> /tmp/debug.log
echo "DEBUG: debug='${debug}'" >> /tmp/debug.log

kafka_brokers="${kafka_brokers}"

i=1
kafka=""
for host in $kafka_brokers  # Loop through each host in the kafka_brokers variable
do
  kafka+="$host:9092,"
  ((i++))
done

# Remove the last comma
kafka="$${kafka%,}"

sudo yum install -y docker
sudo service docker start
sudo docker login -u "${docker_username}" -p "${docker_password}"
sudo docker pull "${docker_username}"/purchases:1.0.0-SNAPSHOT
sudo docker run -d\
  --name purchases\
  -p 8080:8080\
  -e QUARKUS_DATASOURCE_USERNAME="${db_username}" \
  -e QUARKUS_DATASOURCE_PASSWORD="${db_password}" \
  -e QUARKUS_DATASOURCE_REACTIVE_URL="mysql://${rds_address}:${rds_port}/${db_name}" \
  -e KAFKA_BOOTSTRAP_SERVERS="$kafka" \
   "${docker_username}"/purchases:1.0.0-SNAPSHOT
echo "Finished."