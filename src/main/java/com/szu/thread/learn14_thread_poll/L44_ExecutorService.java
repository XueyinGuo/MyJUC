package com.szu.thread.learn14_thread_poll;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/17 21:22
 */

import java.util.concurrent.*;

public class L44_ExecutorService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(5);

        Runnable task = ()->{
            System.out.println("This is it?");

        };

        Callable callable = ()->{
            return "This is it?";
        };

        Callable callable2 = ()-> "This is it?";

        service.execute(task);
        Future<?> future = service.submit(callable);
        Future<?> future2 = service.submit(callable2);
        System.out.println(future.get());
        System.out.println(future2.get());
    }

}
