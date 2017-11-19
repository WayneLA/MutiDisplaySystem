package cn.edu.nuaa.cs.gui.main;

import java.awt.*;
import javax.swing.*;

public class ZHWindow extends JPanel{

    public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	public ZHWindow() {
	    setLayout(new BorderLayout());

		JSplitPane jsp_u = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, MainWindow.win02, MainWindow.win04);
		jsp_u.setOneTouchExpandable(true);
		jsp_u.setEnabled(true);
		jsp_u.setDividerLocation(width/4);

		JSplitPane jsp_d = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, MainWindow.win03, new JPanel());
		jsp_d.setOneTouchExpandable(true);
		jsp_d.setEnabled(true);
		jsp_d.setDividerLocation(width/4);

		JSplitPane jsp_r = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp_u, jsp_d);
		jsp_r.setEnabled(false);
		jsp_r.setDividerLocation(height/2);

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, MainWindow.win01, jsp_r);
		jsp.setEnabled(false);
		jsp.setDividerLocation(width/2);

		add(jsp,BorderLayout.CENTER);
	}

}
