package com.sicau;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Week week = new Week();
        
        String input_str = null;
        String output_str = null;
        boolean invalid = true; // Changed to true to enter the loop
        int index = -1;
        System.out.println("开始执行:请输入1-7");
        
        while(invalid){
            input_str = sc.nextLine();
            try{
                index = Integer.parseInt(input_str) - 1;
                if(index < 0 || index > 6){
                    System.out.println("解析成功:输入的数字不在1-7之间");
                } else {                
                    invalid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("解析失败:只能输入数字1-7");
            }
        }
        sc.close(); // Added scanner close
        output_str = week.getDays(index);
        System.out.println(output_str);
    }
}