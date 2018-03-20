package cn.edu.nuaa.cs.gui.face;

import cn.edu.nuaa.cs.gui.main.MainWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.*;

public class FaceWindow extends JPanel{
	public static String faceLabPath = MainWindow.rootPath_Data +"\\FaceLab\\";
	public static Thread watchThread;
	public static Thread fvThread;

	public FaceWindow(){
		setLayout(new BorderLayout());
		JLabel JL_title = new JLabel("目标对象眼动仪数据显示窗口", SwingConstants.LEFT);
		JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
		JL_title.setBackground(Color.lightGray);
		JL_title.setOpaque(true);
		JButton jbn = new JButton("开始");
		JPanel jpb = new JPanel(new BorderLayout());
		jpb.add(jbn, BorderLayout.CENTER);
		add(JL_title, BorderLayout.NORTH);
		add(new FaceViewer(), BorderLayout.CENTER);
		add(jpb, BorderLayout.SOUTH);

		watchThread = new Thread(new DirectoryWatcher(Paths.get(FaceWindow.faceLabPath)));
		watchThread.start();

		fvThread = new Thread(new FaceViewer.FaceViewerRunnable());
		jbn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(jbn.getText().equals("开始")){
					System.out.println("Start...");
					fvThread.start();
					jbn.setText("暂停");
				}else if(jbn.getText().equals("继续")){
					System.out.println("Continue...");
					FaceViewer.FaceViewerRunnable.showflag = true;
					jbn.setText("暂停");
				}else if(jbn.getText().equals("暂停")){
					System.out.println("Pause...");
					FaceViewer.FaceViewerRunnable.showflag = false;
					jbn.setText("继续");
				}
			}
		});

		File watchDir = new File(faceLabPath);
		if(!(watchDir.exists()&&watchDir.isDirectory())){
			watchDir.mkdirs();
		}else{
			if(watchDir.listFiles().length!=0){
				FaceViewer.curFileName = ((File)watchDir.listFiles()[watchDir.listFiles().length-1]).getName();
			}
		}
	}

	public static void main(String[] args){
		JFrame jf = new JFrame();
		jf.setBounds(300,50,450,430);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout());
		jf.add(new FaceWindow(),BorderLayout.CENTER);
		jf.setVisible(true);
	}
}
