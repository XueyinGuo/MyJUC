package com.szu.thread.learn50_interview.a1b2c3;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 14:49
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Lock_Condition {

    static ReentrantLock lock = new ReentrantLock();
    static Condition letterCondition = lock.newCondition();
    static Condition numberCondition = lock.newCondition();

    public static void main(String[] args) {

        int length = Utils.letters.length;

        Thread letterThread = new Thread(() -> {
            try{
                lock.lock();
                for (int i = 0; i < length; i++) {
                    Utils.printStr(Utils.letters[i]);
                    numberCondition.signal();
                    letterCondition.await();
                }
                numberCondition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }, "letterThread");



        Thread numberThread = new Thread(() -> {
            try{
                lock.lock();
                for (int i = 0; i < length; i++) {
                    Utils.printStr(Utils.numbers[i]);
                    letterCondition.signal();
                    numberCondition.await();
                }
                letterCondition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }, "numberThread");


        letterThread.start();
        numberThread.start();
    }
}
