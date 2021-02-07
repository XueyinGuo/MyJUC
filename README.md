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

# 5. `volatile`是如何保证不会指令重排序的


# 6. CAS 的 ABA 问题

### AtomicStampedReference
+ An AtomicStampedReference maintains an object reference
along with an integer "stamp", that can be updated atomically.

+  Atomically sets the value of both the reference and stamp
     to the given update values if the
     current reference is == to the expected reference 
   and the current stamp is equal to the expected stamp.
