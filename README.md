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

# 10. AQS源码阅读
带图版本请见 https://blog.csdn.net/XueyinGuo/article/details/113785283
### 

## 10.1 AQS简介

**`AbstractQueuedSynchronizer`**简称AQS，是实现JUC包中各种锁的关键，此类是一个模板类，具体的`ReentrantLock`、`CountDownLatch`、`ReadWriteLock`等等都是自己去实现里边变量的使用规则。



各种类型的锁都有自己的锁类型信息

+ 比如`ReadWriteLock`就肯定会有当前的锁状态是读锁模式还是写锁模式

  ```java
  static final Node SHARED = new Node(); // 当前锁状态是 共享锁（读锁）模式
  static final Node EXCLUSIVE = null;    // 当前锁状态是 排它锁（写锁）模式
  ```

这些所有的信息加上线程本身被封装成一个内部类对象 **Node**

每个Node都有指向前驱节点和后继节点的指针，每个节点将有关线程的某些控制信息保存在其节点的前身中。

> 意思是说前一个节点变成什么样的时候，我自己变成什么样。具体例子就是：当时一个==**公平锁**==状态的时候，每个节点都在等排号，当前一个节点时头结点的时候，而且头结点已经准备释放锁了，我需要把我自己节点持有的线程notify一下或者unpark一下。



前驱根据自己状态发出信号，后继节点做出相应的操作。

当锁是==**非公平锁**==的时候，线程们会尝试抢夺锁资源，即使一个节点是在队列中的第一个也并不能保证成功抢夺到锁。







## 10.2 AQS内部类详解

### 10.2.1 Node

+ 用作等待队列

```java
static final class Node {
    static final Node SHARED = new Node(); // 当前锁状态是 共享锁（读锁）模式
    static final Node EXCLUSIVE = null;    // 当前锁状态是 排它锁（写锁）模式
	
    static final int CANCELLED =  1;		// 表示线程被取消（执行过interrupt()方法或者 timeout ）
    
    static final int SIGNAL    = -1;		// 等待队列中 此线程后边的节点需要被 notify()、或者 LockSupport.unpark()【公平锁？】
    
    static final int CONDITION = -2;		// 此线程在一个 condition 等待队列中
    
    static final int PROPAGATE = -3;   		// 我理解是相当于执行 notifyAll()方法的，但是只有头结点是这个状态，当头结点这样时候所有队列中的节											// 点都会去在头结点释放锁之后抢锁 【非公平锁？】
    										// 或者当前线程处于 【condition】 等待队列中的时候，当condition队列的头结点被叫醒的时候，传播给												//当前所有的等待队列中的节点都被唤醒去争抢锁资源
	
    /* 存储一个上边的某一个值，表示当前节点的状态 */
    volatile int waitStatus;

    // 当前节点的前一个节点，用来监视前一个节点的状态（如果前一个节点被取消，自己连接到前一个节点的前一个节点）
    volatile Node prev;

    // 当前节点的后一个节点，当这个节点当做头结点释放锁的时候，通知下一个节点
    volatile Node next;

    // 节点中保存的 线程
    volatile Thread thread;

    // 指向一个 condition等待队列
    Node nextWaiter;
}
```

### 10.2.2 ConditionObject

+ ##### `new ReentrantLock().newCondition()`创建的对象

```java
public class ConditionObject implements Condition, java.io.Serializable {
        // condition等待队列的第一个节点
        private transient Node firstWaiter;
       	// condition等待队列的最后一个节点
        private transient Node lastWaiter;
}
```





## 10.3 AQS类变量详解

```java
// 表示锁当前的状态，初始为0， ReentrantLock.lock()之后数字加1，表示已经上锁
private volatile int state;
// 等待队列的头结点（head节点初始化的时候，waitStatus会被赋值为0）
private transient volatile Node head;
// 等待队列的尾巴节点
private transient volatile Node tail;

// 通过 VarHander 直接获取上边三个变量的地址，直接使用变量地址来进行CAS操作
private static final VarHandle STATE;
private static final VarHandle HEAD;
private static final VarHandle TAIL;
static {
    try {
         /* handle 绑定到一个了个类的属性上，通过位于类的类型，变量的名字 和 变量本身的类型 */
        MethodHandles.Lookup l = MethodHandles.lookup();
        STATE = l.findVarHandle(AbstractQueuedSynchronizer.class, "state", int.class);
        HEAD = l.findVarHandle(AbstractQueuedSynchronizer.class, "head", Node.class);
        TAIL = l.findVarHandle(AbstractQueuedSynchronizer.class, "tail", Node.class);
    } catch (ReflectiveOperationException e) {
        throw new ExceptionInInitializerError(e);
    }
}
```



## 10.4 插入到AQS队列

插入到AQS队列中只需要对当前的tail节点执行一次**CAS操作**

`prev`主要用于处理线程取消（线程超时或者被打断）的情况。如果取消某个节点，则其后继节点（通常）会重新链接到未取消的前任节点。

加入队列就是原子操作更新`tail`节点的`next`对象





# 11`ReentrantLock`与`AQS`



## 11.1 `lock()`

### 获得锁

CAS更改`state`的值，如果当前为0，`线程1`给他加到了1，表示`线程1`抢到了这把锁，其余线程进入等待队列，CAS更新`tail`节点的`next`指针

### 可重入

可重入实现为给`state`属性值往上加1

### 公平性

只有`state`属性再次为0的时候表示`线程1`彻底释放了锁资源，就可以通过上述过程来选择

+ **非公平**：广播消息激活所有线程争抢锁资源

+ **公平**：只把`head`节点的`next`指向的节点激活

## 11.2 `trylock()`

尝试获取锁，获取不到锁或者过了超时时间就把自己设置为取消状态，并且更新前后节点的指向关系

`curNode.next.pre = curNode.pre`

`curNode.pre.next = curNode.next`

## 11.3 `lockInterruptibly()`

节点中线程可以被打断，打断之后的线程也设置为取消状态，并且更新节点的指向关系





# 12. `CountDownLatch`与`AQS`

**state为门栓，当门栓为0时，表示线程不用被阻塞可以继续运行。**




## 12.1 `await()`

+ 获取state是否为0，为0则可以直接继续执行，不用阻塞当前线程

+ 如果获取到当前门栓值大于0，阻塞当前线程

    + 创建一个共享锁模式的Node，用CAS操作添加到等待队列队尾

    + 调用`LockSupport.park()`阻塞线程
    + 更新等待队列的头结点为刚刚创建的节点，并把头结点的`waitStatus`设置为广播模式

## 12.2 `countDown()`

+ CAS操作更新`state`的值，因为值大于0导致其他线程阻塞，所以在每次调用 `state--`

+ 当最后把`state`更新为0的时候，`unparkSuccessor`，解除头结点封印，又因为`await()`时候头结点的模式为广播模式，所以一旦头结点执行，所有的等待队列中的线程均可以激活





# 13. `ReentrantReadWriteLock`与AQS


## 13.1 `readLock.lock()`

### 获取state的值,查看有无排它锁

#### 有排它锁

+ 创建一个节点用CAS操作添加到等待队列末尾
+ 调用`LockSupport.park()`阻塞线程
+ 更新等待队列的头结点为刚刚创建的节点，并把头结点的`waitStatus`设置为广播模式

#### 无排它锁

+ 共享锁数量+1，继续执行代码

## 13.2 `readLock.unlock()`

+ 普通读线程执行的之后，把state数量减-1

+ 最后一个读线程退出的时候，激活等待队列中的线程（此时队列中全是写线程，公平读写锁的话就激活第一个，非公平就激活全部）

## 13.3 `writeLock.lock()`

#### 没有读线程 + 无读线程

`setExclusiveOwnerThread`为自己

#### 有其他读线程

把自己加入到等待队列中，并且锁模式设置为排它锁



## 13.4 `writeLock.unlock()`

`setExclusiveOwnerThread`为`null`，更新`state`数值





# 14. Varhandle

+ 获取到普通属性变量地址之后进行原子操作
+ 比反射快，直接操纵二进制码



