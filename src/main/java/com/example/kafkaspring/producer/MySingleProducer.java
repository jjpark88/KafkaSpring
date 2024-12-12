package com.example.kafkaspring.producer;

import com.example.kafkaspring.model.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MySingleProducer {

    @Qualifier("singleKafkaTemplate")
    private final KafkaTemplate<String, MyMessage> singleKafkaTemplate;

    public void sendMessage(MyMessage myMessage) throws InterruptedException {
        singleKafkaTemplate.send("my-json-topic", String.valueOf(myMessage.getAge()), myMessage);
        System.out.println("Message sent : Single producer");
    }
}

