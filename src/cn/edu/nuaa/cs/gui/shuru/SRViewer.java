package cn.edu.nuaa.cs.gui.shuru;

import cn.edu.nuaa.cs.chart.SingleXYLineChartPanel;
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
public class SRViewer extends JPanel implements Runnable{
    public static String faceLabPath = MainWindow.rootPath_Data +"\\SR\\";
    public static String curFileName = null;
    private int pulse = 500;
    public static SingleXYLineChartPanel schart;
    public static int[] status;
    public static int index = 0;

    public SRViewer() {
        setLayout(new BorderLayout());

        JPanel jp = new JPanel(new FlowLayout());
        schart = new SingleXYLineChartPanel(
                "输入操作状态", "时间", "状态",
                0, 10, -1, 2,
                10,"数值");
        ChartPanel chart = schart.getChartPanel();
        jp.add(chart);
        JScrollPane jsp = new JScrollPane(jp);
        add(jsp);
        setVisible(true);

        new Thread(this).start();
    }

    public void run(){
        while(true){
            if(curFileName!=null){
                System.out.println(">>>    "+curFileName);
                getContent();
                for (int i = 0; i < status.length; i++) {
                    schart.seriesKey.add(index++, status[i]);
                    try {
                        Thread.sleep(pulse);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        File file = new File(SRWindow.dirPath+curFileName);
        ArrayList<String> contents = FileHelper.readFileByLine(file);

        status = new int[contents.size()];
        for(int i=0;i<contents.size();i++) {
            String strs[] = contents.get(i).trim().split(" ");
            status[i] = Integer.valueOf(strs[strs.length - 1]);
        }

        if (file.isFile() && file.exists()) {
            file.delete();
        }
        curFileName = null;
    }

    public static void main(String[] args){
        new SRViewer().getContent();
    }
}
