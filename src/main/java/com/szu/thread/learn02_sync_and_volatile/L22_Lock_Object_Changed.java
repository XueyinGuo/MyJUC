package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          锁定某对象o，如果o的属性发生改变，不影响锁的使用
 *          但是如果o变成另外一个对象，则锁定的对象发生改变
 *          o只是一个指针，此时改变 o 指向了堆空间另外一个对象，之前的 o 还是被 t1 锁着，
 *          所以t2线程此时也可以运行了
 *
 * @Date 2021/2/7 16:53
 */

import java.util.concurrent.TimeUnit;

public class L22_Lock_Object_Changed {
    /* 用来当锁的对象，将来对他进行修改 */
    //所以为了防止锁对象呗修改，应该使用final修饰
    /* final */ Object o = new Object();

    public static void main(String[] args) {
        L22_Lock_Object_Changed l = new L22_Lock_Object_Changed();
        // t1线程开始调用m
        new Thread(l::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(l::m, "t2").start();
        // 此时改变 o 成另一个对象，之前的 o 还是被 t1 锁着，但是这个o只是一个指针，指向了
        // 堆空间中另外一个新的对象， 所以t2线程此时也可以运行了
        l.o = new Object();
    }

    /* m 方法每隔一秒钟打印一下线程名字 */
    void m() {
        synchronized(o) {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}
