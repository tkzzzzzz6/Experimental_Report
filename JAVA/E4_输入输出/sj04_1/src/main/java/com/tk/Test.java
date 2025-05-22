package com.tk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Test {
    public static void main(String[] args) {
        String content_text = "./content.txt"; // 注意路径为项目的相对路径
        try(
            FileWriter fw = new FileWriter(content_text);
            BufferedWriter bw = new BufferedWriter(fw);
        ) {
            //这里使用的是Scanner,并根据Scanner的hasNext()方法判断是否还有输入
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入内容：");
            while(sc.hasNext()){
                String input_str = sc.nextLine();
                bw.write(input_str);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("文件写入失败");
            e.printStackTrace();
        }
    }
}
