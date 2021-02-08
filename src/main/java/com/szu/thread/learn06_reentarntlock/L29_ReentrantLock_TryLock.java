package com.szu.thread.learn06_reentarntlock;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 15:21
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class L29_ReentrantLock_TryLock {

    ReentrantLock lock = new ReentrantLock();

    /* 持有锁五秒 */
    public void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 5; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "  " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* unlock */
            lock.unlock();
        }
    }

    public void m2() {
        boolean locked = false;
        try {
            /*
            * 尝试获取锁
            * */
//            locked = lock.tryLock();
            locked = lock.tryLock(3, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + "  "+ locked);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        L29_ReentrantLock_TryLock test1 = new L29_ReentrantLock_TryLock();
        L29_ReentrantLock_TryLock test2 = new L29_ReentrantLock_TryLock();

        new Thread(test1::m1, "t1").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(test1::m2, "t2").start();
    }
}
