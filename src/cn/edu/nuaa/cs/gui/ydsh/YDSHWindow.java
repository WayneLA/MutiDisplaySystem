package cn.edu.nuaa.cs.gui.ydsh;

import cn.edu.nuaa.cs.gui.main.MainWindow;

import java.awt.*;

import javax.swing.*;

public class YDSHWindow extends JPanel {
	public static String dirPath = MainWindow.rootPath+"\\YDSH";

	public YDSHWindow(){
		setLayout(new BorderLayout());
		JLabel JL_title = new JLabel("目标对象运动手环数据显示窗口", SwingConstants.LEFT);
		JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
		JL_title.setBackground(Color.lightGray);
		JL_title.setOpaque(true);
		add(JL_title, BorderLayout.NORTH);
		add(new YDSHViewer(), BorderLayout.CENTER);
	}
}
