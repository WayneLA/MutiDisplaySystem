package cn.edu.nuaa.cs.gui.main;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import cn.edu.nuaa.cs.gui.face.FaceWindow;
import cn.edu.nuaa.cs.gui.heart.HeartWindow;
import cn.edu.nuaa.cs.gui.indoor.IndoorWindow;
import cn.edu.nuaa.cs.gui.shuru.SRWindow;
import cn.edu.nuaa.cs.gui.warning.IndoorWarning;
import cn.edu.nuaa.cs.gui.wechat.WechatViewer;
import cn.edu.nuaa.cs.gui.wechat.WechatWindow;
import cn.edu.nuaa.cs.gui.ydsh.YDSHWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by 85492 on 2017/11/18.
 */
public class MainWindow extends JFrame {
    public static String title = "民航飞行人员安全风险监测信息原型系统";
    public static String rootPath_Data = "";

    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height = Toolkit.getDefaultToolkit().getScreenSize().height-25;

    public static MainWindow window;
    public static JPanel jp_init = JPanels_init();
    public static JPanel jp_menu = JPanels_menus();

    public static JPanel win01 = new JPanel(new BorderLayout());
    public static JPanel win02 = new JPanel(new BorderLayout());
    public static JPanel win03 = new JPanel(new BorderLayout());
    public static JPanel win04 = new JPanel(new BorderLayout());
    public static JPanel win05 = new JPanel(new BorderLayout());

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new MainWindow();
                    window.getContentPane().add(jp_init);
                    window.setVisible(true);
                    new Thread(){
                        public void run(){
                            System.out.println("windows start ... ");
                            win01.add(new IndoorWindow(), BorderLayout.CENTER);
                            win02.add(new FaceWindow(), BorderLayout.CENTER);
                            win03.add(new YDSHWindow(), BorderLayout.CENTER);
                            win04.add(new SRWindow(), BorderLayout.CENTER);
                            win05.add(new HeartWindow(), BorderLayout.CENTER);
                            System.out.println("windows end ... ");
                        }
                    }.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainWindow(){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0,0, width, height);
        setResizable(false);

        String rp = System.getProperty("user.dir");
        int index = rp.indexOf("MutiDisplaySystem");
        rootPath_Data = rp.substring(0,index)+"Data";
    }
    public static JPanel JPanels_init(){
        JPanel jp = new BackgroundPanel();
        jp.setLayout(new BorderLayout());

        // title
        JLabel jl_title = new JLabel(title, JLabel.CENTER);
        jl_title.setFont(new java.awt.Font("楷体", 1, 40));
        jl_title.setBounds(110,150,800,50);

        // login
        JLabel jl_username = new JLabel("用户名 ：");
        jl_username.setFont(new java.awt.Font("黑体", 4, 20));
        JTextField jtf_usr = new JTextField(10);
        jtf_usr.setSize(30,80);
        JLabel jl_pwd = new JLabel("密　码 ：");
        jl_pwd.setFont(new java.awt.Font("黑体", 4, 20));
        JPasswordField jpf_pwd = new JPasswordField(10);
        jpf_pwd.setSize(30,80);

        // copyright
        JLabel jlc = new JLabel("copyright @ NUAA",JLabel.CENTER);
        jlc.setFont(new java.awt.Font("黑体", 4, 15));
        jlc.setBounds(300,500,800,50);

        // button
        JButton jbexit = new JButton("注册");
        jbexit.setFont(new java.awt.Font("黑体", 4, 20));
        jbexit.setBounds(200,300,100,40);
        jbexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        JButton jbenter = new JButton("登录");
        jbenter.setFont(new java.awt.Font("黑体", 4, 20));
        jbenter.setBounds(450,300,100,40);
        jbenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf_usr.getText().equals("admin") &&
                        String.valueOf(jpf_pwd.getPassword()).equals("123456")){
                    window.getContentPane().remove(jp_init);
                    window.getContentPane().add(jp_menu);
                    window.getContentPane().repaint();
                }else{
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "alert", JOptionPane.ERROR_MESSAGE);
                    jtf_usr.setText("");
                    jpf_pwd.setText("");
                }
            }
        });

        // upper
        JPanel jpu = new JPanel(new BorderLayout());
        jpu.setOpaque(false);
        jpu.add(jl_title, BorderLayout.CENTER);

        // bottom
        JPanel jpm = new JPanel(new GridLayout(3,1));
        jpm.setOpaque(false);

        JPanel jpm01 = new JPanel();
        jpm01.setOpaque(false);
        jpm01.add(jl_username);
        jpm01.add(jtf_usr);
        jpm.add(jpm01);

        JPanel jpm02 = new JPanel();
        jpm02.setOpaque(false);
        jpm02.add(jl_pwd);
        jpm02.add(jpf_pwd);
        jpm.add(jpm02);

        JPanel jpm03 = new JPanel();
        jpm03.setOpaque(false);
        jpm03.add(jbexit);
        jpm03.add(new JLabel("                "));
        jpm03.add(jbenter);
        jpm.add(jpm03);

        JPanel jpcb = new JPanel(new BorderLayout());
        jpcb.setPreferredSize(new Dimension(800,100));
        jpcb.setOpaque(false);
        jpcb.add(jlc,BorderLayout.CENTER);

        JPanel jpmb = new JPanel(new BorderLayout());
        jpmb.setOpaque(false);
        jpmb.add(jpm,BorderLayout.NORTH);
        jpmb.add(jpcb,BorderLayout.CENTER);

        jp.add(jpu,BorderLayout.CENTER);
        jp.add(jpmb,BorderLayout.SOUTH);

        return jp;
    }
    public static JPanel JPanels_menus() {
        JPanel jp = new JPanel();
        jp.setBounds(0,0,width,height);
        jp.setBackground(Color.CYAN);

        jp.add(menu01());
        jp.add(menu02());
        jp.add(menu03());
        jp.add(menu04());

        return jp;
    }

    public static JPanel menu01(){
        JPanel jp = new BackgroundPanel();
        jp.setLayout(new GridLayout(4,1));
        jp.setBounds(width/2-500, height/2-350, 450,300);

        JLabel jl = new JLabel("舱内环境");
        jl.setFont(new java.awt.Font("宋体", 1, 25));
        jl.setBounds(20,10,800,50);
        JLabel jl11 = new JLabel("眼动仪监控数据显示");
        jl11.setFont(new java.awt.Font("楷体", 1, 20));
        jl11.setForeground(Color.WHITE);
        jl11.setBounds(50,40,800,50);
        jl11.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(" 眼动仪监控数据显示");
                JFrame jf = new JFrame();

                jf.setBounds(300,50,450,420);

                jf.setLayout(new BorderLayout());
                jf.add(win02,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });

        jp.add(jl);
        jp.add(jl11);

        return jp;
    }
    public static JPanel menu02(){
        JPanel jp = new BackgroundPanel();
        jp.setLayout(new GridLayout(4,1));
        jp.setBounds(width/2-500, height/2, 450,300);

        JLabel jl2 = new JLabel("工作环境");
        jl2.setFont(new java.awt.Font("宋体", 1, 25));
        jl2.setBounds(20,10,800,50);

        JLabel jl21 = new JLabel("眼动仪监控数据显示");
        jl21.setFont(new java.awt.Font("楷体", 1, 20));
        jl21.setForeground(Color.WHITE);
        jl21.setBounds(50,40,800,50);
        jl21.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("眼动仪监控数据显示");
                JFrame jf = new JFrame();
                jf.setBounds(300,50,450,420);
                jf.setLayout(new BorderLayout());
                jf.add(win02,BorderLayout.CENTER);
                jf.setVisible(true);                jf.setVisible(true);

            }
        });
        JLabel jl22 = new JLabel("输入操作监控显示");
        jl22.setFont(new java.awt.Font("楷体", 1, 20));
        jl22.setForeground(Color.WHITE);
        jl22.setBounds(50,70,800,50);
        jl22.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("输入操作监控显示");
                JFrame jf = new JFrame();
                jf.setBounds(300,50,450,420);
                jf.setLayout(new BorderLayout());
                jf.add(win04,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });
        JLabel jl23 = new JLabel("室内运动对象运动轨迹显示");
        jl23.setFont(new java.awt.Font("楷体", 1, 20));
        jl23.setForeground(Color.WHITE);
        jl23.setBounds(50,100,800,50);
        jl23.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("室内运动对象运动轨迹显示");
                JFrame jf = new JFrame();
                jf.setBounds(300,50,800,600);
                jf.setLayout(new BorderLayout());
                jf.add(win01,BorderLayout.CENTER);
                jf.setVisible(true);

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                IndoorWarning.showWarning();
            }
        });

        jp.add(jl2);
        jp.add(jl21);
        jp.add(jl22);
        jp.add(jl23);

        return jp;
    }
    public static JPanel menu03(){
        JPanel jp = new BackgroundPanel();
        jp.setLayout(new GridLayout(4,1));
        jp.setBounds(width/2+50, height/2-350, 450,300);

        JLabel jl3 = new JLabel("生活环境");
        jl3.setFont(new java.awt.Font("宋体", 1, 25));
        jl3.setBounds(20,20,800,50);

        JLabel jl31 = new JLabel("运动手环数据显示");
        jl31.setFont(new java.awt.Font("楷体", 1, 20));
        jl31.setForeground(Color.WHITE);
        jl31.setBounds(50,50,800,50);
        jl31.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("运动手环数据显示");
                JFrame jf = new JFrame();
                jf.setBounds(300,50,800,600);
                jf.setLayout(new BorderLayout());
                jf.add(win03,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });
        JLabel jl32 = new JLabel("生物数据显示");
        jl32.setFont(new java.awt.Font("楷体", 1, 20));
        jl32.setForeground(Color.WHITE);
        jl32.setBounds(50,80,800,50);
        jl32.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("生物数据显示");
                JFrame jf = new JFrame();
                jf.setBounds(300,50,450,420);
                jf.setLayout(new BorderLayout());
                jf.add(win05,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });
        JLabel jl33 = new JLabel("微信数据显示");
        jl33.setFont(new java.awt.Font("楷体", 1, 20));
        jl33.setForeground(Color.WHITE);
        jl33.setBounds(50,110,800,50);
        jl33.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            NativeInterface.open();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFrame jf = new JFrame("微信数据情绪监测显示");
                    jf.setBounds(300, 50, 800, 600);
                    jf.getContentPane().add(new WechatViewer(WechatWindow.url), BorderLayout.CENTER);
                    jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    jf.add(new WechatViewer(WechatWindow.url), BorderLayout.CENTER);
                    jf.setVisible(true);
                }
            });
            NativeInterface.runEventPump();
            }
        });

        jp.add(jl3);
        jp.add(jl31);
        jp.add(jl32);
        jp.add(jl33);

        return jp;
    }
    public static JPanel menu04(){
        JPanel jp = new BackgroundPanel();
        jp.setLayout(new GridLayout(4,1));
        jp.setBounds(width/2+50, height/2, 450,300);

        JLabel jl4 = new JLabel("综合监测");
        jl4.setFont(new java.awt.Font("宋体", 1, 25));
        jl4.setForeground(Color.WHITE);
        jl4.setBounds(20,10,800,50);
        jl4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("综合显示");
                JFrame jf = new JFrame();
                jf.setBounds(0,0, width, height);
                jf.setLayout(new BorderLayout());
                jf.add(new ZHWindow(),BorderLayout.CENTER);
                jf.setVisible(true);

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                IndoorWarning.showWarning();
            }
        });

        jp.add(jl4);

        return jp;
    }

    public static class BackgroundPanel extends JPanel {
        private Image image =  new ImageIcon("images/bg.png").getImage();

        public BackgroundPanel() {
            super(null);
        }

        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }

    }
    
}
