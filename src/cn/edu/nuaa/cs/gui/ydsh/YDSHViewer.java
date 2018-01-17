package cn.edu.nuaa.cs.gui.ydsh;

import cn.edu.nuaa.cs.io.FileHelper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by 85492 on 2017/3/10.
 */
public class YDSHViewer extends JPanel{
    public static SimpleDateFormat sdf_day = new SimpleDateFormat("yy-mm-dd");
    public static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    public JButton playButton,backwordButton,forwardButton;
    public JPanel progressTimepanel;
    public JLabel currentLabel,totalLabel;
    public JProgressBar progressBar;
    public String btnText = ">";

    public JPanel jpd,jpc;
    Entry[] entries = new Entry[17];
    public int r = 30;

    public int totalValue = 9988779;
    public int startValue = 110100;
    public int curValue = startValue;

    public int[][] relations = {
            {0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0},//1
            {1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},//2
            {0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},//3
            {0,1,1,0,0,1,0,0,1,1,0,0,0,0,0,0,0},//4
            {0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1,0},//5
            {0,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0},//6
            {0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0},//7
            {0,0,0,0,1,0,1,0,1,1,0,0,0,0,0,0,0},//8
            {0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0},//9
            {0,0,0,1,1,0,1,1,1,0,0,1,0,0,0,1,0},//10
            {0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0},//11
            {1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0},//12
            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1},//13
            {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},//14
            {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},//15
            {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},//16
            {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0}//17
    };
    String[] names = {"71450114","71450120","71450122","71450124","71550125",
            "71450126","71550129","71550130","71550131","71550126","71550103",
            "71450104","71550105","71450106","71550108","71550122","71550110"};

    public YDSHViewer() {
        setLayout(new BorderLayout());
        jpd = createEntriesDataPanel();
        jpc = createCtrlSlider();
        add(jpd,BorderLayout.CENTER);
        add(jpc,BorderLayout.SOUTH);
    }
    public JPanel createEntriesDataPanel(){
        JPanel jp = new GroupJPanel();
        jp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < entries.length; i++) {
                    if(entries[i].x<=e.getX() && entries[i].y<=e.getY()
                            && entries[i].x+r>=e.getX()
                            && entries[i].y+r>=e.getY()){
                        entries[i].showDetails();
                        break;
                    }
                }
            }
        });
        return jp;
    }
    public JPanel createCtrlSlider(){
        JPanel jp = new JPanel();
        jp.setBackground(Color.GRAY);

        backwordButton = new JButton("<<");
        backwordButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                MyMain.jumpTo((float) ((progressBar.getPercentComplete() * progressBar.getWidth() - 5)
//                        / progressBar.getWidth()));
                System.out.println("<<");
            }
        });
        forwardButton = new JButton(">>");
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                MyMain.jumpTo((float) (((progressBar.getPercentComplete() * progressBar.getWidth() + 15))
//                        / progressBar.getWidth()));
                System.out.println(">>");
            }
        });
        playButton = new JButton(">");
        playButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (playButton.getText() == ">") {
                    play();
                    btnText = "||";
                    playButton.setText(btnText);
                } else {
//                    pause();
                    btnText = ">";
                    playButton.setText(btnText);
                }
                System.out.println(">");
            }
        });
        jp.add(backwordButton);
        jp.add(playButton);
        jp.add(forwardButton);

        progressTimepanel = new JPanel();
        jp.add(progressTimepanel);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(400,15));

        currentLabel = new JLabel("00:00");
        progressTimepanel.add(currentLabel);

        progressTimepanel.add(progressBar);

        totalLabel = new JLabel(sdf.format(new Date(totalValue)));
        progressTimepanel.add(totalLabel);

        return jp;
    }

    public void play(){
        new Thread(){
            public void run(){
                for (int i = curValue; i < totalValue; i++) {
                    curValue = i;
                    progressBar.setValue(i/((totalValue-110100)/100));
                    currentLabel.setText(sdf.format(new Date(i)));
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                btnText = ">";
                playButton.setText(btnText);
                curValue = startValue;
            }
        }.start();
    }

    public void pause(){

    }

    class GroupJPanel extends JPanel implements MouseListener {
        public GroupJPanel(){
            super();
            //group01
            entries[0] = new Entry(1,670,320,r,r);
            entries[1] = new Entry(2,600,400,r,r);
            entries[2] = new Entry(3,560,100,r,r);
            entries[3] = new Entry(4,480,250,r,r);
            entries[4] = new Entry(5,65, 250,r,r);
            entries[5] = new Entry(6,540,380,r,r);
            entries[6] = new Entry(7,130,300,r,r);
            entries[7] = new Entry(8,140,380,r,r);
            entries[8] = new Entry(9,270,310,r,r);
            entries[9] = new Entry(10,250,100,r,r);
            //group02
            entries[10] = new Entry(11,200,400,r,r);
            entries[11] = new Entry(12,600,200,r,r);
            entries[12] = new Entry(13,110,90, r,r);
            entries[13] = new Entry(14,640,80, r,r);
            entries[14] = new Entry(15,260,370,r,r);
            entries[15] = new Entry(16,180,100,r,r);
            entries[16] = new Entry(17,80, 160,r,r);
        }
        public void paint(Graphics g){
            super.paint(g);
            drawGroup(g);
            drawRelations(g);
            drawEntries(g);
        }
        public void drawGroup(Graphics g){
            g.drawOval(50,50,300,400);
            g.setFont(new Font("Tahoma", Font.BOLD, 15));
            g.drawString("Group 1",170,70);

            g.drawOval(450,50,300,400);
            g.setFont(new Font("Tahoma", Font.BOLD, 15));
            g.drawString("Group 2",570,70);
        }
        public void drawEntries(Graphics g){
            for (int i = 0; i < 17; i++) {
                entries[i].paintEntry(g);
            }
        }
        public void drawRelations(Graphics g){
            for (int i = 0; i < 17; i++) {
                for (int j = i; j < 17; j++) {
                    if(relations[i][j]==1){
                        drawRelation(g,entries[i],entries[j]);
                    }
                }
            }
        }
        public void drawRelation(Graphics g, Entry e1, Entry e2){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawLine(e1.x+e1.width/2, e1.y+e1.length/2,
                         e2.x+e2.width/2, e2.y+e2.length/2);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getX()+" , "+e.getY());
        }
        @Override
        public void mousePressed(MouseEvent e) {

        }
        @Override
        public void mouseReleased(MouseEvent e) {

        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class Entry {
        int id;

        int x, y;
        int width, length;

        String name;
        boolean flag = true;

        public Entry(int id, int x, int y, int width, int length) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.length = length;
        }

        public void paintEntry(Graphics g) {
            Color c = g.getColor();
            if (flag)
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.RED);
            g.fillOval(x, y, width, length);

            g.setColor(Color.WHITE);
            g.drawString("" + id, x + width / 2 - 5, y + length / 2 + 5);
            g.setColor(c);
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
        public void setName(String name) {
            this.name = name;
        }

        public void showDetails() {
            JFrame jf = new JFrame(id+" - "+names[id-1] + " 运动手环数据");
            jf.setBounds(300, 50, 1000, 800);
            jf.add(DetailsPanel(), BorderLayout.CENTER);
            jf.setVisible(true);
        }
        public JPanel DetailsPanel() {
            JPanel jp = new JPanel(new BorderLayout());
            JScrollPane jScrollPane = new JScrollPane();
            //result
            if(true){
                jScrollPane.setViewportView(createChart(names[id-1]));
            } else {
                System.out.println("手环数据存在错误！");
            }
            jp.add(jScrollPane, BorderLayout.CENTER);
            return jp;
        }
        public JPanel createChart(String fileName) {
            File file_state = new File(YDSHWindow.dirPath+"//4_state//"+fileName+".csv");
            File file_qq = new File(YDSHWindow.dirPath+"//5_historySteps_qq//"+fileName+".csv");
            File file_app = new File(YDSHWindow.dirPath+"//6_historySteps_app//"+fileName+".csv");
            File file_wristbands = new File(YDSHWindow.dirPath+"//7_historySteps_wristbands//"+fileName+".csv");

            ArrayList<String> contents_state = FileHelper.readFileByLine(file_state);
            ArrayList<String> contents_qq = FileHelper.readFileByLine(file_qq);
            ArrayList<String> contents_app = FileHelper.readFileByLine(file_app);
            ArrayList<String> contents_wristbands = FileHelper.readFileByLine(file_wristbands);

            Date[] date = new Date[contents_state.size()];
            int[] state1 = new int[contents_state.size()];
            int[] state2 = new int[contents_state.size()];
            int[] state3 = new int[contents_state.size()];
            int[] state4 = new int[contents_state.size()];
            int[] qqSteps = new int[contents_qq.size()];
            int[] appSteps = new int[contents_app.size()];
            int[] wristbandSteps = new int[contents_wristbands.size()];

            for (int i = 0; i < contents_state.size(); i++) {
                String[] strs_state = contents_state.get(i).trim().split(",");
                try {
                    date[i] = sdf_day.parse(strs_state[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                state1[i] = Double.valueOf(strs_state[1]).intValue();
                state2[i] = Double.valueOf(strs_state[2]).intValue();
                state3[i] = Double.valueOf(strs_state[3]).intValue();
                state4[i] = Double.valueOf(strs_state[4]).intValue();
            }
            for (int i = 0; i < contents_qq.size(); i++) {
                String[] strs_qq = contents_qq.get(i).trim().split(",");
                qqSteps[i] = Double.valueOf(strs_qq[1]).intValue();
            }
            for (int i = 0; i < contents_app.size(); i++) {
                String[] strs_app = contents_app.get(i).trim().split(",");
                appSteps[i] = Double.valueOf(strs_app[1]).intValue();
            }
            for (int i = 0; i < contents_wristbands.size(); i++) {
                String[] strs_wristbands = contents_wristbands.get(i).trim().split(",");
                wristbandSteps[i] = Double.valueOf(strs_wristbands[1]).intValue();
            }

            //create bar chart
            ChartPanel cpl;
            CategoryDataset dataset;
            DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

            for (int i = 0; i < contents_state.size(); i++) {
//            for (int i = 0; i < 5; i++) {
                dataset1.addValue(qqSteps[i], "QQ", sdf_day.format(date[i]));
                dataset1.addValue(appSteps[i], "APP", sdf_day.format(date[i]));
                dataset1.addValue(wristbandSteps[i], "WristBands", sdf_day.format(date[i]));
            }
            dataset = dataset1;

            JFreeChart chart = ChartFactory.createBarChart3D(
                    names[id-1]+" 运动数据显示",              // 图表标题
                    "数据来源",                   // 目录轴的显示标签
                    "步数",                         //  数值轴的显示标签
                    dataset,                                      // 数据集
                    PlotOrientation.VERTICAL,                     // 图表方向：水平、垂直
                    true,                                 // 是否显示图例(对于简单的柱状图必须是false)
                    false,                               // 是否生成工具
                    false                                    // 是否生成URL链接
            );

            //从这里开始
            CategoryPlot plot=chart.getCategoryPlot();              //获取图表区域对象
            CategoryAxis domainAxis=plot.getDomainAxis();            //水平底部列表
            domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
            domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));       //垂直标题
            ValueAxis rangeAxis=plot.getRangeAxis();                                    //获取柱状
            rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
            chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
            chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));          //设置标题字体
            //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题
            cpl = new ChartPanel(chart,true);
            return cpl;
        }
    }
}
