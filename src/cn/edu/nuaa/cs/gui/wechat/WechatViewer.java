package cn.edu.nuaa.cs.gui.wechat;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 85492 on 2018/1/6.
 */
public class WechatViewer extends JPanel {
    private JPanel webBrowserPanel;
    private JWebBrowser webBrowser;
    private static final String title = "微信数据情绪监测";

    public WechatViewer(String url) {
        super(new BorderLayout());
        webBrowserPanel = new JPanel(new BorderLayout());
        webBrowser = new JWebBrowser();
        webBrowser.navigate(url);
        webBrowser.setButtonBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setBarsVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
    }
}
