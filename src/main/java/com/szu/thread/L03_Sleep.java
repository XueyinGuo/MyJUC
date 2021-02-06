package com.szu.thread;

import java.util.concurrent.TimeUnit;

public class L03_Sleep {

    static void testSleep(){
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
               try{
                   System.out.println("The Falling Star!");
                   TimeUnit.MICROSECONDS.sleep(500000);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }

        }).start();
    }

    public static void main(String[] args) {
        testSleep();
    }
}
