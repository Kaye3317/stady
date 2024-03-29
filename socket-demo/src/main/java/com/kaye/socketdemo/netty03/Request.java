package com.kaye.socketdemo.netty03;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/2/20$ 15:37$
 */
public class Request implements Serializable {

    private static final long SerialVersionUID = 1L;

    private String id;
    private String name;
    private String requestMessage;

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

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}
