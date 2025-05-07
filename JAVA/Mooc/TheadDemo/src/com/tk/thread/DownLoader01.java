package com.tk.thread;
import java.net.URL;
import java.io.*;
import java.util.concurrent.*;
import java.util.Date;

class DownloadTask implements Runnable {
    private URL url;
    private String file;

    public DownloadTask(URL url, String file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 开始下载: " + url);
            download(url, file);
            System.out.println(Thread.currentThread().getName() + " 完成下载: " + file);
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getName() + " 下载失败: " + url);
            ex.printStackTrace();
        }
    }

    static void download(URL url, String file) throws IOException {
        try (InputStream input = url.openStream();
             OutputStream output = new FileOutputStream(file)) {
            byte[] data = new byte[1024];
            int length;
            while ((length = input.read(data)) != -1) {
                output.write(data, 0, length);
            }
        }
    }
}

class Downloader01 {
    public static void main(String[] args) throws Exception {
        // 修正网址协议（http 而非 https）
        final URL[] urls = {
                new URL("https://www.pku.edu.cn"),
                new URL("https://www.baidu.com"),
                new URL("https://www.sina.com.cn"),
                new URL("http://www.dstang.com") // 确保使用 http 协议
        };

        final String[] files = {
                "pku.htm",
                "baidu.htm",
                "sina.htm",
                "study.htm"
        };

        // 记录开始时间
        long startTime = new Date().getTime();
        System.out.println("开始下载任务，当前时间: " + startTime + " 毫秒");

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(urls.length);

        // 创建CountDownLatch来等待所有下载任务完成
        CountDownLatch latch = new CountDownLatch(urls.length);

        // 提交下载任务到线程池
        for (int idx = 0; idx < urls.length; idx++) {
            final int i = idx;
            executor.submit(() -> {
                try {
                    new DownloadTask(urls[i], files[i]).run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // 无论成功失败，都减少计数
                }
            });
        }

        // 等待所有任务完成
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭线程池
        executor.shutdown();

        // 记录结束时间并计算总用时
        long endTime = new Date().getTime();
        long duration = endTime - startTime;

        System.out.println("所有下载任务已完成!");
        System.out.println("开始时间: " + startTime + " 毫秒");
        System.out.println("结束时间: " + endTime + " 毫秒");
        System.out.println("总耗时: " + duration + " 毫秒");
    }
}