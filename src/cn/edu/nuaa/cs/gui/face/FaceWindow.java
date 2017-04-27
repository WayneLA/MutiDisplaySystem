package cn.edu.nuaa.cs.gui.face;

import cn.edu.nuaa.cs.gui.main.MainWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.*;

public class FaceWindow extends JPanel{
	public static String faceLabPath = MainWindow.rootPath+"\\FaceLab\\";
	public static Thread watchThread;

	public FaceWindow(){
		setLayout(new BorderLayout());
		JLabel JL_title = new JLabel("眼动仪数据显示窗口", SwingConstants.LEFT);
		JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
		JL_title.setBackground(Color.lightGray);
		JL_title.setOpaque(true);
		add(JL_title, BorderLayout.NORTH);
		add(new FaceViewer(), BorderLayout.CENTER);

		File watchDir = new File(faceLabPath);
		if(!(watchDir.exists()&&watchDir.isDirectory())){
			watchDir.mkdirs();
		}

		watchThread = new Thread(new DirectoryWatcher(Paths.get(FaceWindow.faceLabPath)));
		watchThread.start();
	}
}
