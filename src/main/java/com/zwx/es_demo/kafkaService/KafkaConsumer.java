package com.zwx.es_demo.kafkaService;

import com.zwx.es_demo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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

    @KafkaListener(topics = Constant.TOPIC)
    public void consumer(ConsumerRecord<?,?> consumerRecord){
//        判断是否为null
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("收到消息————————————>"+kafkaMessage);

        if(kafkaMessage.isPresent()){
            Object message = kafkaMessage.get();
            log.info("消息"+message);
        }
    }
}
