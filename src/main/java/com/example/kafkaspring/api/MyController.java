package com.example.kafkaspring.api;

import com.example.kafkaspring.data.Request;
import com.example.kafkaspring.model.MyMessage;
import com.example.kafkaspring.model.MyModel;
import com.example.kafkaspring.producer.BatchProducer;
import com.example.kafkaspring.producer.CommitProducer;
import com.example.kafkaspring.producer.MySingleProducer;
import com.example.kafkaspring.producer.SerialProducer;
import com.example.kafkaspring.service.MyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Kafka 실습 API")
public class MyController {

    private final MySingleProducer myProducer;
    private final CommitProducer commitProducer;
    private final BatchProducer batchProducer;
    private final SerialProducer serialProducer;

    /* 1. 단일 메시지 리스너를 통한 PUB/SUB 구현 */
    @Operation(summary="1. 단일 메시지 리스너를 활용한 pub/sub", description ="pub/sub 기본 발행 구독 입니다.")
    @PostMapping("/message/a")
    void message(@RequestBody MyMessage message) throws Exception {
        myProducer.sendMessage(message);
    }

    /* 2. 배치 메시지 리스너를 통한 PUB/SUB 구현  */
    @PostMapping("/message/b")
    @Operation(summary="2. 배치 메시지 리스너를 활용한 pub/sub", description ="pub/sub을 한번에 모아서 처리 합니다.")
    void batchMessage() {
        batchProducer.sendMessage();
    }

    /* 3. 객체를 JSON문자열로 직렬화 구현 */
    @PostMapping("/message/c")
    @Operation(summary="03. 객체를 JSON 문자열로 직렬화 구현", description ="객체를 JSON 문자열로 자동 직렬화 후 PUB/SUB 처리 합니다.")
    void message3(@RequestBody MyMessage message) throws JsonProcessingException {
        serialProducer.sendMessage(message);
    }

    /* 4. 수동 커밋 구현 */
    @PostMapping("/message/d/{key}")
    @Operation(summary="04. 수동 커밋 테스트", description ="카프카 리밸런싱으로 인해 데이터 유실, 중복을 막기 위해 수동 커밋 진행")
    void message4(
            @PathVariable String key,
            @RequestBody String message
    ) {
        commitProducer.sendMessageWithKey(key, message);
    }


    //KAFKA CDC 테스트
    private final MyService myService;

    /* 5. 데이터를 읽어와서 DB에 단순 저장 (cdc 객체 리스너) */
    @Operation(summary="05. [카프카 Cdc] 객체 리스너를 활용한 사용 예시", description ="객체 리스너를 활용한 CDC 구현")
    @PostMapping("/message/e")
    MyModel create(@RequestBody Request request) {
        if (
                request == null ||
                        request.getUserId() == null ||
                        request.getUserName() == null ||
                        request.getUserAge() == null ||
                        request.getContent() == null
        ) return null;

        MyModel myModel = MyModel.join(
                request.getUserId(),
                request.getUserAge(),
                request.getUserName(),
                request.getContent()
        );
        return myService.save(myModel);
    }

    /* 6. 데이터를 읽어와서 DB에 단순 저장 (cdc 트랜잭션) */
    @Operation(summary="06. [카프카 Cdc] 트랜잭션을 활용한 사용 예시", description ="트랜잭션을 활용한 CDC 구현")
    @PostMapping("/message/f")
    MyModel createTrans(@RequestBody Request request) {
        if (
                request == null ||
                        request.getUserId() == null ||
                        request.getUserName() == null ||
                        request.getUserAge() == null ||
                        request.getContent() == null
        ) return null;

        MyModel myModel = MyModel.join(
                request.getUserId(),
                request.getUserAge(),
                request.getUserName(),
                request.getContent()
        );
        return myService.saveTrans(myModel);
    }

    /* 7. 데이터를 읽어와서 DB에 단순 저장 (cdc 이벤트 처리) */
    @Operation(summary="07. [카프카 Cdc] 이벤트를 활용한 사용 예시", description ="이벤트를 활용한 CDC 구현")
    @PostMapping("/message/g")
    MyModel createEvent(@RequestBody Request request ) {
        if (
                request == null ||
                        request.getUserId() == null ||
                        request.getUserName() == null ||
                        request.getUserAge() == null ||
                        request.getContent() == null
        ) return null;

        MyModel myModel = MyModel.join(
                request.getUserId(),
                request.getUserAge(),
                request.getUserName(),
                request.getContent()
        );
        return myService.saveEvent(myModel);
    }
//
//    @GetMapping("/greetings")
//    List<MyModel> list() {
//        return myService.findAll();
//    }
//
//    @GetMapping("/greetings/{id}")
//    MyModel get(
//            @PathVariable Integer id
//    ) {
//        return myService.findById(id);
//    }
//
//    @PatchMapping("/greetings/{id}")
//    MyModel update(
//            @PathVariable Integer id,
//            @RequestBody String content
//    ) {
//        if (id == null || content == null || content.isBlank()) return null;
//        MyModel myModel = myService.findById(id);
//        myModel.setContent(content);
//        return myService.save(myModel);
//    }
//
//    @DeleteMapping("/greetings/{id}")
//    void delete(
//            @PathVariable Integer id
//    ) {
//        myService.delete(id);
//    }
//



}
