package cn.edu.nuaa.cs.gui.main;

import cn.edu.nuaa.cs.gui.face.FaceWindow;
import cn.edu.nuaa.cs.gui.indoor.IndoorWindow;
import cn.edu.nuaa.cs.gui.shuru.SRWindow;
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
    private static String title = "基于民航安全分析的跨源数据显示系统";
    public static String rootPath = "";

    private static int mwwidth = 800;
    private static int mwheight = 600;

    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static MainWindow window;
    public static JPanel jpenter = JPanelsEnter();
    public static JPanel jpmenu = JPanelsMenu();

    public static JPanel win01 = new JPanel(new BorderLayout());
    public static JPanel win02 = new JPanel(new BorderLayout());
    public static JPanel win03 = new JPanel(new BorderLayout());
    public static JPanel win04 = new JPanel(new BorderLayout());

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
        setBackground(Color.WHITE);
        setBounds(200, 60, mwwidth, mwheight);
        setLayout(null);
        setResizable(true);

        String rp = System.getProperty("user.dir");
        int index = rp.indexOf("MutiDisplaySystem");
        rootPath = rp.substring(0,index)+"Data";
    }

    public static JPanel JPanelsEnter(){
        JPanel jp = new BackgroundPanel();

        JLabel jl = new JLabel(title);
        jl.setFont(new java.awt.Font("楷体", 1, 30));
        jl.setBounds(110,150,800,50);

        JButton jbexit = new JButton("退出");
        jbexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        JButton jbenter = new JButton("进入");
        jbenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.remove(jpenter);
                window.repaint();
                window.add(jpmenu);
                //dispose();
            }
        });

        jbexit.setBounds(200,300,100,40);
        jbenter.setBounds(450,300,100,40);

        JLabel jlc = new JLabel("copyright @ NUAA");
        jlc.setFont(new java.awt.Font("黑体", 4, 15));
        jlc.setBounds(300,500,800,50);

        jp.add(jl);
        jp.add(jbexit);
        jp.add(jbenter);
        jp.add(jlc);

        jp.setBounds(0,0,800,600);

        return jp;
    }

    public static JPanel JPanelsMenu(){
        JPanel jp = new BackgroundPanel();

        JLabel jl1 = new JLabel("1、综合显示");
        jl1.setFont(new java.awt.Font("楷体", 1, 25));
        jl1.setBounds(120,100,800,50);
        jl1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("1、综合显示");
                JFrame jf = new JFrame();
                jf.setBounds(0,0, width, height);
                jf.setLayout(new BorderLayout());
                jf.add(new ZHWindow(),BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });

        JLabel jl2 = new JLabel("2、室内运动对象运动轨迹显示");
        jl2.setFont(new java.awt.Font("楷体", 1, 25));
        jl2.setBounds(120,160,800,50);
        jl2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("2、室内运动对象运动轨迹显示");
                JFrame jf = new JFrame();
                jf.setBounds(10,10,800,600);
                jf.setLayout(new BorderLayout());
                jf.add(win01,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });

        JLabel jl3 = new JLabel("3、眼动仪监控数据显示");
        jl3.setFont(new java.awt.Font("楷体", 1, 25));
        jl3.setBounds(120,220,800,50);
        jl3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("3、眼动仪监控数据显示");
                JFrame jf = new JFrame();
                jf.setBounds(10,10,800,600);
                jf.setLayout(new BorderLayout());
                jf.add(win02,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });

        JLabel jl4 = new JLabel("4、运动手环数据显示");
        jl4.setFont(new java.awt.Font("楷体", 1, 25));
        jl4.setBounds(120,280,800,50);
        jl4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("4、运动手环数据显示");
                JFrame jf = new JFrame();
                jf.setBounds(10,10,800,600);
                jf.setLayout(new BorderLayout());
                jf.add(win03,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });

        JLabel jl5 = new JLabel("5、输入操作监控显示");
        jl5.setFont(new java.awt.Font("楷体", 1, 25));
        jl5.setBounds(120,340,800,50);
        jl5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("5、输入操作监控显示");
                JFrame jf = new JFrame();
                jf.setBounds(10,10,800,600);
                jf.setLayout(new BorderLayout());
                jf.add(win04,BorderLayout.CENTER);
                jf.setVisible(true);
            }
        });

        jp.add(jl1);
        jp.add(jl2);
        jp.add(jl3);
        jp.add(jl4);
        jp.add(jl5);

        jp.setBounds(0,0,800,600);
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
