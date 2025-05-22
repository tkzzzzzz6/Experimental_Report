package test4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class Main {
    public static void main(String[] args) {
        String content_text = "./content.txt";
        try(
            FileWriter fw = new FileWriter(content_text); //注意文件的相对路径的问题
            BufferedWriter bw = new BufferedWriter(fw); //执行完以后resource 就关闭
        ) {
            for (int i = 0; i < 3; i++) {
                bw.write("这是第" + (i + 1) + "行");
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("文件写入失败");
            e.printStackTrace();
        }

        try(
            BufferedReader br = new BufferedReader(new FileReader(content_text));
            BufferedWriter bw_copy = new BufferedWriter(new FileWriter("./content_copy.txt"));
        ) {
            String input_str = "";
            while ((input_str = br.readLine()) != null) {
                bw_copy.write(input_str);
            bw_copy.newLine();
            }
        }catch(IOException e){
            System.out.println("文件读取失败");
            e.printStackTrace();
        }
        
    }
}