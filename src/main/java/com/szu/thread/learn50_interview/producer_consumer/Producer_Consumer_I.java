package com.szu.thread.learn50_interview.producer_consumer;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/9 17:40
 */

import java.util.ArrayList;
import java.util.List;

public class Producer_Consumer_I {
    static int MAX = 30;
    List list = new ArrayList(MAX);
    int count = 0;

    public synchronized void put(Object o){
        while (count == MAX){
            try {
                /*
                * wait 会释放当前的锁，让其他想获得这把锁的线程继续执行自己的代码
                * */
                this.wait();
                // this.notifyAll(); 不能写在这里的原因是：
                // 如果两类线程（生产者消费者线程）都在自己wait之后直接叫醒对方，就会在这里陷入while(true) 的死循环
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(o);
        count++;
        System.out.println(Thread.currentThread().getName() + "  " + list.size());
        /*
         * 等到自己添加了一个元素之后再叫醒其他线程（不管是消费者还是生产者）
         * */
        this.notifyAll();
    }

    public synchronized Object get(){
        while (count==0){
            try {
                /*
                 * wait 会释放当前的锁，让其他想获得这把锁的线程继续执行自己的代码
                 * */
                this.wait();
                // this.notifyAll(); 不能写在这里的原因是：
                // 如果两类线程（生产者消费者线程）都在自己wait之后直接叫醒对方，就会在这里陷入while(true) 的死循环
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object remove = list.remove(0);
        count--;
        System.out.println(Thread.currentThread().getName() + "  " + list.size());
        /*
        * 等到自己移除了一个元素之后再叫醒其他线程（不管是消费者还是生产者）
        * */
        this.notifyAll();
        return remove;
    }

    public static void main(String[] args) {
        Producer_Consumer_I test = new Producer_Consumer_I();

        int numOfProduces = 5;
        Thread[] producers = new Thread[numOfProduces];
        for (int i = 0; i < numOfProduces; i++) {
            producers[i] = new Thread(()->{
                while(true){
                    for (int j = 0; j < 20; j++) {
                        test.put(new Object());
                    }
                }

            },"p"+i);
        }

        int numOfConsumers = 20;
        Thread[] consumers = new Thread[numOfConsumers];
        for (int i = 0; i < numOfConsumers; i++) {
            consumers[i] = new Thread(()->{
                while(true){
                    for (int j = 0; j < 5; j++) {
                        test.get();
                    }
                }

            },"c"+i);
        }


        for (int i = 0; i < numOfProduces; i++) {
            producers[i].start();
        }
        for (int i = 0; i < numOfConsumers; i++) {
            consumers[i].start();
        }
    }

}


