package com.example.kafkaspring.api;

import com.example.kafkaspring.model.MyMessage;
import com.example.kafkaspring.producer.BatchProducer;
import com.example.kafkaspring.producer.MyProducer;
import com.example.kafkaspring.producer.MySecondProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyController {

    private final MyProducer myProducer;
    private final MySecondProducer mySecondProducer;
    private final BatchProducer batchProducer;

    @RequestMapping("/hello")
    String hello() {
        return "Hello World";
    }

    @PostMapping("/message")
    void message(
        @RequestBody MyMessage message
    ) throws Exception {
        myProducer.sendMessage(message);
    }

    @PostMapping("/batchMessage")
    void message() {
        batchProducer.sendMessage();
    }

    @PostMapping("/second-message/{key}")
    void message(
            @PathVariable String key,
            @RequestBody String message
    ) {
        mySecondProducer.sendMessageWithKey(key, message);
    }


}
