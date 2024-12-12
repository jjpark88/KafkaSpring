package com.example.kafkaspring.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.kafkaspring.model.Topic.SINGLE_TOPIC;

@Component
public class MySingleConsumer {

    @KafkaListener(
        topics = { "my-json-topic" },
        groupId = "single-consumer-group"
    )

    public void accept(List<String> messages) throws InterruptedException {
        System.out.println("Single consumer message" + messages );
        Thread.sleep(500);
    }







    //수동 커시 설정
//   public void accept(List<String> messages, Acknowledgment acknowledgment) throws InterruptedException {
//        System.out.println("combi3 message" + messages );
//       Thread.sleep(500);
//       acknowledgment.acknowledge();
//   }

}

