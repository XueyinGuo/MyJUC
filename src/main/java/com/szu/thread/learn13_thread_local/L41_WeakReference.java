/**
 * 弱引用遭到gc就会回收
 *
 */
package com.szu.thread.learn13_thread_local;

import java.lang.ref.WeakReference;

public class L41_WeakReference {
    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());
        System.out.println(m);

        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();

    }
}

