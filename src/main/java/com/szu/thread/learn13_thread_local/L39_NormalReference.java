package com.szu.thread.learn13_thread_local;

import java.io.IOException;

public class L39_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        System.out.println(m);
        m = null;
        System.gc();
        System.out.println(m);
        System.in.read();
    }
}
