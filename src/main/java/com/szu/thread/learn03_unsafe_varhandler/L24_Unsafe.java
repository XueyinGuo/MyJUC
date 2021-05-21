package com.szu.thread.learn03_unsafe_varhandler;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *      通过unsafe直接获取到变量的地址，直接操作他的地址改变这个变量
 *
 * @Date 2021/2/7 18:01
 */

import sun.misc.Unsafe;

public class L24_Unsafe {
    static class M{
        private M(){}

        int i = 0;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        M m = (M) unsafe.allocateInstance(M.class);
        m.i = 100;
        System.out.println(m.i);
    }

}
