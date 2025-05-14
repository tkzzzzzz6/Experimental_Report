import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.BorderLayout; // Explicit import for clarity

public class URLGetContent extends JFrame {
    private JTextField urlField = new JTextField("http://www.baidu.com");
    private JButton crawlBtn = new JButton("Start Crawl");
    private JTextArea logArea = new JTextArea();
    private JEditorPane htmlPane = new JEditorPane();
    private JSpinner depthSpinner;
    private Set<String> visitedUrls = new HashSet<>();
    private volatile boolean stopCrawling = false; // For future stop functionality

    public URLGetContent() {
        setTitle("Enhanced Web Crawler");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Top Panel (URL Input & Controls) ---
        JPanel controlPanel = new JPanel(new BorderLayout(5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlPanel.add(new JLabel("URL:"), BorderLayout.WEST);
        controlPanel.add(urlField, BorderLayout.CENTER);

        JPanel controlsEastPanel = new JPanel();
        controlsEastPanel.add(new JLabel("Depth:"));
        SpinnerModel depthModel = new SpinnerNumberModel(1, 0, 10, 1); // Default 1, min 0, max 10
        depthSpinner = new JSpinner(depthModel);
        controlsEastPanel.add(depthSpinner);
        controlsEastPanel.add(crawlBtn);
        controlPanel.add(controlsEastPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.NORTH);

        // --- Center Panel (Log and HTML View) ---
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        htmlPane.setEditable(false);
        htmlPane.setContentType("text/html"); // Set content type for JEditorPane
        JScrollPane htmlScrollPane = new JScrollPane(htmlPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, htmlScrollPane, logScrollPane);
        splitPane.setResizeWeight(0.7); // HTML pane gets more space initially
        add(splitPane, BorderLayout.CENTER);

        // --- Event Listener ---
        crawlBtn.addActionListener(e -> {
            String startUrl = urlField.getText().trim();
            if (startUrl.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a URL.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            logArea.setText(""); // Clear log
            htmlPane.setText(""); // Clear HTML view
            visitedUrls.clear();
            stopCrawling = false;
            int maxDepth = (Integer) depthSpinner.getValue();
            new Thread(() -> crawlPage(startUrl, 0, maxDepth)).start();
        });
    }

    private void crawlPage(String urlStr, int currentDepth, int maxDepth) {
        if (stopCrawling || currentDepth > maxDepth || visitedUrls.contains(urlStr)) {
            if (visitedUrls.contains(urlStr)) {
                appendLog("Already visited: " + urlStr);
            }
            return;
        }
        visitedUrls.add(urlStr);
        appendLog("Crawling (Depth " + currentDepth + "): " + urlStr);

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000); // 5 seconds
            conn.setReadTimeout(10000);    // 10 seconds
            conn.setRequestProperty("User-Agent", "SimpleJavaCrawler/1.0"); // Be a good bot

            String contentType = conn.getContentType();
            String charset = "UTF-8"; // Default charset

            if (contentType != null) {
                Matcher m = Pattern.compile("charset=([\\w-]+)", Pattern.CASE_INSENSITIVE).matcher(contentType);
                if (m.find()) {
                    charset = m.group(1).toUpperCase();
                    appendLog("Charset from HTTP header: " + charset);
                }
            }

            InputStream inputStream = conn.getInputStream();
            // Buffer the input stream to allow peeking for meta tags and then full read
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            
            // Try to get charset from meta tags if it's HTML
            if (contentType != null && contentType.toLowerCase().contains("text/html")) {
                bufferedInputStream.mark(2048); // Mark for reset, allow peeking 2KB
                byte[] headBytes = new byte[2048];
                int bytesRead = bufferedInputStream.read(headBytes);
                bufferedInputStream.reset(); // Important: reset stream for full readAll
                
                if (bytesRead > 0) {
                    String headSnippet = new String(headBytes, 0, bytesRead, StandardCharsets.ISO_8859_1); // Read as ISO-8859-1 to find meta tags
                    String metaCharset = findCharsetInMeta(headSnippet);
                    if (metaCharset != null && !metaCharset.equalsIgnoreCase(charset)) {
                        charset = metaCharset.toUpperCase();
                        appendLog("Charset overridden by meta tag: " + charset);
                    }
                }
            }
            
            String htmlContent = readAll(bufferedInputStream, charset);
            bufferedInputStream.close(); // Close the outermost stream

            // Display HTML (only for the initial page or current page being focused)
            // For simplicity, we'll update for every page. Could be refined.
            final String finalHtmlContent = htmlContent;
            SwingUtilities.invokeLater(() -> {
                 // htmlPane.setText(finalHtmlContent); // This can be slow for large pages or many updates
                 // A better way to handle base for relative URLs in JEditorPane:
                try {
                    HTMLDocument doc = (HTMLDocument) htmlPane.getEditorKit().createDefaultDocument();
                    doc.setBase(url); // Set base for relative links within this page
                    htmlPane.setDocument(doc);
                    htmlPane.setText(finalHtmlContent);
                } catch (Exception ex) {
                    htmlPane.setText("Error displaying HTML: " + ex.getMessage());
                }
            });


            if (!contentType.toLowerCase().contains("text/html") && !htmlContent.toLowerCase().contains("<html")) {
                appendLog("Content does not appear to be HTML: " + urlStr);
                return;
            }

            appendLog("Successfully downloaded: " + urlStr);

            Set<String> emails = extractEmails(htmlContent);
            if (!emails.isEmpty()) {
                appendLog("Found Emails:");
                emails.forEach(email -> appendLog("  - " + email));
            }

            if (currentDepth < maxDepth) {
                appendLog("Extracting links for further crawling (depth " + (currentDepth + 1) + ")...");
                Set<String> links = extractLinks(htmlContent, urlStr);
                for (String link : links) {
                    if (stopCrawling) break;
                    crawlPage(link, currentDepth + 1, maxDepth);
                }
            }

        } catch (MalformedURLException e) {
            appendLog("Malformed URL: " + urlStr + " - " + e.getMessage());
        } catch (IOException e) {
            appendLog("IO Error for " + urlStr + ": " + e.getMessage());
        } catch (Exception e) {
            appendLog("Unexpected error for " + urlStr + ": " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String findCharsetInMeta(String htmlSnippet) {
        // Regex for <meta charset="UTF-8">
        Matcher m = Pattern.compile("<meta\\s+charset\\s*=\\s*[\"']?([\\w-]+)[\"']?", Pattern.CASE_INSENSITIVE).matcher(htmlSnippet);
        if (m.find()) {
            return m.group(1);
        }
        // Regex for <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        m = Pattern.compile("<meta\\s+http-equiv\\s*=\\s*[\"']?Content-Type[\"']?\\s+content\\s*=\\s*[\"']?[^;]+;\\s*charset=([\\w-]+)[\"']?", Pattern.CASE_INSENSITIVE).matcher(htmlSnippet);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    private void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength()); // Auto-scroll
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
            appendLog("Warning: Unsupported encoding '" + charset + "'. Falling back to UTF-8.");
            // Fallback read with UTF-8 if specified charset is bad, though stream is already wrapped.
            // This specific catch might be tricky here if the stream is already consumed or charset was vital.
            // A better approach would be to re-open stream if possible, or ensure charset is valid before this point.
            // For now, just log and proceed with potentially garbled data if charset was wrong.
            // Re-reading with a new charset would require re-opening the stream from conn.
            return ""; // Or re-throw / handle more gracefully
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
        // More robust regex for href, handles single or double quotes, and ignores fragments like #
        Matcher m = Pattern.compile("href\\s*=\\s*[\"']([^\"'#\\s>]+)[\"']", Pattern.CASE_INSENSITIVE).matcher(html);
        try {
            URL base = new URL(baseUrlStr);
            while (m.find()) {
                String link = m.group(1).trim();
                if (link.isEmpty() || link.toLowerCase().startsWith("javascript:") || link.toLowerCase().startsWith("mailto:")) {
                    continue;
                }
                try {
                    URL resolvedUrl = new URL(base, link); // Resolve relative to base
                    // Normalize: remove fragment and ensure it's http/https
                    String fullLink = resolvedUrl.getProtocol() + "://" + resolvedUrl.getAuthority() + resolvedUrl.getPath();
                    if (resolvedUrl.getQuery() != null) {
                        fullLink += "?" + resolvedUrl.getQuery();
                    }
                    if ( (resolvedUrl.getProtocol().equalsIgnoreCase("http") || resolvedUrl.getProtocol().equalsIgnoreCase("https")) &&
                         !links.contains(fullLink) && 
                         !visitedUrls.contains(fullLink) // Optional: minor optimization here, major check is in crawlPage
                    ) {
                         links.add(fullLink);
                    }
                } catch (MalformedURLException ignored) {
                    // appendLog("Skipping malformed link: " + link);
                }
            }
        } catch (MalformedURLException e) {
            appendLog("Error creating base URL for link extraction: " + baseUrlStr);
        }
        return links;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new URLGetContent().setVisible(true));
    }
}