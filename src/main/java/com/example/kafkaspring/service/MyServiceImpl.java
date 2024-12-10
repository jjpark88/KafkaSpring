package com.example.kafkaspring.service;

import com.example.kafkaspring.data.MyEntity;
import com.example.kafkaspring.data.MyJpaRepository;
import com.example.kafkaspring.model.MyModel;
import com.example.kafkaspring.model.MyModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyServiceImpl implements MyService {

    private final MyJpaRepository myJpaRepository;

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
    public MyModel save(MyModel model) {
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(model));
        return MyModelConverter.toModel(entity);
    }

    @Override
    public void delete(Integer id) {
        myJpaRepository.deleteById(id);
    }
}
