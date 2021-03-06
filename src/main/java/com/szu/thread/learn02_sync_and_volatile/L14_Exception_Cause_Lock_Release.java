package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description 出现异常的时候，锁默认会被释放
 *              程序在执行过程中，如果出现异常，默认情况锁会被释放
 *              所以，在并发处理的过程中，有异常要多加小心，不然可能会发生不一致的情况。
 *              比如，在一个web app处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，
 *              在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据。
 *              因此要非常小心的处理同步业务逻辑中的异常
 * @Date 2021/2/7 14:09
 */

public class L14_Exception_Cause_Lock_Release {
    int num = 50;

    public synchronized void hasException() {
        String name = Thread.currentThread().getName();
        try {
            while (true) {
                Thread.sleep(1000);
                System.out.println(name +"  "+ num--);
                if (num % 5 == 0) {
                    int i = 1 / 0;  //此处抛出异常，锁将被释放，
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( Thread.currentThread().getName() + "异常睡觉咯");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.println( Thread.currentThread().getName() +"睡醒了");
        }
    }


    public static void main(String[] args) {
        L14_Exception_Cause_Lock_Release l = new L14_Exception_Cause_Lock_Release();
        new Thread(l::hasException, "t1").start();
        new Thread(l::hasException, "t2").start();

    }
}
