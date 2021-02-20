package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *
 *      LockSupport.park() 不会释放锁
 *
 * @Date 2021/2/19 23:19
 */

import java.util.concurrent.locks.LockSupport;

public class locksupport_lcok {

    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();


        Thread t1 = new Thread(() -> {
            synchronized (o) {

                System.out.println(Thread.currentThread().getName());
                LockSupport.park();

            }
        }, "t1");


        Thread.sleep(1000);


        Thread t2 = new Thread(() -> {
            LockSupport.unpark(t1);

            synchronized (o) {
                System.out.println(Thread.currentThread().getName());
            }


        }, "t2");


        t1.start();
        t2.start();
    }

}
