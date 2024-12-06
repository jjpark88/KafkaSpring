package com.example.kafkaspring.consumer;

import ch.qos.logback.classic.spi.ConfiguratorRank;
import com.example.kafkaspring.model.MyMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.kafkaspring.model.Topic.BATCH_TOPIC;
import static com.example.kafkaspring.model.Topic.SINGLE_TOPIC;

@Component
public class MyConsumer {

    @KafkaListener(
        topics = { SINGLE_TOPIC },
        groupId = "batch-combi-consumer-group"
    )
   public void accept(List<String> messages) throws InterruptedException {
        System.out.println("combi3 message" + messages );
       Thread.sleep(500);
   }

}

