package cn.edu.nuaa.cs.gui.heart;

import cn.edu.nuaa.cs.gui.main.MainWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher4;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by 85492 on 2017/10/27.
 */
public class HeartWindow extends JPanel{
    public static String dirPath = MainWindow.rootPath_Data +"\\Heart\\";
    public static Thread watchThread;

    public HeartWindow(){
        setLayout(new BorderLayout());
        JLabel JL_title = new JLabel("生物状态监控数据显示窗口", SwingConstants.LEFT);
        JL_title.setFont(new Font("宋体", Font.BOLD,20));
        JL_title.setBackground(Color.lightGray);
        JL_title.setOpaque(true);
        add(JL_title, BorderLayout.NORTH);
        add(new HeartViewer(), BorderLayout.CENTER);

        watchThread = new Thread(new DirectoryWatcher4(Paths.get(HeartWindow.dirPath)));
        watchThread.start();
    }
}
