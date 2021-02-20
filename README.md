# 1. Synchronized 是 怎么实现的呢？

https://blog.csdn.net/XueyinGuo/article/details/110913371

**详细讲述了对象加锁的时候发生了什么（对象头MarkWord中和线程之间的互动）**

**详细讲述了锁升级过程**

# 2. 什么情况用什么锁
### 2.1 系统锁
执行时间长，线程数多
### 2.2 自旋锁
加锁代码执行时间短，而且线程数少下的情况

+  代码执行时间短：执行完的时间 < 内核态与用户态切换申请系统锁的时间，当然自旋效率更高
+ 如果虚拟机线程现在有几百个，虽然执行时间短，但是每个线程都要用 `while(true)`循环来耗费CPU，也是受不了这种资源消耗的

# 3. Synchronized 锁定一个字符串或者 Integer，会发生什么？ 
+ Integer有特殊处理，只要里边的值一旦发生改变，就会变成一个新对象
+ String常量是因为每个类中如果有这个字符串在使用，用的都是同一个字符串，如果两个毫不相干的线程同时锁定一个字符串常量，可能会发生甚多莫名其妙的现象

# 4. DCL需不需要加`volatile`关键字
###  要的。因为有可能会因为在巨量高并发的情况下因为指令重排序导致非常罕见的错误。

以 `instance = new SingletonLazyInit();`为例，创建一个对象需要三个步骤：
   + 1.给对象申请内存空间，申请好内存之后，对象中所有属性值都是默认值(int 类型为 0， boolean 为 false 等等)
   
   + 2.给对象属性赋值

   + 3.把对象的引用交给接收值，也就是引用交给 instance 

指令重排序的影响下会可能导致 步骤2与步骤3 颠倒，导致instance中的属性值都是默认值，从而导致很罕见的程序错误
> Reorder_test.java 那个小程序跑了一整下午还没遇见错误

# 5. `volatile`是如何保证不会指令重排序的


# 6. CAS 的 ABA 问题

### AtomicStampedReference
+ An AtomicStampedReference maintains an object reference
along with an integer "stamp", that can be updated atomically.

+  Atomically sets the value of both the reference and stamp
     to the given update values if the
     current reference is == to the expected reference 
   and the current stamp is equal to the expected stamp.


# 7. LongAdder 
### 使用了分段锁

# 8. `ReentrantLock`
+ ####  需要注意的是，【必须要】手动释放锁，手动调用 `lock()` 和 `unlock()` 方法。
+ #### 使用`synchronized`锁定的话如果遇到异常，JVM会自动释放锁，但是`ReentrantLock`必须手动释放锁，因此经常在`finally`中进行锁的释放
### 8.1 可重入性
+ 同一个线程执行一个方法的时候，可以调用另外同一个`ReentrantLock`对象锁定的方法（这么说好像不严谨）
```java
public class L28_ReentrantLock {
    ReentrantLock lock = new ReentrantLock();

    public void l1() {
        try {
            int i = 0;
            lock.lock();
            while (i < 10) {
                if (i == 5) l2();
            }
        }finally {
            /* 解锁一定写在这里 */
            lock.unlock();
        }
    }

    public void l2() {
        try {
            lock.lock();
        }finally {
            /* 解锁一定写在这里 */
            lock.unlock();
        }
    }
}
```
### 8.2 `tryLock()`
+ #### 使用`ReentrantLock`可以进行“尝试锁定”`tryLock()`，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
```java
lock.tryLock();
// 三秒之内拿不到锁就抛出异常
lock.tryLock(3, TimeUnit.SECONDS);
```
### 8.3 `lockInterruptibly()`
+ #### `lockInterruptibly()`方法，可以对线程`interrupt()`方法做出响应， 在一个线程等待锁的过程中，可以被打断

### 8.4 公平锁与非公平锁
+ #### 每次线程来争抢锁的时候，先判断等待队列有没有其他线程在wait，如果有把自己加到等待队列队尾，如果没有直接开始拿锁

# 9. LockSupport
### 9.1 park unpark 如何实现
淦! 竟然是native

# 10. AQS源码阅读
### 详情请见本人博客
[AQS ReentrantLock ReentrantReadWriteLock CountDownLatch源码阅读](https://blog.csdn.net/XueyinGuo/article/details/113785283)
  



# 14. Varhandle

+ 获取到普通属性变量地址之后进行原子操作
+ 比反射快



# 15. ThreadLocal
### 详情请见本人博客
[Java中的强软弱虚四种引用和ThreadLocal](https://blog.csdn.net/XueyinGuo/article/details/113797156)

# 16. 容器
### 请见本人博客
[Java List详解-从Vector到CopyOnWriteList](https://blog.csdn.net/XueyinGuo/article/details/113872708)

[Java Map详解-从HashMap到concurrentHashMap](https://blog.csdn.net/XueyinGuo/article/details/113872666)

[Java Queue详解-从普通Queue到ConcurrentQueue](https://blog.csdn.net/XueyinGuo/article/details/113872562)

