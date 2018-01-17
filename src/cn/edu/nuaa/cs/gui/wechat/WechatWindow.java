package cn.edu.nuaa.cs.gui.wechat;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 85492 on 2018/1/8.
 */
public class WechatWindow extends JPanel{
    public static final String url = "http://localhost:8080/ChatDetection/html/Index.html";

    public WechatWindow(){
        setLayout(new BorderLayout());
        JLabel JL_title = new JLabel("微信数据情绪监测显示", SwingConstants.LEFT);
        JL_title.setFont(new java.awt.Font("宋体", Font.BOLD,20));
        JL_title.setBackground(Color.lightGray);
        JL_title.setOpaque(true);
        add(JL_title, BorderLayout.NORTH);
        add(new WechatViewer(url), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               JFrame jf = new JFrame("Test");
               jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               jf.setBounds(300, 50, 800, 600);
               jf.getContentPane().add(new WechatViewer(WechatWindow.url), BorderLayout.CENTER);
               jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
               jf.add(new WechatViewer(WechatWindow.url), BorderLayout.CENTER);
               jf.setVisible(true);
           }
        });
        NativeInterface.runEventPump();
    }
}
