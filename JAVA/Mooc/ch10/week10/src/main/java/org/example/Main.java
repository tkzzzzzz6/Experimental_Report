package org.example;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.io.File;

public class Main {
    // 定义配色方案
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255); // 淡蓝色背景
    private static final Color TITLE_COLOR = new Color(70, 130, 180);       // 钢蓝色标题
    private static final Color LABEL_COLOR = new Color(47, 79, 79);         // 深灰色标签文字
    private static final Color BUTTON_COLOR = new Color(100, 149, 237);     // 矢车菊蓝按钮
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;             // 白色按钮文字
    private static final Color RESULT_BG_COLOR = new Color(240, 255, 240);  // 淡绿色结果背景
    private static final String IMAGE_PATH = "src/main/resources/background.png"; // 图片路径

    public static void main(String[] args) {
        // 创建主窗体
        Frame frame = new Frame("体重指数计算器");
        frame.setLayout(new BorderLayout(10, 10)); // 使用边界布局，设置组件间距
        frame.setSize(450, 450);
        frame.setBackground(BACKGROUND_COLOR);
        
        // 设置居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 450) / 2;
        int y = (screenSize.height - 450) / 2;
        frame.setLocation(x, y);

        // 设置字体
        Font titleFont = new Font("微软雅黑", Font.BOLD, 18);
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font inputFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 16);
        Font resultFont = new Font("微软雅黑", Font.BOLD, 14);

        // 创建顶部标题面板
        Panel titlePanel = new Panel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setLayout(new BorderLayout());
        
        Label titleLabel = new Label("BMI体重指数计算器", Label.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(TITLE_COLOR);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        Canvas divider1 = new Canvas();
        divider1.setBackground(TITLE_COLOR);
        divider1.setPreferredSize(new Dimension(frame.getWidth(), 2));
        titlePanel.add(divider1, BorderLayout.SOUTH);
        
        frame.add(titlePanel, BorderLayout.NORTH);

        // 创建中央输入面板
        Panel centerPanel = new Panel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        Panel inputPanel = new Panel();
        inputPanel.setLayout(new GridLayout(4, 1, 5, 10)); // 4行1列的网格布局，间距15
        inputPanel.setBackground(BACKGROUND_COLOR);
        
        // 性别选择面板
        Panel genderPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 25, 10));
        genderPanel.setBackground(BACKGROUND_COLOR);
        
        Label genderLabel = new Label("性别:");
        genderLabel.setFont(labelFont);
        genderLabel.setForeground(LABEL_COLOR);
        
        CheckboxGroup genderGroup = new CheckboxGroup();
        Checkbox maleCheckbox = new Checkbox("男", genderGroup, true);
        maleCheckbox.setFont(inputFont);
        maleCheckbox.setForeground(LABEL_COLOR);
        maleCheckbox.setBackground(BACKGROUND_COLOR);
        
        Checkbox femaleCheckbox = new Checkbox("女", genderGroup, false);
        femaleCheckbox.setFont(inputFont);
        femaleCheckbox.setForeground(LABEL_COLOR);
        femaleCheckbox.setBackground(BACKGROUND_COLOR);
        
        genderPanel.add(genderLabel);
        genderPanel.add(maleCheckbox);
        genderPanel.add(femaleCheckbox);
        
        // 身高面板
        Panel heightPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 25, 5));
        heightPanel.setBackground(BACKGROUND_COLOR);
        
        Label heightLabel = new Label("身高(cm):");
        heightLabel.setFont(labelFont);
        heightLabel.setForeground(LABEL_COLOR);
        
        TextField heightField = new TextField(15);
        heightField.setFont(inputFont);
        
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        
        // 体重面板
        Panel weightPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 25, 5));
        weightPanel.setBackground(BACKGROUND_COLOR);
        
        Label weightLabel = new Label("体重(kg):");
        weightLabel.setFont(labelFont);
        weightLabel.setForeground(LABEL_COLOR);
        
        TextField weightField = new TextField(15);
        weightField.setFont(inputFont);
        
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        
        // 按钮面板
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        Button calculateButton = new Button("计算BMI");
        calculateButton.setFont(buttonFont);
        calculateButton.setBackground(BUTTON_COLOR);
        calculateButton.setForeground(BUTTON_TEXT_COLOR);
        calculateButton.setPreferredSize(new Dimension(140, 40));
        
        buttonPanel.add(calculateButton);
        
        // 将各个子面板添加到输入面板
        inputPanel.add(genderPanel);
        inputPanel.add(heightPanel);
        inputPanel.add(weightPanel);
        inputPanel.add(buttonPanel);
        
        // 添加输入面板到中央面板
        centerPanel.add(inputPanel, BorderLayout.CENTER);
        
        // 创建图片面板并放置在右下角
        ImagePanel imagePanel = new ImagePanel(IMAGE_PATH);
        imagePanel.setPreferredSize(new Dimension(150, 150));
        
        // 创建一个面板用于定位图片到右下角
        Panel rightCornerPanel = new Panel(new BorderLayout());
        rightCornerPanel.setBackground(BACKGROUND_COLOR);
        rightCornerPanel.add(imagePanel, BorderLayout.SOUTH);
        
        centerPanel.add(rightCornerPanel, BorderLayout.EAST);
        
        // 添加中央面板到主框架
        frame.add(centerPanel, BorderLayout.CENTER);
        
        // 创建底部结果面板
        Panel resultOuterPanel = new Panel(new BorderLayout());
        resultOuterPanel.setBackground(BACKGROUND_COLOR);
        
        Canvas divider2 = new Canvas();
        divider2.setBackground(TITLE_COLOR);
        divider2.setPreferredSize(new Dimension(frame.getWidth(), 2));
        resultOuterPanel.add(divider2, BorderLayout.NORTH);
        
        Panel resultPanel = new Panel(new GridLayout(2, 1, 5, 10));
        resultPanel.setBackground(RESULT_BG_COLOR);
        resultPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));
        
        // BMI结果面板
        Panel bmiResultPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        bmiResultPanel.setBackground(RESULT_BG_COLOR);
        
        Label bmiResultLabel = new Label("BMI结果:");
        bmiResultLabel.setFont(labelFont);
        bmiResultLabel.setForeground(LABEL_COLOR);
        
        TextField bmiResultField = new TextField(15);
        bmiResultField.setEditable(false);
        bmiResultField.setFont(resultFont);
        
        bmiResultPanel.add(bmiResultLabel);
        bmiResultPanel.add(bmiResultField);
        
        // 状态结果面板
        Panel statusPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        statusPanel.setBackground(RESULT_BG_COLOR);
        
        Label statusLabel = new Label("健康状态:");
        statusLabel.setFont(labelFont);
        statusLabel.setForeground(LABEL_COLOR);
        
        TextField statusField = new TextField(15);
        statusField.setEditable(false);
        statusField.setFont(resultFont);
        
        statusPanel.add(statusLabel);
        statusPanel.add(statusField);
        
        // 添加到结果面板
        resultPanel.add(bmiResultPanel);
        resultPanel.add(statusPanel);
        
        // 添加内边距
        Panel paddingPanel = new Panel(new BorderLayout());
        paddingPanel.setBackground(RESULT_BG_COLOR);
        paddingPanel.add(resultPanel, BorderLayout.CENTER);
        // 添加内边距
        paddingPanel.add(new Panel() {{ setBackground(RESULT_BG_COLOR); setPreferredSize(new Dimension(10, 0)); }}, BorderLayout.WEST);
        paddingPanel.add(new Panel() {{ setBackground(RESULT_BG_COLOR); setPreferredSize(new Dimension(10, 0)); }}, BorderLayout.EAST);
        paddingPanel.add(new Panel() {{ setBackground(RESULT_BG_COLOR); setPreferredSize(new Dimension(0, 10)); }}, BorderLayout.NORTH);
        paddingPanel.add(new Panel() {{ setBackground(RESULT_BG_COLOR); setPreferredSize(new Dimension(0, 10)); }}, BorderLayout.SOUTH);
        
        resultOuterPanel.add(paddingPanel, BorderLayout.CENTER);
        
        // 添加结果面板到主框架底部
        frame.add(resultOuterPanel, BorderLayout.SOUTH);

        // 计算按钮的事件处理
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 获取输入的身高和体重
                    double height = Double.parseDouble(heightField.getText()) / 100; // 转换为米
                    double weight = Double.parseDouble(weightField.getText());
                    boolean isMale = maleCheckbox.getState(); // 获取性别

                    // 计算BMI
                    double bmi = weight / (height * height);
                    DecimalFormat df = new DecimalFormat("#.##"); // 格式化为两位小数
                    String formattedBMI = df.format(bmi);
                    
                    // 设置BMI结果
                    bmiResultField.setText(formattedBMI);
                    
                    // 根据BMI判断体重状态
                    String status = getBMIStatus(bmi, isMale);
                    statusField.setText(status);
                    
                    // 根据BMI结果设置不同的背景色
                    if (status.contains("正常")) {
                        statusField.setBackground(new Color(144, 238, 144)); // 浅绿色
                    } else if (status.contains("过轻") || status.contains("消瘦")) {
                        statusField.setBackground(new Color(255, 255, 224)); // 浅黄色
                    } else {
                        statusField.setBackground(new Color(255, 182, 193)); // 浅红色
                    }
                    
                } catch (NumberFormatException ex) {
                    bmiResultField.setText("输入错误，请输入数字");
                    statusField.setText("");
                    statusField.setBackground(Color.WHITE);
                }
            }
        });

        // 窗口关闭事件
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // 设置窗口可见并强制调整大小
        frame.setVisible(true);
    }

    // 根据BMI值判断体重状态
    private static String getBMIStatus(double bmi, boolean isMale) {
        // 根据图表中的BMI标准判断体重状态
        if (bmi < 16) {
            return "体重过轻（严重消瘦）";
        } else if (bmi < 17) {
            return "体重过轻（中度消瘦）";
        } else if (bmi < 18.5) {
            return "体重过轻（轻度消瘦）";
        } else if (bmi < 25) {
            return "体重正常";
        } else if (bmi < 30) {
            return "体重过重（肥胖前期）";
        } else if (bmi < 35) {
            return "肥胖I级（轻度肥胖）";
        } else if (bmi < 40) {
            return "肥胖II级（中度肥胖）";
        } else {
            return "肥胖III级（严重肥胖）";
        }
    }
    
    // 自定义图片面板类
    static class ImagePanel extends Panel {
        private Image image;
        
        public ImagePanel(String imagePath) {
            try {
                File file = new File(imagePath);
                if (file.exists()) {
                    image = Toolkit.getDefaultToolkit().getImage(imagePath);
                    // 添加图片加载完成的监听
                    MediaTracker tracker = new MediaTracker(this);
                    tracker.addImage(image, 0);
                    try {
                        tracker.waitForID(0, 1000); // 等待最多1秒图片加载
                    } catch (InterruptedException e) {
                        System.err.println("图片加载被中断");
                    }
                } else {
                    System.err.println("图片文件不存在: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("加载图片时发生错误: " + e.getMessage());
            }
        }
        
        @Override
        public void paint(Graphics g) {
            // 先画组件
            super.paint(g);
            
            // 再画图片，确保图片在最上层
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}