package com.szu.thread.learn50_interview;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/9 18:57
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer_Consumer_II_Condition {
    static int MAX = 1000;
    int count = 0;
    static ReentrantLock lock = new ReentrantLock();
    static Condition producerCondition = lock.newCondition();
    static Condition consumerCondition = lock.newCondition();

    List list = new ArrayList(MAX);

    public void put(Object o) {
        try {
            lock.lock();
            while (count == MAX) {
                // consumerCondition.signalAll() 不能写在这里的原因是：
                // 如果两类线程（生产者消费者线程）都在自己wait之后直接叫醒对方，就会在这里陷入while(true) 的死循环
                producerCondition.await();
            }
            list.add(o);
            count++;
            System.out.println(Thread.currentThread().getName() + "  " + list.size());
            consumerCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object get() {
        Object remove = null;
        try {
            lock.lock();
            while (count == 0) {
                // 释放当前的锁
                /* 当前线程进入 consumerCondition 队列等着 */
                consumerCondition.await();
                // producerCondition.signalAll() 不能写在这里的原因是：
                // 如果两类线程（生产者消费者线程）都在自己wait之后直接叫醒对方，就会在这里陷入while(true) 的死循环
            }
            remove = list.remove(0);
            count--;
            /* 等到自己拿走了一个元素之后再叫醒生产者线程 */
            System.out.println(Thread.currentThread().getName() + "  " + list.size());
            producerCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {

            lock.unlock();
            return remove;
        }
    }

    public static void main(String[] args) {
        Producer_Consumer_II_Condition test = new Producer_Consumer_II_Condition();

        int numOfProduces = 5;
        Thread[] producers = new Thread[numOfProduces];
        for (int i = 0; i < numOfProduces; i++) {
            producers[i] = new Thread(() -> {
                while (true) {
                    for (int j = 0; j < 20; j++) {
                        test.put(new Object());
                    }
                }

            }, "p" + i);
        }


        int numOfConsumers = 20;
        Thread[] consumers = new Thread[numOfConsumers];
        for (int i = 0; i < numOfConsumers; i++) {
            consumers[i] = new Thread(() -> {
                while (true) {
                    for (int j = 0; j < 5; j++) {
                        test.get();
                    }
                }

            }, "c" + i);
        }


        for (int i = 0; i < numOfProduces; i++) {
            producers[i].start();
        }
        for (int i = 0; i < numOfConsumers; i++) {
            consumers[i].start();
        }
    }
}
