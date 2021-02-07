package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *       锁力度粗化
 * @Date 2021/2/7 16:45
 */

import java.util.concurrent.TimeUnit;

public class L21_Use_Big_Lock {

    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;
    int count5 = 0;

    /*
    * 过多的重复加锁，可以粗化
    * */
    void m1() {

        synchronized (this) {
            count1++;
        }
        synchronized (this) {
            count2++;
        }
        synchronized (this) {
            count3++;
        }
        synchronized (this) {
            count4++;
        }
        synchronized (this) {
            count5++;
        }


    }
    /*
    * 避免了重复的加锁释放锁的过程，锁的粗化
    * */
    synchronized void m2() {


        count1++;
        count2++;
        count3++;
        count4++;
        count5++;

    }

}
