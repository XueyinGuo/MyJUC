package com.szu.thread.learn14_thread_poll;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/17 21:28
 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class L45_FutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        /*
        *
                Callable callable = ()->{
                    return "This is my Callable";
                };
                Future future = pool.submit(callable);
        *
        * */
        /* FutureTask 自己可以作为一个 Runnable，又可以把结果存在自己里边 */
        FutureTask futureTask = new FutureTask( ()->{
            System.out.println("This is it?");
            return "This is it?";
        } );

        executorService.submit(futureTask);

        System.out.println(futureTask.get());

    }

}
