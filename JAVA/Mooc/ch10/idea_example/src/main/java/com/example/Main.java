package com.example;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;

public class Main extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First GUI");
        frame.setBounds(500, 500, 500, 300);
        frame.setLayout(null);

        JTextArea area = new JTextArea();   //右边就是我们需要编辑的文本域

        File file = new File(".idea");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file);
        File[] files = Optional.ofNullable(file.listFiles()).orElseGet(() -> new File[0]);
        for (File f : files)
            root.add(new DefaultMutableTreeNode(f.getName()));
        JTree tree = new JTree(root);   //左边就是我们的文件树
        tree.addTreeSelectionListener(e -> {   //点击文件之后，我们需要变换编辑窗口中的文本内容，这里加个监听器
            area.setText("");   //先清空
            try (FileReader reader = new FileReader(".idea/"+e.getPath().getLastPathComponent().toString())){
                char[] chars = new char[128];   //直接开始读取内容
                int len;
                while ((len = reader.read(chars)) > 0)
                    area.setText(area.getText() + new String(chars, 0, len));   //开始写入到编辑窗口中
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.add(tree);
        frame.add(area);

        frame.setVisible(true);
        
    }
}