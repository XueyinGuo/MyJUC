package com.szu.thread.learn11_semaphore_exchanger;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 20:10
 */

import java.util.concurrent.Semaphore;

public class L35_Semaphore {
    /* 非公平，50个线程谁抢到算谁的 */
    Semaphore semaphore = new Semaphore(2);

    public void runWithPermission(){
        try {
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println( Thread.currentThread().getName() );
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        L35_Semaphore test = new L35_Semaphore();
        for (int i = 0; i < 50; i++) {
            new Thread(test::runWithPermission, "t"+i).start();
        }
    }
}
