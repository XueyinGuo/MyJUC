package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *      volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 *
 * @Date 2021/2/7 16:20
 */

public class L17_Volatile_Reference_Is_Visible {
    /*
    * m方法 一直用 flag 控制循环
    * l 为 volatile
    * 用 l.flag 改变 flag的值，看是否会打断m的执行
    * */
    boolean flag = true;
    public static volatile L17_Volatile_Reference_Is_Visible l = new L17_Volatile_Reference_Is_Visible();


    public void m(){
        System.out.println("m start");
        while (flag){

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        new Thread(l::m).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.flag = false;
    }
}
