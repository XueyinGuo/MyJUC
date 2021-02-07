package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description synchronized 父类子类可重入
 * @Date 2021/2/7 14:03
 */

public class L13_Synchronized_ReEnter_Into_Super_Class {

    public static void main(String[] args) {
        Son son = new Son();
        new Thread(son::talkToFather, "t1").start();
        new Thread(son::talkToFather, "t2").start();
        new Thread(son::talkToFather, "t3").start();
    }
}

class Father {
    /* 这样调用父类的 synchronized 方法的时候，两个类的this是同一个this */
    public synchronized void talk() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "对他爹说：你强尼玛！");
    }
}

class Son extends Father {
    /* 这样调用父类的 synchronized 方法的时候，两个类的this是同一个this */
    public synchronized void talkToFather() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "想和他爹说话");
        super.talk();
    }
}

