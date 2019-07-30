package com.zwx.es_demo.dao;

import com.zwx.es_demo.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-07-24 16:05
 **/
public interface BookRepository extends ElasticsearchRepository<Book,Long> {
}
