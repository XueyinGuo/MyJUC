package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
        volatile 保证线程之间的可见性
        堆中的变量在使用的时候，线程会把这个变量先拷贝到自己的工作内存，堆中变量啥时候更新
        线程工作内存中不知道
        （CPU的缓存一致性协议）
 * @Date 2021/2/7 15:25
 */

public class L15_Volatile_Visible_Between_Threads {
    // 打开 volatile m方法立马结束
    /* volatile */ boolean flag = true;

    public void m(){
        System.out.println(" m start ");
        while (flag){

        }
        System.out.println(" m end ");
    }

    public static void main(String[] args) {
        L15_Volatile_Visible_Between_Threads l = new L15_Volatile_Visible_Between_Threads();
        new Thread(l::m).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.flag = false;
    }

}
