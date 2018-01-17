package cn.edu.nuaa.cs.gui.ydsh;

import cn.edu.nuaa.cs.gui.main.MainWindow;

import java.awt.*;

import javax.swing.*;

public class YDSHWindow extends JPanel {
	public static String dirPath = MainWindow.rootPath_Data +"\\YDSH";

	public YDSHWindow(){
		setLayout(new BorderLayout());
		JLabel JL_title = new JLabel("目标对象运动手环数据显示窗口", SwingConstants.LEFT);
		JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
		JL_title.setBackground(Color.lightGray);
		JL_title.setOpaque(true);
		add(JL_title, BorderLayout.NORTH);
		add(new YDSHViewer(), BorderLayout.CENTER);
	}

	public static void main(String[] args){
		dirPath = "D:\\IdeaProjects\\Data\\YDSH";
		JFrame jf = new JFrame("目标对象运动手环数据显示窗口");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(300,50,800,600);
		jf.setLayout(new BorderLayout());
		jf.add(new YDSHWindow(),BorderLayout.CENTER);
		jf.setResizable(false);
		jf.setVisible(true);
	}
}
