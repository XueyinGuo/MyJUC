package com.szu.thread.learn50_interview.a1b2c3;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 14:48
 */

public class Wait_Notify {

    static final Object lock = new Object();



    public static void main(String[] args) {

        int length = Utils.letters.length;
        new Thread(()->{
            
            synchronized (lock){
                for (int i = 0; i < length; i++) {
                    Utils.printStr(Utils.letters[i]);
                    /* 先把对方叫醒，然后再把自己wait */
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /* 当 for 结束的时候，总有一个线程在 wait， 通知这个线程结束 */
                lock.notify();
            }

        },"letterThread").start();


        new Thread(()->{

            synchronized (lock){
                for (int i = 0; i < length; i++) {

                    Utils.printStr(Utils.numbers[i]);
                    /* 先把对方叫醒，然后再把自己wait */
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                /* 当 for 结束的时候，总有一个线程在 wait， 通知这个线程结束 */
                lock.notify();
            }

        },"numberThread").start();
    }

}
