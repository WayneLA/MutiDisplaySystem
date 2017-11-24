package cn.edu.nuaa.cs.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

/**
 * Created by 85492 on 2017/3/7.
 */
public class SingleXYLineChartPanel01 implements Runnable {
    private ChartPanel chartPanel;
    private String title, xAxisLabel, yAxisLabel;
    private double lowerx, upperx, lowery, uppery, autoRange;
    private int width = 400, height = 290;

    public XYSeries seriesKey;
    public int[] valuesx;
    public double[] valuesy;

    private Thread thread;
    private int pulse = 10;

    public SingleXYLineChartPanel01(
            String title, String xAxisLabel, String yAxisLabel,
            XYDataset dataset, int width, int height){

        JFreeChart jfreechart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel,
                dataset, PlotOrientation.VERTICAL,true, true, false);
        this.chartPanel = new ChartPanel(jfreechart);
        this.chartPanel.setPreferredSize(new Dimension(width,height));
        configFont(jfreechart,12);
    }

    public SingleXYLineChartPanel01(String title, String xAxisLabel, String yAxisLabel,
                                    double lowerx, double upperx, double lowery, double uppery,
                                    double autoRange, String key) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.lowerx = lowerx;
        this.upperx = upperx;
        this.lowery = lowery;
        this.uppery = uppery;
        this.autoRange = autoRange;
        this.seriesKey = new XYSeries(key);

        XYSeriesCollection xyCollection = new XYSeriesCollection(this.seriesKey);
        this.chartPanel = new ChartPanel(createChart(xyCollection));
        this.chartPanel.setPreferredSize(new Dimension(this.width,this.height));

//        this.thread = new Thread(this);
//        startThread();
    }

    public SingleXYLineChartPanel01(String title, String xAxisLabel, String yAxisLabel,
                                    double lowerx, double upperx, double lowery, double uppery,
                                    double autoRange, String key, int[] valuesx, double[] valuesy) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.lowerx = lowerx;
        this.upperx = upperx;
        this.lowery = lowery;
        this.uppery = uppery;
        this.autoRange = autoRange;
        this.seriesKey = new XYSeries(key);
        this.valuesx = valuesx;
        this.valuesy = valuesy;

        XYSeriesCollection xyCollection = new XYSeriesCollection(this.seriesKey);
        this.chartPanel = new ChartPanel(createChart(xyCollection));
        this.chartPanel.setPreferredSize(new Dimension(this.width,this.height));

        this.thread = new Thread(this);
        startThread();
    }

    private JFreeChart createChart(XYDataset xyCollection) {
        JFreeChart jfreechart = ChartFactory.createXYLineChart(this.title, this.xAxisLabel, this.yAxisLabel,
                xyCollection, PlotOrientation.VERTICAL,true,true,false);

        XYPlot xyplot = jfreechart.getXYPlot();
        //x
        ValueAxis valueaxis = xyplot.getDomainAxis();
        valueaxis.setRange(this.lowerx, this.upperx);
        valueaxis.setAutoRange(true);
        valueaxis.setFixedAutoRange(this.autoRange);
        //y
        valueaxis = xyplot.getRangeAxis();
        valueaxis.setRange(this.lowery, this.uppery);

        configFont(jfreechart,14);

        return jfreechart;
    }

    private void configFont(JFreeChart chart,int size) {
        Font xFont = new Font("宋体", Font.PLAIN, size);
        Font yFont = new Font("宋体", Font.PLAIN, size);
        Font kFont = new Font("宋体", Font.PLAIN, size);
        Font titleFont = new Font("宋体", Font.BOLD, size+4);

        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
        chart.getLegend().setItemFont(kFont);

        XYPlot plot = chart.getXYPlot();
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(xFont);
        domainAxis.setTickLabelFont(xFont);
//        domainAxis.setTickLabelPaint(Color.BLUE);

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(yFont);
        rangeAxis.setTickLabelFont(yFont);
//        rangeAxis.setLabelPaint(Color.BLUE);
    }

    public void startThread() {
        this.thread.start();
    }
    public void run() {
        while (true) {
            if(this.valuesx!=null&&this.valuesy!=null){
                for(int i=0;i<this.valuesy.length;i++) {
                    try {
                        seriesKey.add(this.valuesx[i], this.valuesy[i]);
                        Thread.sleep(this.pulse);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setValuesx(int[] valuesx) {
        this.valuesx = valuesx;
    }
    public void setValuesy(double[] valuesy) {
        this.valuesy = valuesy;
    }

    public int[] getValuesx() {
        return valuesx;
    }
    public double[] getValuesy() {
        return valuesy;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
    public void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
}
