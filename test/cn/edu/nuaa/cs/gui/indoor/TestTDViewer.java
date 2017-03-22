package cn.edu.nuaa.cs.gui.indoor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 85492 on 2017/2/28.
 */
public class TestTDViewer{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame jframe = new JFrame();
                    jframe.setBounds(100,50,800,600);
                    jframe.setLayout(new BorderLayout());
                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jframe.getContentPane().add(new TDViewer(),BorderLayout.CENTER);
                    jframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
