package com.zwx.es_demo.esDao;

import com.zwx.es_demo.esModel.EsBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-07-24 16:05
 **/
public interface EsBookRepository extends ElasticsearchRepository<EsBook,Long> {
    List<EsBook> findByIdBetween(Long a, Long b);

    List<EsBook> findByNameLike(String s);
}
