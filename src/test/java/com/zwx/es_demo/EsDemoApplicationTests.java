package com.zwx.es_demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDemoApplicationTests {

    @Autowired(required=false)
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private MegacorpRepository repository;

    @Test
    public void contextLoads() {
        esTemplate.createIndex(Megacorp.class);
    }

    @Test
    public void save() {
        Megacorp megacorp = new Megacorp(1L,"1","1","1",1L,"1");
        repository.save(megacorp);
    }

}
