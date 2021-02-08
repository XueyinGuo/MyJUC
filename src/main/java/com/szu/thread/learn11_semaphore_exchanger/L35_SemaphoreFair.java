package com.szu.thread.learn11_semaphore_exchanger;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 20:10
 */

import java.util.concurrent.Semaphore;

public class L35_SemaphoreFair {
    /* 50 个线程按顺序获取信号量 */
    Semaphore semaphore = new Semaphore(2, true);

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

    public static void main(String[] args) throws InterruptedException {
        L35_SemaphoreFair test = new L35_SemaphoreFair();
        for (int i = 0; i < 50; i++) {
            new Thread(test::runWithPermission, "t"+i).start();
            Thread.sleep(50);
        }
    }
}
