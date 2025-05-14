import java.io.*;

public class CleanWordFile {
    public static void main(String[] args) {
        try {
            // 配置文件路径
            String inputPath = "College_Grade4_Clean.txt";
            String outputPath = "College_Grade4_Clean.txt";
            
            // 读取和写入
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputPath), "GB2312"));
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputPath), "GB2312"));
            
            String line;
            int count = 0;
            
            while ((line = reader.readLine()) != null) {
                // 如果是空行，保留空行
                if (line.trim().isEmpty()) {
                    writer.newLine();
                    continue;
                }
                
                // 查找最后一个空格
                int lastSpaceIndex = line.lastIndexOf(' ');
                
                if (lastSpaceIndex > 0) {
                    // 只保留最后一个空格之前的内容
                    String cleanedLine = line.substring(0, lastSpaceIndex);
                    writer.write(cleanedLine);
                } else {
                    // 如果没有空格，原样保留
                    writer.write(line);
                }
                
                writer.newLine();
                count++;
            }
            
            reader.close();
            writer.close();
            
            System.out.println("处理完成! 共处理 " + count + " 行");
            System.out.println("清理后的文件已保存为: " + outputPath);
            
        } catch (Exception e) {
            System.out.println("出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}