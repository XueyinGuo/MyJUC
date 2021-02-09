package com.szu.thread.learn01_threads;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/9 20:11
 */

import java.util.concurrent.locks.ReentrantLock;

public class L06_interrupt {

    ReentrantLock lock = new ReentrantLock();

    public  void m1(){

            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+ " is going to sleep 10 seconds");
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName()+ " wake up");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+ " has been interrupted ");
            }finally {
                lock.unlock();
            }

    }


    public static void main(String[] args) throws InterruptedException {
        L06_interrupt test = new L06_interrupt();
        Thread t1 = new Thread(test::m1, "t1");
        t1.start();
        Thread.sleep(1000);

        new Thread(test::m1, "t2").start();
        t1.interrupt();
    }
}
