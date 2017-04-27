package cn.edu.nuaa.cs.gui.main;

import java.awt.*;
import java.nio.file.Paths;
import javax.swing.*;
import cn.edu.nuaa.cs.gui.indoor.IndoorWindow;
import cn.edu.nuaa.cs.gui.ydsh.YDSHWindow;
import cn.edu.nuaa.cs.gui.face.FaceWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher;

public class MainWindow  extends JFrame{
	private static String title = "基于民航安全分析的跨源数据显示系统";
	public static String rootPath = "";

	public static JPanel win01 = new JPanel(new BorderLayout());
	public static JPanel win02 = new JPanel(new BorderLayout());
	public static JPanel win03 = new JPanel(new BorderLayout());

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

		JSplitPane jsp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, win02, win03);
		jsp1.setEnabled(false);
		jsp1.setDividerLocation((height-50)/2);
		JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, win01, jsp1);
		jsp2.setEnabled(false);
		jsp2.setDividerLocation(width*2/3);

		add(jsp2,BorderLayout.CENTER);

	}

	public void initMenu(){

	}
}
