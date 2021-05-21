package com.szu.thread.learn02_sync_and_volatile;
/*
* Synchronized 是 怎么实现的呢？
*
* https://blog.csdn.net/XueyinGuo/article/details/110913371
*
* 详细讲述了对象加锁的时候发生了什么（对象头MarkWord中和线程之间的互动）
* 详细讲述了锁升级过程
* */
public class L08_Synchronized_Class {


    private static int count = 10;
    /* 这里带了 static */
    public synchronized static void m() { //这里等同于synchronized(L08_Synchronized_Class.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        synchronized(L08_Synchronized_Class.class) { //考虑一下这里写synchronized(this)是否可以？
            count --;
        }
    }
}
