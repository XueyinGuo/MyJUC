package com.szu.thread.learn06_reentarntlock;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *           ReentrantLock，可以替代 synchronized,一样可重入
 *              但是必须得 lock.unlock(),写在finally语句中
 *
 * @Date 2021/2/8 14:40
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class L28_ReentrantLock {
    ReentrantLock lock = new ReentrantLock();

    public void l1() {

        try {
            int i = 0;
            lock.lock();
            while (i < 10) {

                TimeUnit.SECONDS.sleep(1);
                System.out.println("l1 " + i++);
                if (i == 5)
                    l2();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            /* 解锁一定写在这里 */
            lock.unlock();
        }

    }

    public void l2() {

        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "  l2");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            /* 解锁一定写在这里 */
            lock.unlock();
        }


    }

    public static void main(String[] args) {
        L28_ReentrantLock test = new L28_ReentrantLock();
        new Thread(test::l1, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(test::l2, "t2").start();
    }
}
