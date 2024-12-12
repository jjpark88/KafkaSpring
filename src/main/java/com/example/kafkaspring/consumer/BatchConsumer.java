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
            topics = { "batch_topic" },
            groupId = "batch-consumer-group",
            containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void listenBatch(@Payload List<ConsumerRecord<String, String>> records) {

        System.out.println("batcMessage size: " + records.size());
        System.out.println("batchMessage: " + records);






//        for (ConsumerRecord<String, String> record : records) {
//            System.out.println(record.topic() + record.value());
//        }
        // 각 ConsumerRecord에서 파티션 정보 추출
//        for (ConsumerRecord<String, String> record : records) {
//            System.out.println("Received message from partition: " + record.partition() + ", offset: " + record.offset());
//        }
    }
}
