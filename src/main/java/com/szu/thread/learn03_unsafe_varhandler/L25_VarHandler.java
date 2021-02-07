package com.szu.thread.learn03_unsafe_varhandler;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *          普通变量可以通过 VarHandle 的方式直接获得这个变量的内存地址，
 *          通过这块地址直接进行CAS操作
 *
 * @Date 2021/2/7 18:06
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class L25_VarHandler {

    int x = 10;

    private static VarHandle handle;

    static {
        try {
            /* handle 绑定到一个了个类的属性上，通过位于类的类型，变量的名字 和 变量本身的类型 */
            handle = MethodHandles.lookup().findVarHandle(L25_VarHandler.class, "x", int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        L25_VarHandler l = new L25_VarHandler();
        /* 经过绑定之后直接找到 new 出来的对象的这个变量的地址 */
        System.out.println(handle.get(l));
        handle.set(l, 9);
        /* 通过地址空间进行CAS操作 */
        handle.compareAndSet(l, 9, 10000);
        System.out.println(handle.get(l));
    }

}
