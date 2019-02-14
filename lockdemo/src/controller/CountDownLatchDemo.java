package controller;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch是说当其他线程都通知该线程后他才会执行
 *
 * @author yk
 * @since 2019/2/12$ 16:07$
 */
public class CountDownLatchDemo {



    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        fixedThreadPool.execute(()->{
            try {
                System.err.println("t1进入.....，开始等待");
                countDownLatch.await();
                System.err.println("t1继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        fixedThreadPool.execute(()->{
            try {
                System.err.println("t2进入.....，开始初始化");
                countDownLatch.countDown();
                System.err.println("t2执行完毕通知t1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fixedThreadPool.execute(()->{
            try {
                System.err.println("t3进入.....，开始初始化");
                countDownLatch.countDown();
                System.err.println("t3执行完毕通知t1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
