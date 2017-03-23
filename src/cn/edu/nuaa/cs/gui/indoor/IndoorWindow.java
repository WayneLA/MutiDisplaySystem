package cn.edu.nuaa.cs.gui.indoor;

import cn.edu.nuaa.cs.io.DirectoryWatcher2;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.*;

public class IndoorWindow extends JPanel {
	public static String fileName = "F:\\Data\\Indoor\\building\\fernuni.dat";
	public static String locationPath = "F:\\Data\\Indoor\\location\\";
	public static Thread watchThread2;

	public IndoorWindow(){
		setLayout(new BorderLayout());
		JLabel JL_title = new JLabel("室内对象显示窗口", SwingConstants.LEFT);
		JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
		JL_title.setBackground(Color.lightGray);
		JL_title.setOpaque(true);
		add(JL_title, BorderLayout.NORTH);
		add(new TDViewer(), BorderLayout.CENTER);

		File watchDir = new File(locationPath);
		if(!(watchDir.exists()&&watchDir.isDirectory())){
			watchDir.mkdirs();
		}

		watchThread2 = new Thread(new DirectoryWatcher2(Paths.get(locationPath)));
		watchThread2.start();
	}
}
