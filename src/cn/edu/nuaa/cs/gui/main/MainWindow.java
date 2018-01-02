package cn.edu.nuaa.cs.gui.main;

import cn.edu.nuaa.cs.gui.face.FaceWindow;
import cn.edu.nuaa.cs.gui.heart.HeartWindow;
import cn.edu.nuaa.cs.gui.indoor.IndoorWindow;
import cn.edu.nuaa.cs.gui.shuru.SRWindow;
import cn.edu.nuaa.cs.gui.warning.IndoorWarning;
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
    private static String title = "民航飞行人员安全风险监测信息系统";
    public static String rootPath = "";

    private static int mwwidth = 800;
    private static int mwheight = 600;

    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static MainWindow window;
    public static JPanel jpenter = JPanelsEnter();
    public static JPanel jpmenu = JPanelsMenu01();

    public static JPanel win01 = new JPanel(new BorderLayout());
    public static JPanel win02 = new JPanel(new BorderLayout());
    public static JPanel win03 = new JPanel(new BorderLayout());
    public static JPanel win04 = new JPanel(new BorderLayout());
    public static JPanel win05 = new JPanel(new BorderLayout());

    public static Thread cthread;

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new MainWindow();
                    window.getContentPane().add(jpenter);
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
        setBounds(100,100,mwwidth,mwheight);
        setResizable(true);
//        setUndecorated(true);
//        getGraphicsConfiguration().getDevice().setFullScreenWindow(this);

        String rp = System.getProperty("user.dir");
        int index = rp.indexOf("MutiDisplaySystem");
        rootPath = rp.substring(0,index)+"Data";
    }

    public static JPanel JPanelsEnter(){
        JPanel jp = new BackgroundPanel();
        jp.setBounds(0,0, width, height);
        jp.setLayout(new BorderLayout());

        JLabel jl = new JLabel(title, JLabel.CENTER);
        jl.setFont(new java.awt.Font("楷体", 1, 40));
        jl.setBounds(110,150,800,50);

        JLabel jl_username = new JLabel("用户名 ：");
        JTextField jtf_usr = new JTextField(10);
        jtf_usr.setSize(30,80);

        JLabel jl_pwd = new JLabel("密　码 ：");
        JPasswordField jpf_pwd = new JPasswordField(10);
        jpf_pwd.setSize(30,80);

        JButton jbexit = new JButton("退出");
        jbexit.setBounds(200,300,100,40);
        jbexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        JButton jbenter = new JButton("登录");
        jbenter.setBounds(450,300,100,40);
        jbenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.remove(jpenter);
                window.repaint();
                window.add(jpmenu);
                //dispose();
            }
        });

        JLabel jlc = new JLabel("copyright @ NUAA",JLabel.CENTER);
        jlc.setFont(new java.awt.Font("黑体", 4, 15));
        jlc.setBounds(300,500,800,50);


        JPanel jpu = new JPanel(new BorderLayout());
        jpu.setOpaque(false);
//        jpu.setPreferredSize(new Dimension(800,300));
        jpu.add(jl, BorderLayout.CENTER);

        JPanel jpm = new JPanel(new GridLayout(3,1));
        jpm.setPreferredSize(new Dimension(800,150));
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
        jpm03.add(jbenter);
        jpm.add(jpm03);

        JPanel jpb = new JPanel(new BorderLayout());
        jpb.setPreferredSize(new Dimension(800,100));
        jpb.setOpaque(false);
        jpb.add(jlc,BorderLayout.CENTER);

        JPanel jpmb = new JPanel(new BorderLayout());
        jpmb.setOpaque(false);
        jpmb.add(jpm,BorderLayout.NORTH);
        jpmb.add(jpb,BorderLayout.CENTER);

        jp.add(jpu,BorderLayout.CENTER);
        jp.add(jpmb,BorderLayout.SOUTH);

        return jp;
    }

    public static JPanel JPanelsMenu01() {
        JPanel jp = new BackgroundPanel();
        jp.setBounds(0,0,width,height);

        JLabel jl1 = new JLabel("1、舱内环境监测");
        jl1.setFont(new java.awt.Font("楷体", 1, 25));
        jl1.setBounds(120,100,800,50);
            JLabel jl11 = new JLabel("1.1、眼动仪监控数据显示");
            jl11.setFont(new java.awt.Font("楷体", 1, 20));
            jl11.setBounds(150,130,800,50);
            jl11.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("1.1、眼动仪监控数据显示");
                    JFrame jf = new JFrame();

                    jf.setBounds(300,50,450,420);

                    jf.setLayout(new BorderLayout());
                    jf.add(win02,BorderLayout.CENTER);
                    jf.setVisible(true);
                }
            });



        JLabel jl2 = new JLabel("2、工作环境监测");
        jl2.setFont(new java.awt.Font("楷体", 1, 25));
        jl2.setBounds(120,180,800,50);
            JLabel jl21 = new JLabel("2.1、眼动仪监控数据显示");
            jl21.setFont(new java.awt.Font("楷体", 1, 20));
            jl21.setBounds(150,210,800,50);
            jl21.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("2.1、眼动仪监控数据显示");
                    JFrame jf = new JFrame();

                    jf.setBounds(300,50,450,420);

                    jf.setLayout(new BorderLayout());
                    jf.add(win02,BorderLayout.CENTER);
                    jf.setVisible(true);
                }
            });
            JLabel jl22 = new JLabel("2.2、输入操作监控显示");
            jl22.setFont(new java.awt.Font("楷体", 1, 20));
            jl22.setBounds(150,240,800,50);
            jl22.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("2、输入操作监控显示");
                    JFrame jf = new JFrame();
                    jf.setBounds(300,50,450,420);
                    jf.setLayout(new BorderLayout());
                    jf.add(win04,BorderLayout.CENTER);
                    jf.setVisible(true);
                }
            });
            JLabel jl23 = new JLabel("2.3、室内运动对象运动轨迹显示");
            jl23.setFont(new java.awt.Font("楷体", 1, 20));
            jl23.setBounds(150,270,800,50);
            jl23.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("2.3、室内运动对象运动轨迹显示");
                    JFrame jf = new JFrame();
                    jf.setBounds(300,50,800,600);
                    jf.setLayout(new BorderLayout());
                    jf.add(win01,BorderLayout.CENTER);
                    jf.setVisible(true);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    IndoorWarning.showWarning();
                }
            });



        JLabel jl3 = new JLabel("3、生活环境监测");
        jl3.setFont(new java.awt.Font("楷体", 1, 25));
        jl3.setBounds(120,320,800,50);
            JLabel jl31 = new JLabel("3.1、运动手环数据显示");
            jl31.setFont(new java.awt.Font("楷体", 1, 20));
            jl31.setBounds(150,350,800,50);
            jl31.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("3.1、运动手环数据显示");
                    JFrame jf = new JFrame();
                    jf.setBounds(300,50,800,600);
                    jf.setLayout(new BorderLayout());
                    jf.add(win03,BorderLayout.CENTER);
                    jf.setVisible(true);
                }
            });
            JLabel jl32 = new JLabel("3.2、生物数据显示");
            jl32.setFont(new java.awt.Font("楷体", 1, 20));
            jl32.setBounds(150,380,800,50);
            jl32.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("3.2、生物数据显示");
                    JFrame jf = new JFrame();
                    jf.setBounds(300,50,450,420);
                    jf.setLayout(new BorderLayout());
                    jf.add(win05,BorderLayout.CENTER);
                    jf.setVisible(true);
                }
            });



        JLabel jl4 = new JLabel("4、综合监测");
        jl4.setFont(new java.awt.Font("楷体", 1, 25));
        jl4.setBounds(120,430,800,50);
        jl4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("5、综合显示");
                JFrame jf = new JFrame();
                jf.setBounds(0,0, width, height);
                jf.setLayout(new BorderLayout());
                jf.add(new ZHWindow(),BorderLayout.CENTER);
                jf.setVisible(true);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                IndoorWarning.showWarning();
            }
        });

        jp.add(jl1);
        jp.add(jl11);
        jp.add(jl2);
        jp.add(jl21);
        jp.add(jl22);
        jp.add(jl23);
        jp.add(jl3);
        jp.add(jl31);
        jp.add(jl32);
        jp.add(jl4);

        return jp;
    }

    public static JPanel JPanelsMenu02(){
        JPanel jp = new BackgroundPanel();

//        JLabel jl21 = new JLabel("2.1、眼动仪监控数据显示");
//        jl21.setFont(new java.awt.Font("楷体", 1, 25));
//        jl21.setBounds(120,190,800,50);
//        jl21.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("2.1、眼动仪监控数据显示");
//                JFrame jf = new JFrame();
//
//                jf.setBounds(10,10,450,420);
//
//                jf.setLayout(new BorderLayout());
//                jf.add(win02,BorderLayout.CENTER);
//                jf.setVisible(true);
//            }
//        });
//
//        JLabel jl11 = new JLabel("1.1、输入操作监控显示");
//        jl11.setFont(new java.awt.Font("楷体", 1, 25));
//        jl11.setBounds(120,160,800,50);
//        jl11.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("2、输入操作监控显示");
//                JFrame jf = new JFrame();
//                jf.setBounds(10,10,450,420);
//                jf.setLayout(new BorderLayout());
//                jf.add(win04,BorderLayout.CENTER);
//                jf.setVisible(true);
//            }
//        });
//
//        JLabel jl3 = new JLabel("3、运动手环数据显示");
//        jl3.setFont(new java.awt.Font("楷体", 1, 25));
//        jl3.setBounds(120,220,800,50);
//        jl3.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("3、运动手环数据显示");
//                JFrame jf = new JFrame();
//                jf.setBounds(10,10,800,600);
//                jf.setLayout(new BorderLayout());
//                jf.add(win03,BorderLayout.CENTER);
//                jf.setVisible(true);
//            }
//        });
//
//        JLabel jl4 = new JLabel("4、室内运动对象运动轨迹显示");
//        jl4.setFont(new java.awt.Font("楷体", 1, 25));
//        jl4.setBounds(120,280,800,50);
//        jl4.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("4、室内运动对象运动轨迹显示");
//                JFrame jf = new JFrame();
//                jf.setBounds(10,10,800,600);
//                jf.setLayout(new BorderLayout());
//                jf.add(win01,BorderLayout.CENTER);
//                jf.setVisible(true);
//            }
//        });
//
//        JLabel jl5 = new JLabel("5、综合显示");
//        jl5.setFont(new java.awt.Font("楷体", 1, 25));
//        jl5.setBounds(120,340,800,50);
//        jl5.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("5、综合显示");
//                JFrame jf = new JFrame();
//                jf.setBounds(0,0, width, height);
//                jf.setLayout(new BorderLayout());
//                jf.add(new ZHWindow(),BorderLayout.CENTER);
//                jf.setVisible(true);
//            }
//        });
//
//        jp.add(jl21);
//        jp.add(jl11);
//        jp.add(jl3);
//        jp.add(jl4);
//        jp.add(jl5);

        jp.setBounds(0,0,width,height);
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
