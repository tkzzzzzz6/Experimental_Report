package com.tk.thread;

public class MyRunnable implements Runnable{
    public String name;
    public MyRunnable(String name){
        this.name = name;
    }
    @Override
    public void run() {
        for(int i = 0;i<100;++i){
            System.out.println(name + "运行" +i);
        }
    }
}
