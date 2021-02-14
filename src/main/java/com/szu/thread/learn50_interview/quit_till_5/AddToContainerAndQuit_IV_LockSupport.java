package com.szu.thread.learn50_interview.quit_till_5;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/9 17:16
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class AddToContainerAndQuit_IV_LockSupport {

    List<Object> list = new ArrayList<>();
    //把线程声明再外边是因为 LockSupport.unpark() 需要知道两个线程的存在
    static Thread t1;
    static Thread t2;

    public void add() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Object o = new Object();
            if (i == 5){
                // 写线程加到第五个之后把自己拴住，把读线程解栓
                LockSupport.unpark(t2);
                LockSupport.park();
            }
            System.out.println("t1 add  " + i);
            list.add(o);
        }
        System.out.println("\n\nt1 is going to die");
    }

    public void getSize() throws InterruptedException {
        while(true){
            // 如果是读线程自己先启动，就把自己先拴住，让写线程去先执行
            // 再被叫醒的时候就已经是写线程写了五个了
            LockSupport.park();
            if (list.size() == 5){
                break;
            }
        }
        System.out.println("\n\nt2 is going to die\n\n");
        // 读线程自己死的时候，把写线程的门栓打开
        LockSupport.unpark(t1);
    }

    public static void main(String[] args) {
        AddToContainerAndQuit_IV_LockSupport test = new AddToContainerAndQuit_IV_LockSupport();

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

        t1 = new Thread(writeTask);
        t2 = new Thread(readTask);
        t1.start();
        t2.start();
    }

}
