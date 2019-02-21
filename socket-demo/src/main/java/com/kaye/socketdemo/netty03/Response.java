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

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String responseMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
