package com.kaye.jvm.jvm01;

public class Test01 {

    public static void main(String[] args) {

        //【Run】-【Edit Configuration】中
        //1. 参数配置：-XX:+PrintGC -Xms5m -Xmx20m -XX:-UseSerialGC -XX:+PrintGCDetails

        //查看GC信息
        System.err.println("max memory: " + Runtime.getRuntime().maxMemory());
        System.err.println("free memory: " + Runtime.getRuntime().freeMemory());
        System.err.println("total memory: " + Runtime.getRuntime().totalMemory());

        //使用1M后的大小
        byte[] bytes1 = new byte[1 * 1024 * 1024];
        System.err.println("使用1M后的大小");
        System.err.println("max memory: " + Runtime.getRuntime().maxMemory());
        System.err.println("free memory: " + Runtime.getRuntime().freeMemory());
        System.err.println("total memory: " + Runtime.getRuntime().totalMemory());

        //使用5M后的大小
        byte[] bytes2 = new byte[6 * 1024 * 1024];
        System.err.println("使用5M后的大小");
        System.err.println("max memory: " + Runtime.getRuntime().maxMemory());
        //由于初始内存不够了，就会去最大内存申请内存
        System.err.println("free memory: " + Runtime.getRuntime().freeMemory());
        //所以总共内存变大了
        System.err.println("total memory: " + Runtime.getRuntime().totalMemory());
    }

}
