package com.zwx.es_demo.service;

import com.zwx.es_demo.dao.BookRepository;
import com.zwx.es_demo.esModel.EsBook;
import com.zwx.es_demo.kafkaService.KafkaProducer;
import com.zwx.es_demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        EsBook book1 = new EsBook(book.getId(),book.getName());
        kafkaProducer.insertBook(book1);
    }
}
