package com.szu.thread.learn13_thread_local;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/12 21:34
 */

public class LargeObject {
    byte[] large = new byte[1024*1024*10];

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize largeObject");
    }
}
