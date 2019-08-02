package com.zwx.es_demo.service;

import com.zwx.es_demo.dao.BookRepository;
import com.zwx.es_demo.kafkaService.KafkaProducer;
import com.zwx.es_demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-08-02 15:03
 **/
@Service
public class BookService {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private BookRepository bookRepository;

    public void insertBook(Book book) {
        bookRepository.save(book);
        com.zwx.es_demo.esModel.Book book1 = new com.zwx.es_demo.esModel.Book(book.getId(),book.getName());
        kafkaProducer.insertBook(book1);
    }
}
