package com.tk.thread;
import java.net.URL;
import java.io.*;

class Downloader
{
    public static void main(String[] args)
            throws Exception
    {
        final URL[] urls = {
                new URL("https://www.pku.edu.cn"),
                new URL("https://www.baidu.com"),
                new URL("https://www.sina.com.cn"),
                new URL("http://www.dstang.com")
        };
        final String[] files = {
                "pku.htm",
                "baidu.htm",
                "sina.htm",
                "study.htm",
        };

        for(int idx=0; idx<urls.length; idx++){
            try{
                System.out.println( urls[idx] );
                download( urls[idx], files[idx]);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    static void download( URL url, String file)
            throws IOException
    {
        try(InputStream input = url.openStream();
            OutputStream output = new FileOutputStream(file))
        {
            byte[] data = new byte[1024];
            int length;
            while((length=input.read(data))!=-1){
                output.write(data,0,length);
            }
        }
    }
}


