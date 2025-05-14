import java.io.*;

public class Dump {
    public static void main(String[] args) {
        try{
            dump(new FileInputStream("aaa.dump"),new FileOutputStream("bbb.dump"));
        } catch (IOException e) {
            System.out.println("文件读取错误");
            e.printStackTrace();
        }
    }
}
