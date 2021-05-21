package com.szu.thread.learn01_threads;
/*
* t1线程中调用 t2.join()
*
*
*   T1        T2
*     |
*     |
*     |
*     |------>|
*             |
*             |
*             |
*     |<------| (线程2执行结束)
*     |
*     |
* */
public class L05_Join {

    static void testJoin(){


        Thread t2 = new Thread(()->{
            try {
                for (int i = 0; i < 1000; i++) {
                    if (i % 100 == 0){
                        Thread.sleep(1000);
                        System.out.println("t2 is still runing");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("t2 finishing");
        });

        Thread t1 = new Thread(()->{
            try {
                System.out.println("Now start join\n");
                t2.join();
                System.out.println("Back to t1");
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        t1.start();
        /*
        * 为什么这里加上睡一觉之后， t1就会执行完
        * */
        // TODO 为什么这里加上睡一觉之后， t1就会执行完
//        try {
//            Thread.sleep(1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        t2.start();
    }

    public static void main(String[] args) {
        testJoin();
    }

}
