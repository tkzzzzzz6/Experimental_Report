import java.net.URL;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.nio.charset.*;

/**
 * URL爬虫类 - 用于爬取网页内容并提取其中的URL链接
 */
class URLCrawler 
{
    /**
     * 主方法 - 启动URL爬虫程序
     * @param args 命令行参数
     * @throws Exception 可能抛出的异常
     */
    public static void main(String[] args)
        throws Exception
    {
        // 创建线程安全的URL队列
        ConcurrentLinkedQueue<String> urls = 
            new ConcurrentLinkedQueue<>();
        // 添加初始URL
        urls.add( "http://www.dstang.com" );
        int cnt=0;
        // 循环处理URL队列
        while(!urls.isEmpty()){
            String url = urls.poll();
            System.out.println(url);
            // 为每个URL创建新线程进行处理
            new Thread( ()-> {
                try{                    
                    // 下载URL内容
                    String content = download(
                        new URL(url), "gb2312");
                    // 解析内容中的URL
                    List<String> moreUrl = parse( content );
                    // 将新发现的URL添加到队列
                    urls.addAll(moreUrl);
                    //System.out.println(moreUrl);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }).start();
            
            // 限制爬取数量
            if(cnt++>5) break;
            // 线程休眠4秒，避免请求过于频繁
            try{ Thread.sleep(4000); }catch(InterruptedException ex){}
        }
    }

    /**
     * 解析HTML内容中的URL链接
     * @param text HTML文本内容
     * @return 提取出的URL列表
     */
    static List<String> parse(String text) {
        // 定义匹配href属性的正则表达式
        String patternString = 
            "\\s*href\\s*=\\s*(\"([^\"]*\")|(\'[^\']*\')|([^\'\">\\s]+))\\s*"; 
        Pattern pattern = Pattern.compile(patternString, 
            Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
        Matcher matcher = pattern.matcher( text );
        List<String> list = new ArrayList<>();
        // 查找所有匹配的URL
        while (matcher.find()) {
            String href = matcher.group(1);
            // 清理URL中的引号
            href = href.replaceAll("\'","").replaceAll("\"","");
            // 只保留http开头的URL
            if(href.startsWith("http:") )
                list.add(href); 
        }
        return list;
    }

    /**
     * 下载URL内容
     * @param url 要下载的URL
     * @param charset 字符编码
     * @return 下载的内容字符串
     * @throws Exception 可能抛出的异常
     */
    static String download( URL url, String charset)
        throws Exception
    {
        try(InputStream input 
                = url.openStream();
            ByteArrayOutputStream output 
                = new ByteArrayOutputStream())
        {
            // 读取URL内容到字节数组
            byte[] data = new byte[1024];
            int length;
            while((length=input.read(data))!=-1){
                output.write(data,0,length);
            }
            // 将字节数组转换为指定编码的字符串
            byte[] content = output.toByteArray();
            return new String(content, Charset.forName(charset));
        }
    }
}
