package com.szu.thread.learn50_interview;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          面试题：
 *          实现一个容器，提供两个方法，add，getSize
 *          写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束，并且强制线程1也结束
 *          （本例中代码有问题）
 * @Date 2021/2/9 14:33
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AddToContainerAndQuit {

    /*
    * volatile 修饰引用类型的时候，引用类型指向的值得内部发生变化，这是检测不到的！
    * 所以这个题用 volatile 解决不了
    * */
    volatile List<Object> list = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock();

    public void add() {
        for (int i = 0; i < 10; i++) {

            try {
                lock.lock();
                Object o = new Object();
                list.add(o);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }
    }


    public void getSize(Thread t) {
        try {
            lock.lock();
            if (list.size() == 5) {
                System.out.println("已经5个了，我不要了！");
                t.interrupt();
            }
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        AddToContainerAndQuit container = new AddToContainerAndQuit();

        Thread writeThread = new Thread(container::add, "writeThread");
        Runnable interruptTask = () -> container.getSize(writeThread);
        Thread readThread = new Thread(interruptTask, "readThread");
        writeThread.start();
        readThread.start();
        Thread.sleep(1000);
        System.out.println(container.list.size());
    }
}
