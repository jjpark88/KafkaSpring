package com.example.kafkaspring.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.kafkaspring.model.Topic.MY_SECOND_TOPIC;

@RequiredArgsConstructor
@Component
public class CommitProducer {

    @Qualifier("serialKafkaTemplate")
    private final KafkaTemplate<String, String> serialKafkaTemplate;

    public void sendMessageWithKey(String key, String message) {
        serialKafkaTemplate.send(MY_SECOND_TOPIC, key, message);
    }
}
