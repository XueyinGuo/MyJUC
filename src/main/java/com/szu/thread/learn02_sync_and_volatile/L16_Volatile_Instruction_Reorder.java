package com.szu.thread.learn02_sync_and_volatile;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          单例模式的双锁（DCL）
 *          DCL 需不需要 volatile
 *          答案是需要加 volatile
 *          如果不加的话，可能会因为指令重排序导致非常罕见的出错情况
 *             要的。因为有可能会因为在巨量高并发的情况下因为指令重排序导致非常罕见的错误。

            以 `instance = new SingletonLazyInit();`为例，创建一个对象需要三个步骤：
                1.给对象申请内存空间，申请好内存之后，对象中所有属性值都是默认值(int 类型为 0， boolean 为 false 等等)

                2.给对象属性赋值

                3.把对象的引用交给接收值，也就是引用交给 instance

            指令重排序的影响下会可能导致 步骤2与步骤3 颠倒，导致instance中的属性值都是默认值，从而导致很罕见的程序错误
           ====================================================================
           ====================================================================
            突然感觉上边的逻辑有问题， 2021-04-22 特此测试
            即使发生了指令重排序，真的会让方法退出临界区 直接释放锁吗？
            讲道理不会的把！
            我们知道 通过 volatile 是通过 lock 指令锁定一小块内存地址来操作的
            导致这块地址想要成功访问的时候需要先尝试 lock 指令知否锁定成功
            如果想要锁定成功，必须等上一个锁定这片内存的线程【或者cpu】释放（我理解的好像就是一种锁）
            而释放这个锁之前的线程【或者CPU】必须是需要写回去之后才能释放
            从而做到了 loadStoreFence

            但是转念一想：本来 sync 就已经保证了 可见性和原子性，那么我为什么还要用 volatile 呢？
            即使发生了指令重排序，真的会让方法退出临界区 直接释放锁吗？
            讲道理不会的把！
            释放锁之前就必须需要写回主存，为什么还要多加一个 volatile 加一个 LoadStoreFence 呢？
           ====================================================================
           ====================================================================
 * @Date 2021/2/7 15:37
 */

import java.util.ArrayList;
import java.util.List;

public class L16_Volatile_Instruction_Reorder {
}

/*
 * 双空判断的饿汉式加载
 * */
class SingletonLazyInit {

    public static /* volatile */ SingletonLazyInit instance;

    public int num;


    static final Object lock = new Object();

    private SingletonLazyInit() {
        num = 100;
    }

    //获取单例实例
    public static SingletonLazyInit getInstanceLazy() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new SingletonLazyInit();
                }
            }
        }
        return instance;
    }

    public static void setInstanceNull() {
        synchronized (lock) {
            instance = null;
        }
    }

    public static void main(String[] args) {


        List<SingletonLazyInit> list = new ArrayList<>();

        /* 不断获取 instance，放入 list 中，如果list不为空就先不往里放了 */
        new Thread(() -> {
            synchronized (list) {
                if (list.isEmpty()) {
                    SingletonLazyInit instanceLazy = SingletonLazyInit.getInstanceLazy();
                    list.add(instanceLazy);
                }
            }
        }).start();
        /* 只要list 不空，就取出来，看看是否发生了指令重拍，如果发生了重拍，而且把重排完之后的没调用构造方法的 instance 赋值回主存，那么 num 的值必为 0 */
        new Thread(() -> {
            synchronized (list) {
                if (!list.isEmpty()) {
                    SingletonLazyInit remove = list.remove(0);
                    if (remove != null && remove.num == 0)
                        System.out.println("FUCK");
                }
            }
        }).start();


        while (true) {
            setInstanceNull();
        }
    }

}

/*
 * 饿汉式单例模式
 * */
class SingletonHungary {

    private SingletonHungary() {
    }

    public static final SingletonHungary INSTANCE = new SingletonHungary();

    public static SingletonHungary getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonHungary instance1 = getInstance();
        SingletonHungary instance2 = getInstance();
        System.out.println(instance1 == instance2);
    }
}