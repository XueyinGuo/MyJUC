package com.szu.thread.learn50_interview;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          实现一个容器，提供两个方法，add，getSize
            写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束，
 *
 * @Date 2021/2/9 16:57
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AddToContainerAndQuit_III_CountDownLatch {

    List<Object> list = new ArrayList<>();
    //两个门栓， readLatch 是 读线程t2执行getSize的门栓，
    // writeLatch 是 写线程 t1 执行 add 方法的门栓
    CountDownLatch writeLatch = new CountDownLatch(1);
    CountDownLatch readLatch = new CountDownLatch(1);


    public void add() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Object o = new Object();
            if (i == 5){
                // 写线程加到第五个之后把自己拴住，把读线程解栓
                readLatch.countDown();
                writeLatch.await();
            }
            System.out.println("t1 add  " + i);
            list.add(o);
        }
        System.out.println("t1 is going to die");
    }

    public void getSize() throws InterruptedException {
        while(true){
            // 如果是读线程自己先启动，就把自己先拴住，让写线程去先执行
            // 再被叫醒的时候就已经是写线程写了五个了
            readLatch.await();
            if (list.size() == 5){
                break;
            }
        }
        System.out.println("\n\nt2 is going to die\n\n");
        // 读线程自己死的时候，把写线程的门栓打开
        writeLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        AddToContainerAndQuit_III_CountDownLatch test = new AddToContainerAndQuit_III_CountDownLatch();
        // 写线程的run方法
        Runnable writeTask = ()-> {
            try {
                test.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 读线程的run方法
        Runnable readTask = ()-> {
            try {
                test.getSize();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(writeTask);
        Thread t2 = new Thread(readTask);
        t1.start();
        t2.start();
    }
}
