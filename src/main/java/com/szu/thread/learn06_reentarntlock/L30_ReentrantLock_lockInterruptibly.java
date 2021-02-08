package com.szu.thread.learn06_reentarntlock;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 15:53
 */

import java.util.concurrent.locks.ReentrantLock;

public class L30_ReentrantLock_lockInterruptibly {
    ReentrantLock lock = new ReentrantLock();

    /* 正常锁定 */
    public void m1() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " got the Lock");
            Thread.sleep(10000);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    /* Interrupted */
    public void m2(){
        try {
            lock.lockInterruptibly();
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " Interrupted");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        L30_ReentrantLock_lockInterruptibly test = new L30_ReentrantLock_lockInterruptibly();

        Thread t1 = new Thread(test::m1, "t1");
        Thread t2 = new Thread(test::m2, "t2");
        t1.start();
        t2.start();
        Thread.sleep(3000);
        /* 打断 t2 */
        t2.interrupt();
    }
}
