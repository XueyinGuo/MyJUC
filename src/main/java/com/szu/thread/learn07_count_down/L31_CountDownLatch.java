package com.szu.thread.learn07_count_down;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 16:08
 */

import java.util.concurrent.CountDownLatch;

public class L31_CountDownLatch {

    public static void testJoin() throws InterruptedException {
        Thread[] threads = createThreads();

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
            threads[i].join();
        }
        System.out.println("All Join");
    }

    public static void testCountDown(){
        Thread[] threads = new Thread[30];
        CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    if (j == 999){
                        System.out.println(Thread.currentThread().getName() + " Done!");
                    }
                }
                latch.countDown();
            }, "t"+i);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try{
            latch.await();
        }catch (Exception e){

        }
        System.out.println("end Latch");
    }

    private static Thread[] createThreads() {
        Thread[] threads = new Thread[30];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    if (j == 999){
                        System.out.println(Thread.currentThread().getName() + " Done!");
                    }
                }
            }, "t"+i);
        }
        return threads;
    }


    public static void main(String[] args) throws InterruptedException {
        testJoin();
        testCountDown();
    }
}
