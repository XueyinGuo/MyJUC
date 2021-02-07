package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/7 17:02
 */

public class L23_Do_Not_Lock_String {

    String s1 = "SZU";
    String s2 = "SZU";
    /* m1 锁 s1 ,但是只有一个方法能进去*/
    void m1(){
        synchronized (s1){
            try {

                while(true){
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 来了就是深圳人");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /* m2 锁 s2,但是只有一个方法能进去 */
    void m2(){
        synchronized (s2){
            try {

                while(true){
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 来了就是深圳人");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        L23_Do_Not_Lock_String l1 = new L23_Do_Not_Lock_String();
        L23_Do_Not_Lock_String l2 = new L23_Do_Not_Lock_String();
        // s1 s2 字面量一样，两个指针都是指向同一个位置
        System.out.println(l1.s1 == l1.s2);
        /* 创建了两个对象，分别调用两个对象的不同方法，但是两个方法锁的是同一个对象 */
        new Thread(l1::m1, "t1").start();
        new Thread(l2::m2, "t2").start();
    }

}
