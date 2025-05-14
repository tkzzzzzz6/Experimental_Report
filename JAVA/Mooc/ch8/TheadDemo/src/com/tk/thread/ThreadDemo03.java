package com.tk.thread;

class RunnableDemo implements Runnable{
    public String name;
    public RunnableDemo(String name){
        this.name = name;
    }
    @Override
    public void run() {
        for(int i = 0;i<100;++i){
//            System.out.println(name + "运行" +i);
//            System.out.println("current running Thread is "+ Thread.currentThread().getName());
            try{
                Thread.sleep(100);
                System.out.println(name + "运行" +i);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

public class ThreadDemo03 {
    public static void main(String[] args) {
//        //使用继承Runnable接口的方式
//        RunnableDemo r2 = new RunnableDemo("B");
//        RunnableDemo r1 = new RunnableDemo("A");
//        //创建线程对象
//        Thread t1 = new Thread(r1);
//        Thread t2 = new Thread(r2);
//        //启动线程
//        t1.start();
//        t2.start();
        RunnableDemo t1 = new RunnableDemo("A");
        Thread t = new Thread(t1);
        t.start();
        for(int i = 0;i<100;++i){
            if (i>10){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("主线程"+ i);
        }
    }
}
