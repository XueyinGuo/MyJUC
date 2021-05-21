package com.szu.thread.learn13_thread_local;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description

        在Thread类中，有一个变量 ThreadLocal.ThreadLocalMap threadLocals = null;
        所以说每个线程栈都有一个私有的map
        其他线程访问不到

 * @Date 2021/2/10 21:08
 */

public class L38_ThreadLocal {

    static ThreadLocal<Star> threadLocal = new ThreadLocal<Star>();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        /* t2 往自己的map中设置值， t1当然访问不到 */
        new Thread(() -> {
            try {
                threadLocal.set(new Star());
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + "  " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }

}

class Star {

    String name = "The Falling star!";
}