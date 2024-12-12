package com.example.kafkaspring.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.example.kafkaspring.model.Topic.MY_SECOND_TOPIC;

@Component
public class CommitConsumer {

    @KafkaListener(
        topics = { MY_SECOND_TOPIC },
        groupId = "test-consumer-group",
        containerFactory = "commitkafkaListenerContainerFactory"
    )
    public void accept(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) {
        System.out.println("[commit Consumer] Message arrived! - " + message.value());
        System.out.println("[commit Consumer] Offset - " + message.offset() + " / Partition - " + message.partition());

        acknowledgment.acknowledge();
    }
}
