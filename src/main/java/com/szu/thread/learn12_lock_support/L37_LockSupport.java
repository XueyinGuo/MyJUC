package com.szu.thread.learn12_lock_support;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/9 14:17
 */

import java.util.concurrent.locks.LockSupport;

public class L37_LockSupport {

    public void m(){
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(i);
                /* 二话不说，直接停车，线程你别跑了 */
                /* 如果在调用 park 之前调用过 unpark，那么这次调用 park 是不管用的 */
                if (i == 5){
                    LockSupport.park();
                }
                if (i == 8){
                    /*
                    * 如果在调用 park 之前调用过 unpark，
                    * 那么第一次调用 park 是不管用的
                    * 第二次调用 park 才会管用
                    * */
                    LockSupport.park();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        L37_LockSupport test = new L37_LockSupport();
        Thread t1 = new Thread(test::m);
        t1.start();
        LockSupport.unpark(t1);
        /* 就算是多提前调用 n 次，最后也只是取消 park 一次 */
        LockSupport.unpark(t1);
        try {
            Thread.sleep(15000);
        }catch (Exception e){

        }
        System.out.println("15s after");
        LockSupport.unpark(t1);
    }
}
