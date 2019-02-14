package com.kaye.mqdemo.controller;

import java.io.Serializable;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/1/17$ 17:25$
 */
public class User implements Serializable {

    private static final long serialVersionUID = -219988432063763456L;

    private String name;

    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
