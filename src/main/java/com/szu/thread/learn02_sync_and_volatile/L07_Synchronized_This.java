package com.szu.thread.learn02_sync_and_volatile;
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
    /*
    * 这种情况下应该注意锁力度的粗化和细化问题
    * */
    public synchronized void m() { //等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public void m2() {
        synchronized(this) { //任何线程要执行下面的代码，必须先拿到this的锁
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

}
