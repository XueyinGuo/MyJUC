package com.szu.thread;

public class L11_Dirty_Read {

    String name;
    double account;

    public L11_Dirty_Read() {
        this.name = name;
    }
    /*
    * 设置余额方法先睡十秒，再设置账户余额
    * */
    public synchronized void set(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setAccount(100.00);
    }


    public double getAccount() {
        return account;
    }

    public /* synchronized */ void setAccount(double account) {
        this.account = account;
    }

    public static void main(String[] args) {
        L11_Dirty_Read l = new L11_Dirty_Read();
        /* 设置余额的线程睡两秒 */
        new Thread(l::set, "setAccountThread").start();
        /* 产生脏读，直接读取老数据 */
        /* 加上 synchronized 防止脏读 */
        System.out.println( l.getAccount() );
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /* 最终获取到最新数据 */
        System.out.println( l.getAccount() );
    }
}
