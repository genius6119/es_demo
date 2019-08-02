package com.zwx.es_demo.esModel;

import lombok.Data;

/**
 * @program: es_demo
 * @description: 消息结构体
 * @author: Zwx
 * @create: 2019-07-30 15:54
 **/
@Data
public class IndexMessage {

    public static final String INDEX = "index";
    public static final String REMOVE = "remove";

    private Integer id;
    private Book book;
    private String operation;
    private int retry = 0;

    public IndexMessage() {
    }

    public IndexMessage(Integer id, Book book, String operation, int retry) {
        this.id = id;
        this.book = book;
        this.operation = operation;
        this.retry = retry;
    }

    @Override
    public String toString() {
        return "IndexMessage{" +
                "id=" + id +
                ", book=" + book +
                ", operation='" + operation + '\'' +
                ", retry=" + retry +
                '}';
    }
}
