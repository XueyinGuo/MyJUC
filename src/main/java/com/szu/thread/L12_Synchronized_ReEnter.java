package com.szu.thread;

/*
* synchronized 可重入
*
* 线程回家，家里只能有一个人，只有进了大门之后才能进屋门
* */
public class L12_Synchronized_ReEnter {

    public synchronized void getIntoTheGate() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "进了大门");
        getIntoTheHouse();
    }

    public synchronized void getIntoTheHouse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "进了卧室");
        getOut();
    }

    private synchronized void getOut() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "终于出来了\n");
    }


    public static void main(String[] args) {
        L12_Synchronized_ReEnter l = new L12_Synchronized_ReEnter();
        new Thread(l::getIntoTheGate, "t1").start();
        new Thread(l::getIntoTheGate, "t2").start();
        new Thread(l::getIntoTheGate, "t3").start();
    }
}
