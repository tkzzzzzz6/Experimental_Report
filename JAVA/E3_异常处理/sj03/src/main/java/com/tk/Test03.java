package com.tk;

import java.io.IOException;

public class Test03 {
    public static void main(String[] args) throws IOException {
        int i = 1, j;
        try{
            System.out.println("Try:这是一个异常处理的例子：");
            j = i/0;
            throw new ArithmeticException();
        }catch(ArithmeticException e){
            System.out.println("Catch:"+e+";"+"\n"+"reason:"+e.getMessage());
        }finally{
            System.out.println("Finally:must go inside finally");
        }
    }
}