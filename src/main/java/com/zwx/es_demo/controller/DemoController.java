package com.zwx.es_demo.controller;

import com.zwx.es_demo.kafkaService.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-07-30 17:00
 **/
@RestController
@RequestMapping
public class DemoController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping("/send")
    public String send(){
        kafkaProducer.sendMsg("哈哈哈哈");
        return "ok";
    }
}
