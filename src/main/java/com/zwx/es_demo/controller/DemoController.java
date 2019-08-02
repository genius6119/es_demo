package com.zwx.es_demo.controller;

import com.zwx.es_demo.kafkaService.KafkaProducer;
import com.zwx.es_demo.model.Book;
import com.zwx.es_demo.service.BookService;
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

    @Autowired
    private BookService bookService;

    @RequestMapping("/send")
    public String send(){
        kafkaProducer.sendMsg("哈哈哈哈");
        return "ok";
    }

    @RequestMapping("/insertBook")
    public String insertBook(Book book){
        bookService.insertBook(book);
        return "ok";
    }
}
