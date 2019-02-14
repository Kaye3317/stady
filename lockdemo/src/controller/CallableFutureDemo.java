package controller;

import java.util.concurrent.*;

/**
 * future callable演示
 *
 * @author yk
 * @since 2019/2/12$ 16:47$
 */
public class CallableFutureDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return "123";
            }
        });
        Future future = executorService.submit(futureTask);

        //返回null表示线程执行完成
        System.err.println(future.get());
        //线程是否执行完成
        System.err.println(future.isDone());
        //线程是被被取消
        System.err.println(future.isCancelled());
        //返回线程的值
        System.err.println(futureTask.get());
        //线程释放执行完成
        System.err.println(futureTask.isDone());
        //线程是否被取消
        System.err.println(futureTask.isCancelled());

        executorService.shutdown();
    }

}
