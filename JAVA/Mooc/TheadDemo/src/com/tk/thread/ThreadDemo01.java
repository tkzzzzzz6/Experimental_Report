package com.tk.thread;

public class ThreadDemo01 {
    public static void main(String[] args) {
//        //创建线程对象
//        MyThread t1 = new MyThread("A");
//        MyThread t2 = new MyThread("B");
//        //启动线程,线程的启动是调用run()方法,而不是start()方法
//        t1.start();
//        t2.start();

        //使用继承Runnable接口的方式
        MyRunnable r1 = new MyRunnable("A");
        MyRunnable r2 = new MyRunnable("B");
        //创建线程对象
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        //启动线程
        t1.start();
        t2.start();
    }
}
