package com.szu.thread;
/*
* Synchronized 是 怎么实现的呢？
*
* https://blog.csdn.net/XueyinGuo/article/details/110913371
*
* 详细讲述了对象加锁的时候发生了什么（对象头MarkWord中和线程之间的互动）
* 详细讲述了锁升级过程
* */
public class L07_Synchronized_This {

    private int count = 10;

    public synchronized void m() { //等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public void n() { //访问这个方法的时候不需要上锁
        count++;
    }
}
