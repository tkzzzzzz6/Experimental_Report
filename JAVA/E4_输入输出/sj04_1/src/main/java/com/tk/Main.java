package com.tk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        String content_text = "./content.txt"; // 注意路径为项目的相对路径
        try(
            FileWriter fw = new FileWriter(content_text);
            BufferedWriter bw = new BufferedWriter(fw);
        ) {
            //这里使用的硬编码的方法
            bw.write("This is test");
            bw.newLine();
            bw.write("这是一个写入测试!");
        } catch (IOException e) {
            System.out.println("文件写入失败");
            e.printStackTrace();
        }
    }
}
