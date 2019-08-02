package com.zwx.es_demo.dao;

import com.zwx.es_demo.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-08-02 15:01
 **/
@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
}
