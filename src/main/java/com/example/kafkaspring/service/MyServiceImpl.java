package com.example.kafkaspring.service;

import com.example.kafkaspring.data.MyEntity;
import com.example.kafkaspring.data.MyJpaRepository;
import com.example.kafkaspring.event.MyCdcApplicationEvent;
import com.example.kafkaspring.model.MyModel;
import com.example.kafkaspring.model.MyModelConverter;
import com.example.kafkaspring.model.OperationType;
import com.example.kafkaspring.producer.MyCdcProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyServiceImpl implements MyService {

    private final MyJpaRepository myJpaRepository;
    private final MyCdcProducer myCdcProducer;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public MyModel save(MyModel model) {
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(model));
        return MyModelConverter.toModel(entity);
    }

    @Override
    @Transactional
    public MyModel saveTrans(MyModel model) {
        OperationType operationType = model.getId() == null ? OperationType.CREATE : OperationType.UPDATE;
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(model));
        MyModel resultModel = MyModelConverter.toModel(entity);
        try {
            myCdcProducer.sendMessage(
                    MyModelConverter.toMessage(
                            resultModel.getId(),
                            resultModel,
                            operationType
                    )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for sendMessage", e);
        }

        return resultModel;
    }

    @Override
    @Transactional
    public void deleteTrans(Integer id) {
        myJpaRepository.deleteById(id);
        try {
            myCdcProducer.sendMessage(
                    MyModelConverter.toMessage(id, null, OperationType.DELETE)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for sendMessage", e);
        }
    }


    @Override
    @Transactional
    public MyModel saveEvent(MyModel model) {
        OperationType operationType = model.getId() == null ? OperationType.CREATE : OperationType.UPDATE;
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(model));
        MyModel resultModel = MyModelConverter.toModel(entity);
        applicationEventPublisher.publishEvent(
                new MyCdcApplicationEvent(
                        this,
                        resultModel.getId(),
                        resultModel,
                        operationType
                )
        );
        return resultModel;
    }

    @Override
    @Transactional
    public void deleteEvent(Integer id) {
        myJpaRepository.deleteById(id);
        applicationEventPublisher.publishEvent(
                new MyCdcApplicationEvent(
                        this,
                        id,
                        null,
                        OperationType.DELETE
                )
        );
    }


    @Override
    public List<MyModel> findAll() {
        List<MyEntity> entities = myJpaRepository.findAll();
        return entities.stream().map(MyModelConverter::toModel).toList();
    }

    @Override
    public MyModel findById(Integer id) {
        Optional<MyEntity> entity = myJpaRepository.findById(id);
        return entity.map(MyModelConverter::toModel).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        myJpaRepository.deleteById(id);
    }
}
