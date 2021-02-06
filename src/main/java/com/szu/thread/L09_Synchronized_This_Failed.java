package com.szu.thread;
/*
* Thread 模拟营业大厅叫号机程序
*
* 只有 500 个号码，三个机器去抢
* */
public class L09_Synchronized_This_Failed extends  Thread{

    private final String name;
    private static final int MAX = 500;
    private static int index = 1;

    public L09_Synchronized_This_Failed(String name) {
        this.name = name;
    }

    /*
    * 锁 this， 只是锁的调用这个类中方法的那个对象（本例中是三个线程分别锁了 m1 m2 m3），不存在锁的争抢情况
    *
    * 打开 static 之后 三个线程都是去抢 class 对象那把锁
    * */
    public /* static */ synchronized void print(){
        while(index <= MAX){
            System.out.println(" 当前号码： " + index);
            index++;
        }
    }

    @Override
    public synchronized void run() {
        print();
    }

    public static void main(String[] args) {
        L09_Synchronized_This_Failed m1 = new L09_Synchronized_This_Failed("Machine 1");
        L09_Synchronized_This_Failed m2 = new L09_Synchronized_This_Failed("Machine 2");
        L09_Synchronized_This_Failed m3 = new L09_Synchronized_This_Failed("Machine 3");
        m1.start();
        m2.start();
        m3.start();
    }


}
