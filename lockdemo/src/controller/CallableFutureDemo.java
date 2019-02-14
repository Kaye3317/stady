package controller;

import java.util.concurrent.*;

/**
 * java类作用描述
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

        System.err.println(future.get());
        System.err.println(future.isDone());
        System.err.println(future.isCancelled());
        System.err.println(futureTask.get());
        System.err.println(futureTask.isDone());
        System.err.println(futureTask.isCancelled());

        executorService.shutdown();
    }

}
