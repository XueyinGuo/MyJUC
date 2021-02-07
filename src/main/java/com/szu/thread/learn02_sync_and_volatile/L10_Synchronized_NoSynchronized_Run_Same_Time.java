package com.szu.thread.learn02_sync_and_volatile;

public class L10_Synchronized_NoSynchronized_Run_Same_Time {

    public synchronized void m1(){
        System.out.println("This is m1");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Now m1 is wake up");
        System.out.println(Thread.currentThread().getName());
    }


    public void m2(){
        System.out.println("this is m2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        L10_Synchronized_NoSynchronized_Run_Same_Time l = new L10_Synchronized_NoSynchronized_Run_Same_Time();
        new Thread(l::m1, "thread 1").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(l::m2, "thread 2").start();
    }
}
