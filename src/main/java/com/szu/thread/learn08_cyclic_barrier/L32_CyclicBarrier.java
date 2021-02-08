package com.szu.thread.learn08_cyclic_barrier;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          限流场景？？？不对！只有等凑够线程数才行
 *
 * @Date 2021/2/8 16:30
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class L32_CyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Run());
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        /*
                        * 线程的run方法中调用 cyclicBarrier.await()，使当时定义 cyclicBarrier 对象中的
                        * parties值 -1；减到0的时候开始执行 cyclicBarrier 的 run 方法
                        * */
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

class Run implements Runnable{
    @Override
    public void run() {
        System.out.println("满员了，开车！");
    }
}