package com.zwx.es_demo.esModel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @program: es_demo
 * @description: 书
 * @author: Zwx
 * @create: 2019-07-24 15:58
 **/
@Data
@Document(indexName = "book",type = "doc",shards = 1,replicas = 0)
public class Book implements Serializable {
    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_smart")      /** 指定中文分词 ik_smart*/
    private String name;

    public Book() {
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
