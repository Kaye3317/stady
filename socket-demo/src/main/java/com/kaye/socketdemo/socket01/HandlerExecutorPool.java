package com.kaye.socketdemo.socket01;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 手动创建线程池
 *
 * @author yk
 * @since 2019/2/13$ 16:51$
 */
public class HandlerExecutorPool {

    private ExecutorService executorService;

    /**
     * 初始化手动穿件的线程池参数
     *
     * @param maxPoolSize 最大线程容量
     * @param queueSize   最大阻塞线程容量
     */
    public HandlerExecutorPool(int maxPoolSize, int queueSize) {
        this.executorService = new ThreadPoolExecutor(
                //核心线程数，根据系统硬件获取
                Runtime.getRuntime().availableProcessors(),
                //最大线程数
                maxPoolSize,
                //线程存在时间
                120L,
                //线程存在时间单位
                TimeUnit.SECONDS,
                //阻塞队列 用于存储阻塞的线程
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    /**
     * 开启一个线程
     *
     * @param task runnable实例
     */
    public void execute(Runnable task) {
        executorService.execute(task);
    }

}
