package com.szu.thread.learn10_read_write_lock;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 19:28
 */

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class L34_ReadWriteLock {

    static ReentrantLock reentrantLock = new ReentrantLock();
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static int num;
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        L34_ReadWriteLock test = new L34_ReadWriteLock();
        /* 使用普通的可重入锁 也是排它锁 */
//        Runnable task1 = () -> test.read(reentrantLock);
//        Runnable task2 = () -> test.write(reentrantLock, new Random().nextInt(50));
        /* 使用读写锁，读线程可以同时进行 */
        Runnable task1 = () -> test.read(readLock);
        Runnable task2 = () -> test.write(writeLock, new Random().nextInt(50));
        for (int i = 0; i < 50; i++) {
            new Thread(task1).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(task2).start();
        }
    }

    public void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over");
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public void write(Lock lock, int val) {
        try {
            lock.lock();
            Thread.sleep(1000);
            num = val;
            System.out.println("write over");
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }
}
