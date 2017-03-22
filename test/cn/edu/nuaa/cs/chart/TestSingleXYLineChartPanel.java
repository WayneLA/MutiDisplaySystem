package cn.edu.nuaa.cs.chart;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 85492 on 2017/3/8.
 */
public class TestSingleXYLineChartPanel {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame jframe = new JFrame();
                    jframe.setBounds(100,50,800,600);
                    jframe.setLayout(new BorderLayout());
                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    int[] x = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
                    double[] y = {2,1,4,3,7,5,7,8,9,5,4,3,5,7,15,1,1,1,2,2,3,3,3,3,3,3,3};
                    ChartPanel cp =
                            new SingleXYLineChartPanel("测试","时间","数值",
                                    0,10,0,10,10,
                                    "数据",x,y).getChartPanel();
                    jframe.getContentPane().add(cp,BorderLayout.CENTER);
                    jframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
