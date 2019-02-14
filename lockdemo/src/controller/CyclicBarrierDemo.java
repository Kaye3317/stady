package controller;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/2/12$ 16:29$
 */
public class CyclicBarrierDemo {

    static class Runner implements Runnable {


        private CyclicBarrier cyclicBarrier;
        private String name;

        public Runner(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }



        @Override
        public void run() {
            try {
                Thread.sleep(2000 * new Random().nextInt(5));
                System.err.println(name + "ready");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println(name + "go!");
        }
    }

    public void print(){
        System.err.println("123");
    }
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Runner(cyclicBarrier,"zhangsan"));
        executorService.execute(new Runner(cyclicBarrier,"lisi"));
        executorService.execute(new Runner(cyclicBarrier,"wangwu"));

    }
}
