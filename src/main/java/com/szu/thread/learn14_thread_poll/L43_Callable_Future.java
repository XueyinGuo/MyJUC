package com.szu.thread.learn14_thread_poll;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/17 21:11
 */

import java.util.concurrent.*;

public class L43_Callable_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        Callable callable = ()->{

            return "This is my Callable";
        };

        Future future = pool.submit(callable);

        System.out.println(future.get());
        pool.shutdown();

        new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 10;
            }
        };
    }

}
