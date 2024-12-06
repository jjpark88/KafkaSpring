package com.example.kafkaspring.producer;

import com.example.kafkaspring.model.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BatchProducer {

    private final KafkaTemplate<Object, String> kafkaTemplate;

    public void sendMessage() {
        for (int i = 1; i <= 20; i++) {
            kafkaTemplate.send("batch_test_topic2", "Message " + i);
            System.out.println("Sent: Message " + i);
        }
    }
}
