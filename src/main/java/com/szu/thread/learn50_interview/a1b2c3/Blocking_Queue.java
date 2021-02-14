package com.szu.thread.learn50_interview.a1b2c3;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *              BlockingQueue put是阻塞方法，只有当其他线程拿走了，他才会往下执行
 *                            take 也是阻塞方法，只有当前线程取到了东西才会走
 *
 * @Date 2021/2/14 14:49
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Blocking_Queue {

    static BlockingQueue<String> letterQueue = new ArrayBlockingQueue(1);
    static BlockingQueue<String> numberQueue = new ArrayBlockingQueue(1);

    public static void main(String[] args) {

        int length = Utils.letters.length;

        Thread letterThread = new Thread(() -> {
            for (int i = 0; i < length; i++) {
                Utils.printStr(Utils.letters[i]);
                try {
                    letterQueue.put("Ur turn");
                    numberQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "letterThread");

        Thread numberThread = new Thread(() -> {
            for (int i = 0; i < length; i++) {
                try {
                    letterQueue.take();
                    Utils.printStr(Utils.numbers[i]);

                    numberQueue.put("Ur turn");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "numberThread");

        letterThread.start();
        numberThread.start();
    }
}
