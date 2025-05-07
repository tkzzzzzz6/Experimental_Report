package com.tk.thread;

public class MyThread extends Thread {
    private String name;

    public MyThread(String name){
        this.name = name;
    }

    @Override
    public void run(){
        for(int i = 0;i<100;++i){
            System.out.println(name + "运行" + i);
        }
        super.run();
    }
}
