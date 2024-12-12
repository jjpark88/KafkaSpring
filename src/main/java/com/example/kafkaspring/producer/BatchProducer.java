package com.example.kafkaspring.producer;

import com.example.kafkaspring.model.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BatchProducer {

    @Qualifier("batchKafkaTemplate")
    private final KafkaTemplate<String, String> batchKafkaTemplate;

    public void sendMessage() {
        for (int i = 1; i <= 5; i++) {
            batchKafkaTemplate.send("batch_topic","Message " + i);
            System.out.println("Sent: BatchMessage " + i);
        }
    }
}
