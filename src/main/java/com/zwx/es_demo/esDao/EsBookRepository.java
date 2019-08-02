package com.zwx.es_demo.esDao;

import com.zwx.es_demo.esModel.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-07-24 16:05
 **/
public interface EsBookRepository extends ElasticsearchRepository<Book,Long> {
}
