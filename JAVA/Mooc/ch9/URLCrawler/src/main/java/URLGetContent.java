import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.BorderLayout;

public class URLGetContent extends JFrame {
    private JTextField urlField = new JTextField("http://www.baidu.com");
    private JButton crawlBtn = new JButton("开始爬取");
    private JTextArea logArea = new JTextArea();
    private JEditorPane htmlPane = new JEditorPane();
    private JSpinner depthSpinner;
    private Set<String> visitedUrls = new HashSet<>();
    private volatile boolean stopCrawling = false; // 用于未来的停止功能

    public URLGetContent() {
        setTitle("增强型网络爬虫");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 顶部面板（URL输入和控制） ---
        JPanel controlPanel = new JPanel(new BorderLayout(5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlPanel.add(new JLabel("URL:"), BorderLayout.WEST);
        controlPanel.add(urlField, BorderLayout.CENTER);

        JPanel controlsEastPanel = new JPanel();
        controlsEastPanel.add(new JLabel("深度:"));
        SpinnerModel depthModel = new SpinnerNumberModel(1, 0, 10, 1); // 默认1，最小0，最大10
        depthSpinner = new JSpinner(depthModel);
        controlsEastPanel.add(depthSpinner);
        controlsEastPanel.add(crawlBtn);
        controlPanel.add(controlsEastPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.NORTH);

        // --- 中央面板（日志和HTML视图） ---
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        htmlPane.setEditable(false);
        htmlPane.setContentType("text/html"); // 为JEditorPane设置内容类型
        JScrollPane htmlScrollPane = new JScrollPane(htmlPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, htmlScrollPane, logScrollPane);
        splitPane.setResizeWeight(0.7); // HTML面板初始获得更多空间
        add(splitPane, BorderLayout.CENTER);

        // --- 事件监听器 ---
        crawlBtn.addActionListener(e -> {
            String startUrl = urlField.getText().trim();
            if (startUrl.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入URL。", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            logArea.setText(""); // 清除日志
            htmlPane.setText(""); // 清除HTML视图
            visitedUrls.clear();
            stopCrawling = false;
            int maxDepth = (Integer) depthSpinner.getValue();
            new Thread(() -> crawlPage(startUrl, 0, maxDepth)).start();
        });
    }

    private void crawlPage(String urlStr, int currentDepth, int maxDepth) {
        if (stopCrawling || currentDepth > maxDepth || visitedUrls.contains(urlStr)) {
            if (visitedUrls.contains(urlStr)) {
                appendLog("已访问: " + urlStr);
            }
            return;
        }
        visitedUrls.add(urlStr);
        appendLog("正在爬取（深度 " + currentDepth + "）: " + urlStr);

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000); // 5秒
            conn.setReadTimeout(10000);    // 10秒
            conn.setRequestProperty("User-Agent", "SimpleJavaCrawler/1.0"); // 做一个好的爬虫

            String contentType = conn.getContentType();
            String charset = "UTF-8"; // 默认字符集

            if (contentType != null) {
                Matcher m = Pattern.compile("charset=([\\w-]+)", Pattern.CASE_INSENSITIVE).matcher(contentType);
                if (m.find()) {
                    charset = m.group(1).toUpperCase();
                    appendLog("从HTTP头获取的字符集: " + charset);
                }
            }

            InputStream inputStream = conn.getInputStream();
            // 缓冲输入流以允许查看meta标签然后完整读取
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            
            // 如果是HTML，尝试从meta标签获取字符集
            if (contentType != null && contentType.toLowerCase().contains("text/html")) {
                bufferedInputStream.mark(2048); // 标记以便重置，允许查看2KB
                byte[] headBytes = new byte[2048];
                int bytesRead = bufferedInputStream.read(headBytes);
                bufferedInputStream.reset(); // 重要：重置流以进行完整读取
                
                if (bytesRead > 0) {
                    String headSnippet = new String(headBytes, 0, bytesRead, StandardCharsets.ISO_8859_1); // 使用ISO-8859-1读取以查找meta标签
                    String metaCharset = findCharsetInMeta(headSnippet);
                    if (metaCharset != null && !metaCharset.equalsIgnoreCase(charset)) {
                        charset = metaCharset.toUpperCase();
                        appendLog("由meta标签覆盖的字符集: " + charset);
                    }
                }
            }
            
            String htmlContent = readAll(bufferedInputStream, charset);
            bufferedInputStream.close(); // 关闭最外层流

            // 显示HTML（仅用于初始页面或当前聚焦的页面）
            // 为简单起见，我们将为每个页面更新。可以改进。
            final String finalHtmlContent = htmlContent;
            SwingUtilities.invokeLater(() -> {
                 // htmlPane.setText(finalHtmlContent); // 对于大页面或多次更新可能较慢
                 // 在JEditorPane中处理相对URL的更好方法：
                try {
                    HTMLDocument doc = (HTMLDocument) htmlPane.getEditorKit().createDefaultDocument();
                    doc.setBase(url); // 为此页面内的相对链接设置基础
                    htmlPane.setDocument(doc);
                    htmlPane.setText(finalHtmlContent);
                } catch (Exception ex) {
                    htmlPane.setText("显示HTML时出错: " + ex.getMessage());
                }
            });


            if (!contentType.toLowerCase().contains("text/html") && !htmlContent.toLowerCase().contains("<html")) {
                appendLog("内容似乎不是HTML: " + urlStr);
                return;
            }

            appendLog("成功下载: " + urlStr);

            Set<String> emails = extractEmails(htmlContent);
            if (!emails.isEmpty()) {
                appendLog("找到的电子邮件:");
                emails.forEach(email -> appendLog("  - " + email));
            }

            if (currentDepth < maxDepth) {
                appendLog("提取链接以进行进一步爬取（深度 " + (currentDepth + 1) + "）...");
                Set<String> links = extractLinks(htmlContent, urlStr);
                for (String link : links) {
                    if (stopCrawling) break;
                    crawlPage(link, currentDepth + 1, maxDepth);
                }
            }

        } catch (MalformedURLException e) {
            appendLog("格式错误的URL: " + urlStr + " - " + e.getMessage());
        } catch (IOException e) {
            appendLog("IO错误 " + urlStr + ": " + e.getMessage());
        } catch (Exception e) {
            appendLog("意外错误 " + urlStr + ": " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String findCharsetInMeta(String htmlSnippet) {
        // 用于 <meta charset="UTF-8"> 的正则表达式
        Matcher m = Pattern.compile("<meta\\s+charset\\s*=\\s*[\"']?([\\w-]+)[\"']?", Pattern.CASE_INSENSITIVE).matcher(htmlSnippet);
        if (m.find()) {
            return m.group(1);
        }
        // 用于 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 的正则表达式
        m = Pattern.compile("<meta\\s+http-equiv\\s*=\\s*[\"']?Content-Type[\"']?\\s+content\\s*=\\s*[\"']?[^;]+;\\s*charset=([\\w-]+)[\"']?", Pattern.CASE_INSENSITIVE).matcher(htmlSnippet);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength()); // 自动滚动
        });
    }

    private String readAll(InputStream stream, String charset) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            appendLog("警告：不支持的编码 '" + charset + "'。回退到 UTF-8。");
            // 如果指定的字符集无效，使用 UTF-8 作为后备方案，尽管流已经被包装。
            // 如果流已经被消费或字符集至关重要，这个特定的捕获可能会很棘手。
            // 更好的方法是如果可能的话重新打开流，或确保在此点之前字符集是有效的。
            // 目前，如果字符集错误，只是记录并继续处理可能乱码的数据。
            // 使用新的字符集重新读取需要从 conn 重新打开流。
            return ""; // 或者重新抛出/更优雅地处理
        }
    }

    private Set<String> extractEmails(String html) {
        Set<String> emails = new HashSet<>();
        Matcher m = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}", Pattern.CASE_INSENSITIVE).matcher(html);
        while (m.find()) {
            emails.add(m.group());
        }
        return emails;
    }

    private Set<String> extractLinks(String html, String baseUrlStr) {
        Set<String> links = new HashSet<>();
        // 更健壮的 href 正则表达式，处理单引号或双引号，并忽略 # 等片段
        Matcher m = Pattern.compile("href\\s*=\\s*[\"']([^\"'#\\s>]+)[\"']", Pattern.CASE_INSENSITIVE).matcher(html);
        try {
            URL base = new URL(baseUrlStr);
            while (m.find()) {
                String link = m.group(1).trim();
                if (link.isEmpty() || link.toLowerCase().startsWith("javascript:") || link.toLowerCase().startsWith("mailto:")) {
                    continue;
                }
                try {
                    URL resolvedUrl = new URL(base, link); // 相对于基础 URL 解析
                    // 标准化：移除片段并确保是 http/https
                    String fullLink = resolvedUrl.getProtocol() + "://" + resolvedUrl.getAuthority() + resolvedUrl.getPath();
                    if (resolvedUrl.getQuery() != null) {
                        fullLink += "?" + resolvedUrl.getQuery();
                    }
                    if ( (resolvedUrl.getProtocol().equalsIgnoreCase("http") || resolvedUrl.getProtocol().equalsIgnoreCase("https")) &&
                         !links.contains(fullLink) && 
                         !visitedUrls.contains(fullLink) // 可选：这里的小优化，主要检查在 crawlPage 中
                    ) {
                         links.add(fullLink);
                    }
                } catch (MalformedURLException ignored) {
                    // appendLog("跳过格式错误的链接: " + link);
                }
            }
        } catch (MalformedURLException e) {
            appendLog("创建链接提取的基础 URL 时出错: " + baseUrlStr);
        }
        return links;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new URLGetContent().setVisible(true));
    }
}