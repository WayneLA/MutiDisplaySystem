package cn.edu.nuaa.cs.gui.indoor;

import java.awt.*;

import javax.swing.*;

public class IndoorWindow extends JPanel {
	public static String fileName = "F:\\Data\\Indoor\\fernuni.dat";

	public IndoorWindow(){
		setLayout(new BorderLayout());
		JLabel JL_title = new JLabel("室内对象显示窗口", SwingConstants.LEFT);
		JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
		JL_title.setBackground(Color.lightGray);
		JL_title.setOpaque(true);
		add(JL_title, BorderLayout.NORTH);
		add(new TDViewer(), BorderLayout.CENTER);
	}
}
