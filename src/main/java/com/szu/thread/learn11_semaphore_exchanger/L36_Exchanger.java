package com.szu.thread.learn11_semaphore_exchanger;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 20:16
 */

import java.util.concurrent.Exchanger;

public class L36_Exchanger {
    /*
    * 线程之间的通信，两个线程之间的通信，用 Exchanger
    * */
    Exchanger<String> exchanger = new Exchanger<>();

    public void m(String s){
        try {
            s = exchanger.exchange(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "  " + s);
    }

    public static void main(String[] args) {
        L36_Exchanger test = new L36_Exchanger();
        Runnable runnable = ()->test.m(Thread.currentThread().getName());
        Thread t1 = new Thread(runnable, "我是线程1");
        Thread t2 = new Thread(runnable, "我是线程2");
        t1.start();
        t2.start();
    }
}
