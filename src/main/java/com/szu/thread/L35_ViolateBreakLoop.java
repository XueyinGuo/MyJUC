package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/4/24 18:56
 */

public class L35_ViolateBreakLoop {


    boolean run = true;
    volatile int c = 1;

    public static void main(String[] args) throws InterruptedException {

        L35_ViolateBreakLoop v = new L35_ViolateBreakLoop();
        //thread 1
        new Thread(() -> {
            while (v.run) {
                /*
                * 可以在运行的时候使用JVM参数：-Xint 来关闭JIT，用纯解释模式运行这段代码试试看
                * 在解释模式下加不加这一行都可以跳出循环。
                *
                * 由于使用的变量 run 没有volatile修饰，不一定遵循可见性原则，而在这段代码的运行过程中，
                * JVM发现了这个循环是一个热点任务，一直在运行，于是将其进行JIT编译。
                * 编译时它发现变量c可以不遵循可见性原则，那么如果不保证可见性，那么这段代码事实上就是一个死循环。
                *
                * JIT直接把这个循环编译成了while(true)，导致对变量 run 在主线程的改变它是永远也看不到的，
                * 虽然这么做和正常执行代码的结果有出入，但是并不违反没有volatile下不保证可见性的内存模型。
                * JIT这么做每次循环都能减少一次读取变量的开销，提高了运行效率
                *
                * 一旦加上这行代码后。由于变量 c 是一个volatile的变量，
                * JIT编译器认为不能使用前面那种激进的优化策略，于是while中判断 run 的逻辑被保留了下来。
                *
                * 虽然它可以跳出循环，但是JVM内存模型中定义了不加volatile的变量不保证内存可见性，
                * 这里的变量c依然是没有线程间可见性的，仅仅是当前这个代码的语境和HotSpot现有的JIT编译器实现让这里产生了这样的结果罢了，
                * 不能在正常写代码的时候依靠类似这种方式来保证volatile的语义。
                * */
                int a = v.c;    /* 如果注释这行，线程 1 无法中止 */

            }
        }).start();

        //thread 2
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            v.run = false;
            System.out.println("set run false");

        }).start();
    }


}


