package com.szu.thread.learn14_thread_poll;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/17 21:18
 */

import java.util.concurrent.Executor;

public class L42_Executor implements Executor {

    Runnable task;

    @Override
    public void execute(Runnable command) {
//        task.run();
        command.run();
    }


    public static void main(String[] args) {

        L42_Executor test = new L42_Executor();

        Runnable task = ()->{
//            System.out.println("就这？");
            System.out.println("This is it?");
        };

        test.execute(task);

    }
}
