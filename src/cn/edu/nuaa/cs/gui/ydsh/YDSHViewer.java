package cn.edu.nuaa.cs.gui.ydsh;

import cn.edu.nuaa.cs.chart.DoubleXYLineChartPanel;
import cn.edu.nuaa.cs.io.FileHelper;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by 85492 on 2017/3/10.
 */
public class YDSHViewer extends JPanel{
    public YDSHViewer() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
        JScrollPane jScrollPane = new JScrollPane();

        File[] fileList = new File(YDSHWindow.dirPath).listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()){
                ArrayList<String> contents = FileHelper.readFileByLine(fileList[i]);
                ArrayList<Integer> steps = new ArrayList<Integer>();
                for (int j = 0; j < contents.size(); j++) {
                    String[] dayContent = contents.get(j).split(" ");
                    for (int k = 0; k < dayContent.length; k++) {
                        steps.add(Integer.valueOf(dayContent[k]));
                    }
                }

                String name = fileList[i].getName().split("\\.")[0];
                XYDataset dataset = createXYDataset(steps);
                DoubleXYLineChartPanel chart = new DoubleXYLineChartPanel(
                        name+"步数图","","步数",dataset,350,200);

                tabbedPane.add(name, chart.getChartPanel());
            }else{
                continue;
            }
        }

        jScrollPane.setViewportView(tabbedPane);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private XYDataset createXYDataset(ArrayList<Integer> steps) {
        XYSeries xyseries1 = new XYSeries("正常");
        XYSeries xyseries2 = new XYSeries("异常");
        for (int i = 0; i < steps.size(); i++) {
            xyseries1.add(i,steps.get(i));
            if(i>1000&&i<2000){
                xyseries2.add(i,steps.get(i)+1000);
            }
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xyseries1);
        xySeriesCollection.addSeries(xyseries2);
        return xySeriesCollection;
    }
}
