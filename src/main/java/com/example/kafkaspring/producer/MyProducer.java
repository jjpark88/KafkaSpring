package com.example.kafkaspring.producer;

import com.example.kafkaspring.model.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyProducer {

    private final KafkaTemplate<Object, String> kafkaTemplate;

    public void sendMessage(MyMessage myMessage) throws InterruptedException {
        //kafkaTemplate.send("json-topic", String.valueOf(myMessage.getAge()), myMessage);
        for (int i = 1; i <= 3; i++) {

            kafkaTemplate.send("single_listner", "Message combi4" + i);
            System.out.println("Sent: Message combi4 " + i);
        }
    }
}

