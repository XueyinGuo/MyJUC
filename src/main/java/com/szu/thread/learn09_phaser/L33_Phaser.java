package com.szu.thread.learn09_phaser;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/8 17:03
 */

import java.util.Random;
import java.util.concurrent.Phaser;

public class L33_Phaser extends Phaser {
    public static void main(String[] args) {
        /* 开启一个容量20的phaser */
        phaser.bulkRegister(20);
        for (int i = 0; i < 18; i++) {
            new People("person"+i).start();
        }
        new People("新郎").start();
        new People("新娘").start();

    }
    static L33_Phaser phaser = new L33_Phaser();
    /*
    * return true的时候，表示不继续执行方法了
    * */
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {

        switch (phase){
            case 0:
                System.out.println("所有人到齐了！ " + registeredParties + "\n");
                return false;
            case 1:
                System.out.println("所有人吃饱了！ " + registeredParties + "\n");
                return false;
            case 2:
                System.out.println("其余人都走了！ " + registeredParties + "\n");
                return false;
            case 3:
                System.out.println("新郎追到了新娘，嘿嘿嘿结束！ " + registeredParties + "\n");
                return true;
            default:
                return true;
        }
    }



    /**
     * 每个人是一个线程，每个人都可以来参加婚礼，
     * 参加婚礼的过程是 到达、吃饭、滚蛋（新郎新娘除外）
     * */
    static class People extends Thread{
        static Random random = new Random();
        String name;

        public People(String name) {
            this.name = name;
        }
        /*
        * 每个线程执行自己的方法的时候都会把自己注册到下一个阶段，
        * 或者把自己解注册 */
        public void arrive(){
            threadSleep(getNextInt());
            System.out.println(name + " 到达现场！");
            // 注册自己，下一阶段还有我的事
            phaser.arriveAndAwaitAdvance();
        }
        public void eat(){
            threadSleep(getNextInt());
            System.out.println(name + " 开始吃饭！");
            // 注册自己，下一阶段还有我的事
            phaser.arriveAndAwaitAdvance();
        }
        public void leave(){
            threadSleep(getNextInt());

            if (name == "新娘" || name == "新郎"){
                // TODO 这样写为什么新娘新娘都出轨了？？？？
                // 新郎新娘之外的人都可以走了，随完礼吃完饭就可以走了
                phaser.arriveAndAwaitAdvance();
                System.out.println(name + " 说等到其他人都走了，我们就可以开始嘿嘿嘿了！！！");
            }else{
                phaser.arriveAndDeregister();
                //phaser.register()
                System.out.println(name + " 准备走了！");
            }
        }

        /*
        * 到这一阶段的时候，所有的线程里就剩下新郎新娘了
        * 所以等够了他们两个就可以执行了
        * */
        public void havingSex(){

            threadSleep(getNextInt());
            // 新郎新娘不能走，还要继续执行 havingSex 方法，所以继续注册自己，等待下一个方法的执行
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + " 说其他已经都走了，我们开始嘿嘿嘿吧！！！！！！");

        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            havingSex();
        }


        private void threadSleep(int nextInt) {
            try {
                Thread.sleep(nextInt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private int getNextInt(){
            return random.nextInt(3000);
        }
    }

}

