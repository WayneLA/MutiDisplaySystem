package cn.edu.nuaa.cs.gui.shuru;

import cn.edu.nuaa.cs.gui.main.MainWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher3;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by 85492 on 2017/10/27.
 */
public class SRWindow extends JPanel{
    public static String dirPath = MainWindow.rootPath_Data +"\\SR\\";
    public static Thread watchThread;

    public SRWindow(){
        setLayout(new BorderLayout());
        JLabel JL_title = new JLabel("输入操作状态监控数据显示窗口", SwingConstants.LEFT);
        JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
        JL_title.setBackground(Color.lightGray);
        JL_title.setOpaque(true);
        add(JL_title, BorderLayout.NORTH);
        add(new SRViewer(), BorderLayout.CENTER);

        watchThread = new Thread(new DirectoryWatcher3(Paths.get(SRWindow.dirPath)));
        watchThread.start();
    }
}
