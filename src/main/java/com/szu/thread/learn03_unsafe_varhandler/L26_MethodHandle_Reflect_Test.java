package com.szu.thread.learn03_unsafe_varhandler;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/6/24 22:19
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class L26_MethodHandle_Reflect_Test {

    public static void main(String[] args) throws Throwable {
        int times = 10000000;
        long start = System.currentTimeMillis();
        MethodType methodType = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle substring = MethodHandles.lookup().findVirtual(String.class, "substring", methodType);
        for (int i = 0; i < times; i++) {
//            String handle = (String) substring.invokeWithArguments("hello", 0, 5);  /* 1325 */
            String handle = (String) substring.invoke("hello", 0, 5);             /* JDK 8：56ms, JDK11:47ms */
//            System.out.println(handle);
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        Method reflectMethod = String.class.getMethod("substring", int.class, int.class);
        for (int i = 0; i < times; i++) {

            String reflect = (String) reflectMethod.invoke("Hello", 0, 5);      /* JDK8: 24ms, JDK11: 33ms */
//            System.out.println(reflect);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}

