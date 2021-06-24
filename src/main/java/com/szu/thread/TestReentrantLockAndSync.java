package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/4/25 21:04
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLockAndSync {

    public static int c = 0;
    public static int d = 0;
    public static int e = 0;

    public static final ReentrantLock unfairLock = new ReentrantLock();

    public static void testAll(int threadCount, int loopCount) throws InterruptedException {
        testSynchronized(threadCount, loopCount);
        testReentrantLockUnfair(threadCount, loopCount);
    }

    public static void testReentrantLockUnfair(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                /* 锁粗粒度化之后，运行时间差不多 */
                /*
                 *   细粒度的话，解释一波：
                 *
                 *   我觉得还是跟空转有关系，还有state不断++  -- 有关
                 *
                 *   而且应该是 synchronized 已经做成了用户态的锁，重量级锁用 ObjectMonitor，也不用内核切换去申请系统锁了
                 *
                 *   synchronized 直接放弃CPU，然后加入锁对象的等待队列   【VS】 ReentrantLock 499个线程空转64次之后再加入AQS队列，并且每次细粒度情况下都在不断的state++ 和 -- 一通操作
                 *
                 *   浓缩成一句话  就是 synchronized 牛逼
                 * */
                for (int j = 0; j < loopCount; j++) {
                    unfairLock.lock();

//                    try {
                    d++;
//                    } finally {
                    unfairLock.unlock();
//                    }
                }

                countDownLatch.countDown();
            }).start();
        }


        countDownLatch.await();

        System.out.println("testReentrantLockUnfair: result=" + d + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }

    public static void testSynchronized(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {


                for (int j = 0; j < loopCount; j++) {
                    synchronized (TestReentrantLockAndSync.class) {
                        e++;
                    }
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();

        System.out.println("testSynchronized: result=" + e + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------------------------------------");
        testAll(1, 100000);
        System.out.println("-------------------------------------");
        testAll(2, 100000);
        System.out.println("-------------------------------------");
        testAll(4, 100000);
        System.out.println("-------------------------------------");
        testAll(6, 100000);
        System.out.println("-------------------------------------");
        testAll(8, 100000);
        System.out.println("-------------------------------------");
        testAll(10, 100000);
        System.out.println("-------------------------------------");
        testAll(50, 100000);
        System.out.println("-------------------------------------");
        testAll(100, 100000);
        System.out.println("-------------------------------------");
        testAll(200, 100000);
        System.out.println("-------------------------------------");
        testAll(500, 100000);
        System.out.println("-------------------------------------");
        System.out.println("-------------------------------------");
        testAll(500, 10000);
        System.out.println("-------------------------------------");
        testAll(500, 1000);
        System.out.println("-------------------------------------");
        testAll(500, 100);
        System.out.println("-------------------------------------");
        testAll(500, 10);
        System.out.println("-------------------------------------");
        testAll(500, 1);
        System.out.println("-------------------------------------");
    }

}
