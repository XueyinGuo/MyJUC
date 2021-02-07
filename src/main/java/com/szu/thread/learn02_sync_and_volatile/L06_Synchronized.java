package com.szu.thread.learn02_sync_and_volatile;
/*
* Synchronized 是 怎么实现的呢？
*
* https://blog.csdn.net/XueyinGuo/article/details/110913371
* 详细讲述了对象加锁的时候发生了什么（对象头MarkWord中和线程之间的互动）
* 详细讲述了锁升级过程
* */
public class L06_Synchronized {

    private static int count = 300000;
    /* 每次新建一个对象当锁有点麻烦 */
    private static Object o = new Object();

    public static void test(){
        synchronized (o){
            for (int i = 0; i < 100000; i++) {
                count--;
            }
        }
    }

    static void testSynchronized(){
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        t3.start();
        t2.start();
        t1.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            test();
        }
    }

    public static void main(String[] args) {
        testSynchronized();
        System.out.println(count);
    }
}
