package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *
 *
 * @Date 2021/2/20 12:51
 */

public class Reorder_test {
    static Test test = new Test();

    public static void main(String[] args) {
        /*
        * 一个线程死循环，不断更改test的引用
        * */
        new Thread(()->{
            for (;;){
                test = new Test();
            }
        }).start();

        /*
        * 万一发生指令重排序，Test 的 num 属性肯定是 0
        * */
        for (int j = 0; j < 48; j++) {
            new Thread(()->{
                for(;;){
                    if (test.num == 0){
                        System.out.println("Oops!");
                    }
                }
            }).start();
        }


    }
}
class Test{

    int num = 10;

}