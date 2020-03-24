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
public class EsBook implements Serializable {
    @Id
    private Long id;
    /**
     * 不加Type的话 默认FieldType.Auto jpa也可以通过字段的值猜出它的类型
     * 这里也指定了中文分词 ik_smart ，具体安装方法见百度
     */
    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String name;

    public EsBook() {
    }

    public EsBook(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
