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

    private int id;

    private String name;

    private int num;

    private byte[] files;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", files=" + Arrays.toString(files) +
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

    public byte[] getFiles() {
        return files;
    }

    public void setFiles(byte[] files) {
        this.files = files;
    }
}
