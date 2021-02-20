package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 20:53
 */

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class testqueue {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<Integer> objects = new LinkedTransferQueue<>();



        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    objects.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(()->{

                objects.put(finalI);

            }, "t"+i).start();
        }
    }
}


