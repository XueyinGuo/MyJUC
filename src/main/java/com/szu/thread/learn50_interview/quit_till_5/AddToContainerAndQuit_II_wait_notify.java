package com.szu.thread.learn50_interview.quit_till_5;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          面试题：
 *          实现一个容器，提供两个方法，add，getSize
 *          写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束，【并且强制线程1也结束】
 *          (代码仍有问题，原题中并没有强制线程1也结束)
 * @Date 2021/2/9 14:33
 */

import java.util.ArrayList;
import java.util.List;

public class AddToContainerAndQuit_II_wait_notify {


    List<Object> list = new ArrayList<>();

    static Thread t1 ,t2;

    public synchronized void add() {
        for (int i = 0; i < 10; i++) {
            try {
                if (i == 5){
                    this.notify();
                    this.wait();
                    if (Thread.interrupted()){
                        System.out.println(Thread.currentThread().getName() + "  is going to die");
                        return;
                    }
                }

                Object o = new Object();
                list.add(o);
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName()+" has been interrupted!");
                e.printStackTrace();
            } finally {

            }
        }
    }

    public synchronized void getSize(Thread t) {
        try {
            while(true){
                if (list.size() == 5) {
                    System.out.println("Its already"+ list.size() + ", I need no more");
                    // TODO 线程2已经正确退出，但是为什么线程1的腿没被打折？？？
                    /*
                    * interrupt()方法只是改变中断状态，不会中断一个正在运行的线程。
                    * TODO 那我怎么在t2 线程中 改变 t1线程的状态呢？
                    *  线程被阻塞的时候调用他的 interrupt 方法，会被清除flag，白调用一通
                    * */
                    t.interrupt();
                    break;
                }
                this.wait();
            }
            System.out.println(Thread.currentThread().getName()+" is going to die");
        } catch (Exception e) {
        } finally {
            // TODO 是不是这里的 叫醒给 t1 的interrupt重新设置成了false ？？？（debug的时候看来不是的）
            // TODO 这个方法执行完在栈帧中弹出之后，崩回到lambda表达式的时候，线程的interrupted属性又回去了？？？？
            /*
            * 线程被阻塞的时候调用他的 interrupt 方法，会被清除flag，白调用一通
            * */
            this.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddToContainerAndQuit_II_wait_notify test = new AddToContainerAndQuit_II_wait_notify();


        t1 = new Thread(test::add, "t1");

        Runnable interruptTask = ()->test.getSize(t1);

        t2 = new Thread(interruptTask, "t2");

        t2.start();
        t1.start();
        t1.join();
        t2.join();

        System.out.println(test.list.size());
    }
}
