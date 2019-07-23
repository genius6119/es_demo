package com.zwx.es_demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.yaml.snakeyaml.events.Event;

/**
 * @program: es_demo
 * @description:
 * Megacorp 实体类
 * Long 实体类中@Id 的数据类型
 * @author: Zwx
 * @create: 2019-07-23 11:11
 **/
public interface MegacorpRepository extends ElasticsearchRepository<Megacorp,Long> {
}
