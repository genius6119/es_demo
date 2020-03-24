package com.zwx.es_demo.kafkaService;

import com.alibaba.fastjson.JSON;
import com.zwx.es_demo.constant.Constant;
import com.zwx.es_demo.esModel.EsBook;
import com.zwx.es_demo.esModel.IndexMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: es_demo
 * @description: 消息生产者
 * @author: Zwx
 * @create: 2019-07-30 16:34
 **/
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMsg(String content){
        IndexMessage msg = new IndexMessage(1,null,"哈喽哈喽",0);
        log.info(msg.toString());
        kafkaTemplate.send(Constant.TOPIC, JSON.toJSONString(msg));
    }

    public void insertBook(EsBook book){
        IndexMessage msg = new IndexMessage(1,book,"新增",0);
        log.info(msg.toString());
        kafkaTemplate.send(Constant.TOPIC, JSON.toJSONString(msg));
    }
}
