package com.szu.thread.learn01_threads;

public class L04_Yield {
    static void testYield(){
        new Thread(()->{
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Star star star star----" + i);
                    if (i % 5 == 0){
                        Thread.yield();
                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Yanni yanni yanni----" + i);
                    if (i % 10 == 0){
                        Thread.yield();
                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        testYield();
    }
}
