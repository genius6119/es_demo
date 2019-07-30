package com.zwx.es_demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


/**
 * @program: es_demo
 * @description:
 * @author: Zwx
 * @create: 2019-07-20 17:37
 **/
@Data
@Document(indexName = "megacorp",type = "docs",shards = 3,replicas = 0)
public class Megacorp implements Serializable {

    @Id
    private Long id;

    /**
     * 不加Type的话 默认FieldType.Auto jpa也可以通过字段的值猜出它的类型
     */
    @Field(type = FieldType.Text,fielddata = true)
    private String last_name;
    @Field(type = FieldType.Text)
    private String first_name;
    @Field(type = FieldType.Text)
    private String interests;
    @Field(type = FieldType.Double)
    private Long age;
    @Field(type = FieldType.Text)
    private String about;


    public Megacorp(Long id, String last_name, String first_name, String interests, Long age, String about) {
        this.id = id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.interests = interests;
        this.age = age;
        this.about = about;
    }

    public Megacorp() {
    }
}
