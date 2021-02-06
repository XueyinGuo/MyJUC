package com.szu.thread;
/*
* 创建线程的三种方式
* 1.继承Thread
* 2.实现Runnable
* 3.线程池
* 4.lambda
* */
public class L02_CreateThreadInThreeWays {

    static class MyThread extends Thread{
        @Override
        public void run() {

            System.out.println("This is My Own Thread");
        }
    }

    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("This is Runnable");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRunnable()).start();

        new Thread(()->{
            System.out.println("This is lambda...");
        }).start();
    }

}
