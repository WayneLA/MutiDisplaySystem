package cn.edu.nuaa.cs.gui.ydsh;

import cn.edu.nuaa.cs.gui.face.FaceWindow;

import javax.swing.*;
import java.awt.*;

public class TestYDSHWindow extends JPanel {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame jframe = new JFrame();
					jframe.setBounds(100,50,450,400);
					jframe.setLayout(new BorderLayout());
					jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jframe.getContentPane().add(new YDSHWindow(),BorderLayout.CENTER);
					jframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
