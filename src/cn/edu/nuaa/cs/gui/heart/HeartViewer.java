package cn.edu.nuaa.cs.gui.heart;

import cn.edu.nuaa.cs.gui.face.MultiLineChart;
import cn.edu.nuaa.cs.gui.main.MainWindow;
import cn.edu.nuaa.cs.io.FileHelper;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by 85492 on 2017/10/27.
 */
public class HeartViewer extends JPanel implements Runnable{
    public static String heartPath = MainWindow.rootPath_Data +"\\Heart\\";
    public static String curFileName = null;
    private int pulse = 50;
    public static String[] time;
    public static double[] data02;
    public static double[] data03;
    public static double[] data04;
    public static double[] data05;
    public static double[] data06;
    public static double[] data07;
    public static double[] data08;

    public static int index = 0;
    public static MultiLineChart mlchart;

    public HeartViewer() {
        setLayout(new BorderLayout());

        JPanel jp = new JPanel(new FlowLayout());
        mlchart = new MultiLineChart();
        ChartPanel chart = mlchart.getChartPanel();
        jp.add(chart);
        JScrollPane jsp = new JScrollPane(jp);
        add(jsp);
        setVisible(true);

        heartPath = MainWindow.rootPath_Data +"\\Heart\\";

        new Thread(this).start();
    }

    public void run(){
        while(true){
            if(curFileName!=null){
                System.out.println(">>>    "+curFileName);
                getContent();
                for (int i = 0; i < time.length; i++) {
                    mlchart.seriesKey1.add(index, data02[i]);
                    mlchart.seriesKey2.add(index, data03[i]);
                    mlchart.seriesKey3.add(index, data04[i]);
                    mlchart.seriesKey4.add(index, data05[i]);
                    mlchart.seriesKey5.add(index, data06[i]);
                    mlchart.seriesKey6.add(index, data07[i]);
                    mlchart.seriesKey7.add(index, data08[i]);
                    try {
                        Thread.sleep(pulse);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    index++;
                }
                try {
                    Thread.sleep(pulse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                continue;
            }else{
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getContent(){
        File file = new File(HeartWindow.dirPath+curFileName);
        ArrayList<String> contents = FileHelper.readFileByLine(file);

        time = new String[contents.size()];
        data02 = new double[contents.size()];
        data03 = new double[contents.size()];
        data04 = new double[contents.size()];
        data05 = new double[contents.size()];
        data06 = new double[contents.size()];
        data07 = new double[contents.size()];
        data08 = new double[contents.size()];

        for(int i=1;i<contents.size();i++) {
            String strs[] = contents.get(i).trim().split(",");

            time[i] = strs[0];
            data02[i] = Double.valueOf(strs[1]);
            data03[i] = Double.valueOf(strs[2]);
            data04[i] = Double.valueOf(strs[3]);
            data05[i] = Double.valueOf(strs[4]);
            data06[i] = Double.valueOf(strs[5]);
            data07[i] = Double.valueOf(strs[6]);
            data08[i] = Double.valueOf(strs[7]);
        }

        if (file.isFile() && file.exists()) {
            file.delete();
        }
        curFileName = null;
    }

    public static void main(String[] args){
        MainWindow.rootPath_Data = "D:\\IdeaProjects\\Data";
        new HeartWindow();
        JPanel jpl = new HeartViewer();

        JFrame jf = new JFrame();
        jf.add(jpl);
        jf.setVisible(true);
    }
}
