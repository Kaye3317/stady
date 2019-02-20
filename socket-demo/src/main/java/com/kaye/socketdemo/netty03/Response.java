package com.kaye.socketdemo.netty03;

import lombok.Data;

import java.io.Serializable;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/2/20$ 15:38$
 */
public class Response implements Serializable {

    private static final long SerialVersionUID = 1L;

    private int id;

    private String name;

    private int num;

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
