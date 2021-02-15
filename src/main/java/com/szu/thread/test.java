package com.szu.thread;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/14 20:53
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class test {

    public static void main(String[] args) {
        Map<Integer, Large> test = new ConcurrentSkipListMap<>();
        int i = 0;
        while(i < 50){
            test.put(i ,new Large());
            i++;
        }
    }
}

class Large{
    byte[] obe = new byte[1024*1024*5];
}
