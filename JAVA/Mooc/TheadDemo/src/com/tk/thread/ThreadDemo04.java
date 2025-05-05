package com.tk.thread;

class RunnableDemo1 implements Runnable {
    private int ticket = 5;

    public void run() {
        for (int i = 0; i < 10; ++i) {
//            synchronized (this) {
//                if (ticket > 0) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("The ticket leave:" + ticket--);
//                }
//            }
//        }
            tell();
        }
    }
    public synchronized void tell(){
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The ticket leave:" + ticket--);
        }
    }
}
public class ThreadDemo04{
    public static void main(String[] args) {
        RunnableDemo1 r = new RunnableDemo1();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);
        t1.start();
        t2.start();
        t3.start();
    }
}
