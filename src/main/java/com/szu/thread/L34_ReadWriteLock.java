package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 20:53
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

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

    public static void main(String[] args) throws InterruptedException {
        L34_ReadWriteLock test = new L34_ReadWriteLock();
        /* 使用读写锁，读线程可以同时进行 */
        Runnable task1 = () -> test.read(readLock);
        Runnable task2 = () -> test.write(writeLock, new Random().nextInt(50));

        /*
         * 首先启动4个读线程,每个读线程都是 exclusive
         * 每个读线程睡1s钟，三个执行完需要 4 秒钟
         *
         * E -> E -> E -> E
         * 队列状态如上所示
         * */
        createWriteThread(task2, 4, "write");
        /*
         * 主线程睡1s，保证现在是读线程在占着锁
         *
         * */
        Thread.sleep(1000);
        /*
         * 启动 10 个读线程，因为此时是 写线程在占用锁，
         * 所以读线程已经加到了队列中
         * 此时前边三个写线程全部执行完至少还剩 3s
         *
         * E -> E -> E -> R -> ... -> R
         * */
        createReadThread(task1, 10, "read");
        Thread.sleep(1000);
        /*
         * 再启动 3 个 写线程，前三个中的某个写线程还没有释放锁
         * 所以这三个线程也会加入队列
         * 此时前边三个写线程全部执行完至少还剩 2s
         *
         * E -> E -> E -> R -> ... -> R -> E -> E -> E
         * */
        createWriteThread(task2, 3, "write");
        /*
         * 主线程睡1s，保证现在是读线程在占着锁
         * 此时前边三个写线程全部执行完至少还剩 1s
         * */
        Thread.sleep(1000);
        /*
         * 启动 10 个读线程，因为此时是 写线程在占用锁，
         * 所以读线程已经加到了队列中
         *
         * E -> E -> E -> R -> ... -> R -> E -> E -> E -> R -> R ....
         * */
        createReadThread(task1, 10, "read");

        /*
         * 最后的执行顺序，请大家自己打印看
         * 前四个 写锁
         * 十个 读锁
         * 三个 写锁
         * 十个 读锁
         * */
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


    public static void createWriteThread(Runnable task2, int i2, String write) {
        for (int i = 0; i < i2; i++) {
            new Thread(task2, write + i).start();
        }
    }

    public static void createReadThread(Runnable task1, int i2, String read) {
        for (int i = 0; i < i2; i++) {
            new Thread(task1, read + i).start();
        }
    }
}
