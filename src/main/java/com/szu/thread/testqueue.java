package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 20:53
 */

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;

public class testqueue {

    public static void main(String[] args) throws InterruptedException {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.add(1);
        priorityQueue.add(6);
        priorityQueue.add(5);
        priorityQueue.add(7);
        priorityQueue.add(8);
        priorityQueue.add(18);
        priorityQueue.add(0);

        while(!priorityQueue.isEmpty()){
            System.out.println(priorityQueue.poll());
        }
    }
}


