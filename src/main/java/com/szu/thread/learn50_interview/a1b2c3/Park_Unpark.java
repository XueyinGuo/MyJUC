package com.szu.thread.learn50_interview.a1b2c3;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 14:48
 */

import java.util.concurrent.locks.LockSupport;

public class Park_Unpark {

    static Thread letterThread = null;
    static Thread numberThread = null;

    public static void main(String[] args) {

        int length = Utils.letters.length;

        letterThread = new Thread(() -> {
            for (int i = 0; i < length; i++) {
                Utils.printStr(Utils.letters[i]);
                /* 先把对方叫醒，然后再把自己 wait */
                LockSupport.unpark(numberThread);
                LockSupport.park();
            }
        }, "letterThread");


        numberThread = new Thread(() -> {
            for (int i = 0; i < length; i++) {
                LockSupport.park();
                Utils.printStr(Utils.numbers[i]);
                /* 先把对方叫醒，然后再把自己wait */
                LockSupport.unpark(Park_Unpark.letterThread);
            }
        }, "numberThread");

        letterThread.start();
        numberThread.start();

    }

}
