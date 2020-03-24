package com.zwx.es_demo.kafkaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwx.es_demo.constant.Constant;
import com.zwx.es_demo.esDao.EsBookRepository;
import com.zwx.es_demo.esModel.IndexMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-07-30 16:34
 **/
@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private EsBookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = Constant.TOPIC)
    public void consumer(ConsumerRecord<?,?> consumerRecord) throws IOException {
//        判断是否为null
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("收到消息————————————>"+kafkaMessage);

        if(kafkaMessage.isPresent()){
            Object message = kafkaMessage.get();
            log.info("消息" + message);
            IndexMessage indexMessage = objectMapper.readValue(message.toString(),IndexMessage.class);
            bookRepository.save(indexMessage.getBook());
        }


    }
}
