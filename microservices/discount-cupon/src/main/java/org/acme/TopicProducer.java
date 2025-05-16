package org.acme;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

import org.acme.model.Topic;

import java.util.Properties;


public class TopicProducer extends Thread  {

    private String kafka_servers;
    private Topic topic;

    private Producer<String, String> producer;

    public TopicProducer(Topic topic_received , String kafka_servers_received)  
    {
        topic = topic_received;
        kafka_servers = kafka_servers_received;

        try {
            Properties properties = new Properties();
            properties.put("bootstrap.servers", kafka_servers);
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            producer = new KafkaProducer<>(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic.TopicName, message);

        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
