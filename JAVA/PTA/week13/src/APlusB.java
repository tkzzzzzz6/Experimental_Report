import java.io.*;

public class APlusB {
  public static void main(String[] args) {
    try {
      // 读取父目录下的a.txt和b.txt
      BufferedReader readerA = new BufferedReader(new FileReader("../a.txt"));
      BufferedReader readerB = new BufferedReader(new FileReader("../b.txt"));
      int a = Integer.parseInt(readerA.readLine().trim());
      int b = Integer.parseInt(readerB.readLine().trim());
      readerA.close();
      readerB.close();

      int sum = a + b;

      // 写入父目录下的c.txt
      BufferedWriter writer = new BufferedWriter(new FileWriter("../c.txt"));
      writer.write(String.valueOf(sum));
      writer.close();
    } catch (IOException e) {
      System.out.println("Error reading or writing file");
      e.printStackTrace();
    }
  }
}
