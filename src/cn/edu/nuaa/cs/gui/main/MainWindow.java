package cn.edu.nuaa.cs.gui.main;

import java.awt.*;
import java.nio.file.Paths;
import javax.swing.*;
import cn.edu.nuaa.cs.gui.indoor.IndoorWindow;
import cn.edu.nuaa.cs.gui.shuru.SRWindow;
import cn.edu.nuaa.cs.gui.ydsh.YDSHWindow;
import cn.edu.nuaa.cs.gui.face.FaceWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher;

public class MainWindow  extends JFrame{
	private static String title = "基于民航安全分析的跨源数据显示系统";
	public static String rootPath = "";

	public static JPanel win01 = new JPanel(new BorderLayout());
	public static JPanel win02 = new JPanel(new BorderLayout());
	public static JPanel win03 = new JPanel(new BorderLayout());
	public static JPanel win04 = new JPanel(new BorderLayout());

	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static void main(String[] args) {
		String rp = System.getProperty("user.dir");
		int index = rp.indexOf("MutiDisplaySystem");
		rootPath = rp.substring(0,index)+"Data";

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			Thread.sleep(500);
		}catch(InterruptedException e){
			e.printStackTrace();
		}

		Thread cthread = new Thread(new DirectoryWatcher(Paths.get(FaceWindow.faceLabPath)));
		cthread.run();
	}
	
	public MainWindow() {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setBounds(0, 0, width, height-30);
		setLayout(new BorderLayout());
		setResizable(true);

		initMenu();

		win01.add(new IndoorWindow(), BorderLayout.CENTER);
		win02.add(new FaceWindow(), BorderLayout.CENTER);
		win03.add(new YDSHWindow(), BorderLayout.CENTER);
		win04.add(new SRWindow(), BorderLayout.CENTER);
/*
		JSplitPane jsp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, win02, win03);
		jsp1.setEnabled(false);
		jsp1.setDividerLocation((height-50)/2);
		JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win01, jsp1);
		jsp2.setEnabled(false);
		jsp2.setDividerLocation(width/2);
		add(jsp2,BorderLayout.CENTER);
*/
/*
		JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win04, win04);
		jsp1.setEnabled(true);
		jsp1.setOneTouchExpandable(true);
//		jsp1.setDividerLocation(width/2);
		JSplitPane jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, win02, jsp1);
		jsp2.setEnabled(false);
		jsp2.setDividerLocation(height/2);
		JSplitPane jsp3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win01, jsp2);
		jsp3.setEnabled(false);
		jsp3.setDividerLocation(width/2);
		add(jsp3,BorderLayout.CENTER);
*/
		JSplitPane jsp_u = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win02, win04);
		jsp_u.setOneTouchExpandable(true);
		jsp_u.setEnabled(true);
		jsp_u.setDividerLocation(width/4);

		JSplitPane jsp_d = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win03, new JPanel());
		jsp_d.setOneTouchExpandable(true);
		jsp_d.setEnabled(true);
		jsp_d.setDividerLocation(width/4);

		JSplitPane jsp_r = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp_u, jsp_d);
		jsp_r.setEnabled(false);
		jsp_r.setDividerLocation(height/2);

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win01, jsp_r);
		jsp.setEnabled(false);
		jsp.setDividerLocation(width/2);
		add(jsp,BorderLayout.CENTER);
	}

	public void initMenu(){

	}
}
