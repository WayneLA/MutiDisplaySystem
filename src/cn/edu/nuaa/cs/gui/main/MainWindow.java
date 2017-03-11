package cn.edu.nuaa.cs.gui.main;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.*;

import cn.edu.nuaa.cs.gui.indoor.IndoorWindow;
import cn.edu.nuaa.cs.gui.ydsh.YDSHWindow;
import cn.edu.nuaa.cs.gui.face.FaceWindow;
import cn.edu.nuaa.cs.io.DirectoryWatcher;

public class MainWindow  extends JFrame{
	private static String title = "NUAA-综合数据显示系统";

	public static JPanel win01 = new JPanel(new BorderLayout());
	public static JPanel win02 = new JPanel(new BorderLayout());
	public static JPanel win03 = new JPanel(new BorderLayout());

	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static void main(String[] args) {
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
		Thread cthread = new Thread(new DirectoryWatcher(Paths.get(FaceWindow.faceLabPath)));
		cthread.run();
	}
	
	public MainWindow() {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setBounds(0, 0, width, height);
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
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		JMenuItem mntmExport = new JMenuItem("Export");
		JMenuItem mntmImport = new JMenuItem("Import");
		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		JMenuItem mntmSave = new JMenuItem("Save");
		JMenuItem mntmExit = new JMenuItem("Exit");
		menuBar.add(mnFile);
		mnFile.add(mntmSave);
		mnFile.add(mntmSaveAs);
		mnFile.add(mntmImport);
		mnFile.add(mntmExport);
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
	}
}