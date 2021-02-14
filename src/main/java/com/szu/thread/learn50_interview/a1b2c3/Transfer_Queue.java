package com.szu.thread.learn50_interview.a1b2c3;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *                      TransferQueue 的 transfer 方法  也是线程通信用的，阻塞等待取走
 *                                        take() 阻塞， 取不到一直等待
 * @Date 2021/2/14 14:49
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Transfer_Queue {

    static TransferQueue<String> queue = new LinkedTransferQueue();

    public static void main(String[] args) {

        int length = Utils.letters.length;

        Thread letterThread = new Thread(() -> {
            for (int i = 0; i < length; i++) {

                try {
                    queue.transfer(Utils.letters[i]);
                    Utils.printStr(queue.take());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "letterThread");


        Thread numberThread = new Thread(() -> {
            for (int i = 0; i < length; i++) {
                try {
                    Utils.printStr(queue.take());
                    queue.transfer(Utils.numbers[i]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "numberThread");

        letterThread.start();
        numberThread.start();
    }
}
