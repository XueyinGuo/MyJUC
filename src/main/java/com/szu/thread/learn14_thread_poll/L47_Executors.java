package com.szu.thread.learn14_thread_poll;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/18 16:40
 */

import java.util.concurrent.Executors;

public class L47_Executors {

    /*
    * 线程池的工厂
    * */
    public static void main(String[] args) {

        Executors.defaultThreadFactory();

        Executors.newCachedThreadPool();

        /* 0 core，  Integer.MAX_VALUE 非核心线程 */
        Executors.newCachedThreadPool(Executors.defaultThreadFactory());

        /* 固定大小的线程池，核心非核心就4个，而且一直活着 */
        Executors.newFixedThreadPool(4);

    }
}
