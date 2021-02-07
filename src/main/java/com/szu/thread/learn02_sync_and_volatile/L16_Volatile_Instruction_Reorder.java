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

 * @Date 2021/2/7 15:37
 */

public class L16_Volatile_Instruction_Reorder {}

/*
* 双空判断的饿汉式加载
* */
class SingletonLazyInit{

    private /* volatile */ SingletonLazyInit instance;

    Object lock = new Object();

    private SingletonLazyInit(){}
    //获取单例实例
    public SingletonLazyInit getInstanceLazy(){
        if (instance == null){
            synchronized (lock){
                if (instance == null){
                    instance = new SingletonLazyInit();
                }
            }
        }
        return instance;
    }

}

/*
* 饿汉式单例模式
* */
class SingletonHungary{

    private SingletonHungary(){}

    public static final SingletonHungary INSTANCE = new SingletonHungary();

    public static SingletonHungary getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonHungary instance1 = getInstance();
        SingletonHungary instance2 = getInstance();
        System.out.println(instance1 == instance2);
    }
}