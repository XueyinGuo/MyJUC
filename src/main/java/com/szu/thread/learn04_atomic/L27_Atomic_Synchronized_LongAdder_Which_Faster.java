package com.szu.thread.learn04_atomic;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 14:02
 */

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class L27_Atomic_Synchronized_LongAdder_Which_Faster {
    long count1 = 0;
    final Object lock = new Object();
    AtomicLong count2 = new AtomicLong(0);
    LongAdder count3 = new LongAdder();

    /*
     * =====================================================================
     * =====================================================================
     * Synchronized
     * =====================================================================
     * =====================================================================
     * */
    public void Sync() {
        synchronized (lock) {
            for (int i = 0; i < 100000; i++) {
                count1++;
            }
        }
    }

    public void testSync() {
        L27_Atomic_Synchronized_LongAdder_Which_Faster l = new L27_Atomic_Synchronized_LongAdder_Which_Faster();
        ArrayList<Thread> threads = new ArrayList<>();
        // 创建十个线程，塞入线程集合
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(l::Sync, "t" + i));
        }
        // 启动每个线程
        startThreads(threads);
    }

    /*
     * =====================================================================
     * =====================================================================
     * AtomicLong
     * =====================================================================
     * =====================================================================
     * */
    public void Atomic() {
        for (int i = 0; i < 100000; i++) {
            count2.incrementAndGet();
        }
    }
    public void testAtomic() {
        L27_Atomic_Synchronized_LongAdder_Which_Faster l = new L27_Atomic_Synchronized_LongAdder_Which_Faster();
        ArrayList<Thread> threads = new ArrayList<>();
        // 创建十个线程，塞入线程集合
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(l::Atomic, "t" + i));
        }
        startThreads(threads);
    }


    /*
     * =====================================================================
     * =====================================================================
     * LongAdder
     * =====================================================================
     * =====================================================================
     * */
    public void Adder() {
        for (int i = 0; i < 100000; i++) {
            count3.increment();
        }
    }
    public void testLongAdder() {
        L27_Atomic_Synchronized_LongAdder_Which_Faster l = new L27_Atomic_Synchronized_LongAdder_Which_Faster();
        ArrayList<Thread> threads = new ArrayList<>();
        // 创建十个线程，塞入线程集合
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(l::Adder, "t" + i));
        }
        startThreads(threads);
    }
    /*
    * ==========================================
    * 启动所有线程
    * ==========================================
    * */
    public static void startThreads(ArrayList<Thread> threads) {
        // 启动每个线程
        threads.forEach(o -> {
            o.start();
        });
        //等待所有线程结束在输出 的值
        threads.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {

        L27_Atomic_Synchronized_LongAdder_Which_Faster test = new L27_Atomic_Synchronized_LongAdder_Which_Faster();
        startTest(test);
    }

    private static void startTest(L27_Atomic_Synchronized_LongAdder_Which_Faster test) {
        long start = System.currentTimeMillis();
        test.testSync();
        long end = System.currentTimeMillis();
        System.out.println("Sync: " + (end - start));


        start = System.currentTimeMillis();
        test.testAtomic();
        end = System.currentTimeMillis();
        System.out.println("Atomic: " + (end - start));


        start = System.currentTimeMillis();
        test.testLongAdder();
        end = System.currentTimeMillis();
        System.out.println("LongAdder: " + (end - start));
    }
}
