package com.szu.thread.learn04_atomic;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 13:54
 */

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class L26_AtomicInteger {

    AtomicInteger ai = new AtomicInteger(0);

    public void add(){
        for (int i = 0; i < 10000; i++) {
            ai.incrementAndGet();
        }

    }

    public static void main(String[] args) {
        L26_AtomicInteger l = new L26_AtomicInteger();
        ArrayList<Thread> threads = new ArrayList<>();
        // 创建十个线程，塞入线程集合
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(l::add, "t"+i));
        }
        // 启动每个线程
        threads.forEach(o->{
            o.start();
        });
        //等待所有线程结束在输出 ai 的值
        threads.forEach(o->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(l.ai);

    }
}
