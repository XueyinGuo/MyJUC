package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *
 *  与 TEST 配套食用
 *
 * @Date 2021/4/25 21:10
 */


import java.util.concurrent.locks.ReentrantLock;

/**
 * 为了测试 SYNC 和 ReentrantLock 二者性能谁更高，具体解释写在了 {@link TestReentrantLockAndSync} 中了
 * */
public class TESTII {

    volatile static int a = 0;



    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        Thread[] theads = new Thread[500];
        for (int i = 0; i < theads.length; i++) {
            theads[i] = new Thread(()->{

                try {
                    lock.lock();
                    for (int j = 0; j < 100000; j++) {
                        a++;
                    }
                }finally {
                    lock.unlock();
                }

            });
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < theads.length; i++) {
            theads[i].start();
        }

        for (int i = 0; i < theads.length; i++) {
            theads[i].join();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
