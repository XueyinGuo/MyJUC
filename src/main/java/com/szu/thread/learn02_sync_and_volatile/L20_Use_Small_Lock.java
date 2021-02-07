package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *       业务逻辑中只有下面这句需要sync，这时不应该给整个方法上锁
 *       采用细粒度的锁，可以使线程争用时间变短，从而提高效率
 * @Date 2021/2/7 16:45
 */

import java.util.concurrent.TimeUnit;

public class L20_Use_Small_Lock {

    int count = 0;

    synchronized void m1() {
        //线程睡觉都加了锁，闲得蛋疼
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //业务逻辑中只有下面这句需要加锁，这时不应该给整个方法上锁
        count ++;

        //线程睡觉都加了锁，闲得蛋疼
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //业务逻辑中只有下面这句需要sync，这时不应该给整个方法上锁
        //采用细粒度的锁，可以使线程争用时间变短，从而提高效率
        synchronized(this) {
            count ++;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
