package com.szu.thread.learn14_thread_poll;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/17 21:34
 */

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class L46_CompletableFuture {

    public static void main(String[] args) {

        Supplier supplier = ()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "sdfasd";
        };


        CompletableFuture.supplyAsync(supplier)
                .thenAccept(System.out::println);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
