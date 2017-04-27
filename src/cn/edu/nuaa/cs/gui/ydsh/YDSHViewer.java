package cn.edu.nuaa.cs.gui.ydsh;

import cn.edu.nuaa.cs.chart.DoubleXYLineChartPanel;
import cn.edu.nuaa.cs.io.FileHelper;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 85492 on 2017/3/10.
 */
public class YDSHViewer extends JPanel{

    DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ArrayList dayList = new ArrayList();
    ArrayList resList = new ArrayList();
    ArrayList<Date> times = new ArrayList<Date>();
    ArrayList<Integer> steps = new ArrayList<Integer>();

    public YDSHViewer() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
        JScrollPane jScrollPane = new JScrollPane();

        //result
        File[] resultfileList = new File(YDSHWindow.dirPath+"//result").listFiles();
        File[] datafileList = new File(YDSHWindow.dirPath+"//data").listFiles();

        if(resultfileList.length==datafileList.length){
            for (int i = 0; i < resultfileList.length; i++) {
                if(resultfileList[i].isFile() && datafileList[i].isFile() &&
                        resultfileList[i].getName().split("\\.")[0].equals(
                                datafileList[i].getName().split("\\.")[0])){
                    XYSeries xyseries1 = new XYSeries("正常");
                    XYSeries xyseries2 = new XYSeries("异常");

                    ArrayList<String> result = FileHelper.readFileByLine(resultfileList[i]);
                    ArrayList<String> data = FileHelper.readFileByLine(datafileList[i]);

                    int n=0;
                    for (int j = 0; j < result.size(); j++) {
                        String[] temp = result.get(j).split(",");
                        int[] flag = new int[4];
                        String dayStr = temp[0];
                        flag[0] = Integer.valueOf(temp[1].split("\\.")[0]);
                        flag[1] = Integer.valueOf(temp[2].split("\\.")[0]);
                        flag[2] = Integer.valueOf(temp[3].split("\\.")[0]);
                        flag[3] = Integer.valueOf(temp[4].split("\\.")[0]);

                        for (int k = 0; k < data.size(); k++) {
                            String[] tmp = data.get(k).split(",");
                            if(tmp[0].contains(dayStr)){
                                int m = isInterval(tmp[0]);
                                if(m>-1 && m<4) {
                                    if (flag[m] == 1) {
                                        xyseries1.add(n++, Integer.valueOf(tmp[1]));
                                    } else {
                                        xyseries2.add(n++, Integer.valueOf(tmp[1]));
                                    }
                                }
                            }
                        }
                    }

                    String name = resultfileList[i].getName().split("\\.")[0];
                    XYDataset dataset = createXYDataset(xyseries1, xyseries2);
                    DoubleXYLineChartPanel chart = new DoubleXYLineChartPanel(
                            name+" - 运动手环数据","","步数",
                            dataset,350,200);

                    tabbedPane.add(name, chart.getChartPanel());
                }
            }
        }else{
            System.out.println("手环数据存在错误！");
        }

        jScrollPane.setViewportView(tabbedPane);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private XYDataset createXYDataset(XYSeries xyseries1, XYSeries xyseries2) {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xyseries1);
        xySeriesCollection.addSeries(xyseries2);
        return xySeriesCollection;
    }

    private int isInterval(String dateStr){
        DateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        try {
            Date time1 = sdf2.parse("07:00:00");
            Date time2 = sdf2.parse("12:00:00");
            Date time3 = sdf2.parse("14:00:00");
            Date time4 = sdf2.parse("18:00:00");
            Date time5 = sdf2.parse("22:00:00");

            Date t = sdf2.parse(dateStr.split(" ")[1]);

            if(t.compareTo(time1)>0 && t.compareTo(time2)<0){
                return 0;
            }
            if(t.compareTo(time2)>0 && t.compareTo(time3)<0){
                return 1;
            }
            if(t.compareTo(time3)>0 && t.compareTo(time4)<0){
                return 2;
            }
            if(t.compareTo(time4)>0 && t.compareTo(time5)<0){
                return 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
