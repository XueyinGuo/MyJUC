package com.szu.thread.learn14_thread_poll;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/18 16:41
 */

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class L48_ThreadPool {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,
                8,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(8),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        /*
        * 二话不说就往用线程池执行任务，
        * 前4个任务提交的时候，由于核心线程数还不够，就启动核心线程接住了前4个
        *  queue的容量为 8 ，所以 第5个之后的8个放到了队列中
        *
        * 然后队列满了，第 12 个之后的任务由 另外的线程（非核心线程执行），最多线程总数 8 个，核心非核心各4个
        *
        * 从第16个之后的任务执行拒绝策略
        * */
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new Run(i));
        }
    }

}

class Run implements Runnable{

    int number;

    public Run(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "  " + number);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}