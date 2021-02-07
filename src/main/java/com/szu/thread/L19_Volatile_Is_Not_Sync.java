package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *           volatile 变量并不会是变量同步，只是保证线程之间的可见性
 *
 * @Date 2021/2/7 16:32
 */

import java.util.ArrayList;

public class L19_Volatile_Is_Not_Sync {
    /*
    * volatile 变量并不会是变量同步，只是保证线程之间的可见性
    * */
    volatile int count = 0;

    public void add(){
        for (int i = 0; i < 10000; i++) {
            // count 不是原子性操作，所以没有同步的限制就会导致线程不安全
            count++;
        }
    }

    public static void main(String[] args) {
        L19_Volatile_Is_Not_Sync l = new L19_Volatile_Is_Not_Sync();
        ArrayList<Thread> threads = new ArrayList<>();
        // 创建10个线程
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(l::add));
        }
        // 启动所有线程
        threads.forEach((t) ->{
            t.start();
        });
        //等待所有线程结束
        threads.forEach((t)->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(l.count);
    }

}
