package com.example.kafkaspring.consumer;

import com.example.kafkaspring.model.MyMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.kafkaspring.model.Topic.*;


@Component
public class BatchConsumer {
    @KafkaListener(
            topics = { SINGLE_TOPIC },
            groupId = "batch-consumer-group", // MyConsumer의 groupId와 반드시 달라야 함!
            containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void listenBatch(@Payload List<ConsumerRecord<String, String>> records) {

        System.out.println("combi2 size: " + records.size());
            System.out.println("combi2: " + records);

        // 각 ConsumerRecord에서 파티션 정보 추출
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("Received message from partition: " + record.partition() + ", offset: " + record.offset());
        }
    }
}
