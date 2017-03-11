package cn.edu.nuaa.cs.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

/**
 * Created by 85492 on 2017/3/7.
 */
public class DoubleXYLineChartPanel implements Runnable{
    private ChartPanel chartPanel;
    private String title, xAxisLabel, yAxisLabel;
    private double lowerx, upperx, lowery, uppery, autoRange;
    private int width = 400, height = 290;

    public XYSeries seriesKey1,seriesKey2;
    public int[] valuesx;
    public double[] valuesy1;
    public double[] valuesy2;

    private Thread thread;
    private int pulse = 10;

    public DoubleXYLineChartPanel(String title, String xAxisLabel, String yAxisLabel,
                                  XYDataset dataset, int width,int height) {
        JFreeChart jfreechart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel,
                dataset, PlotOrientation.VERTICAL,true, true, false);

        this.chartPanel = new ChartPanel(jfreechart);
        this.chartPanel.setPreferredSize(new Dimension(width,height));

        configFont(jfreechart,12);
    }

    public DoubleXYLineChartPanel(String title, String xAxisLabel, String yAxisLabel,
                                  double lowerx, double upperx, double lowery, double uppery,
                                  double autoRange, String key1, String key2) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.lowerx = lowerx;
        this.upperx = upperx;
        this.lowery = lowery;
        this.uppery = uppery;
        this.autoRange = autoRange;
        this.seriesKey1 = new XYSeries(key1);
        this.seriesKey2 = new XYSeries(key2);

        XYSeriesCollection xyCollection01 = new XYSeriesCollection(this.seriesKey1);
        XYSeriesCollection xyCollection02 = new XYSeriesCollection(this.seriesKey2);
        this.chartPanel = new ChartPanel(createChart(xyCollection01,xyCollection02));
        this.chartPanel.setPreferredSize(new Dimension(this.width,this.height));

//        this.thread = new Thread(this);
//        startThread();
    }

    public DoubleXYLineChartPanel(String title, String xAxisLabel, String yAxisLabel,
                             double lowerx, double upperx, double lowery, double uppery,
                             double autoRange, String key1, String key2,
                             int[] valuesx, double[] valuesy1, double[] valuesy2) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.lowerx = lowerx;
        this.upperx = upperx;
        this.lowery = lowery;
        this.uppery = uppery;
        this.autoRange = autoRange;
        this.seriesKey1 = new XYSeries(key1);
        this.seriesKey2 = new XYSeries(key2);
        this.valuesx = valuesx;
        this.valuesy1 = valuesy1;
        this.valuesy2 = valuesy2;

        XYSeriesCollection xyCollection01 = new XYSeriesCollection(this.seriesKey1);
        XYSeriesCollection xyCollection02 = new XYSeriesCollection(this.seriesKey2);
        this.chartPanel = new ChartPanel(createChart(xyCollection01,xyCollection02));
        this.chartPanel.setPreferredSize(new Dimension(this.width,this.height));

        this.thread = new Thread(this);
        startThread();
    }

    private JFreeChart createChart(XYDataset xyCollection1, XYDataset xyCollection2) {
        JFreeChart jfreechart = ChartFactory.createXYLineChart(
                this.title, this.xAxisLabel, this.yAxisLabel,
                xyCollection1, PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot xyplot = jfreechart.getXYPlot();
        // 横坐标设定
        ValueAxis valueaxis = xyplot.getDomainAxis();
        valueaxis.setRange(this.lowerx,this.upperx);
        valueaxis.setAutoRange(true);
        valueaxis.setFixedAutoRange(this.autoRange);
        // 纵坐标设定
        valueaxis = xyplot.getRangeAxis();
        valueaxis.setRange(this.lowery, this.uppery);

        xyplot.setDataset(1, xyCollection2);
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

        XYPlot plot = chart.getXYPlot();// 图形的绘制结构对象
        // 图片标题
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
        // 底部
        chart.getLegend().setItemFont(kFont);
        //X轴
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(xFont);// 轴标题
        domainAxis.setTickLabelFont(xFont);// 轴数值
//        domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色
        //Y轴
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(yFont);
//        rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色
        rangeAxis.setTickLabelFont(yFont);
        //线条颜色
        // 坐标轴1--曲线颜色
        StandardXYItemRenderer renderer0 = new StandardXYItemRenderer();
        renderer0.setSeriesPaint(0, Color.BLUE);
        renderer0.setLegendTextPaint(0, Color.BLUE);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer1 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.RED);
        renderer1.setLegendTextPaint(0, Color.RED);// 设置对应图例字体颜色

        plot.setRenderer(0, renderer0);
        plot.setRenderer(1, renderer1);
    }

    public void startThread() {
        this.thread.start();
    }
    public void run() {
        while (true) {
            if(this.valuesx!=null&&this.valuesy1!=null&&this.valuesy2!=null){
                for(int i=0;i<this.valuesx.length;i++) {
                    try {
                        seriesKey1.add(this.valuesx[i], this.valuesy1[i]);
                        seriesKey2.add(this.valuesx[i], this.valuesy2[i]);
                        Thread.sleep(pulse);
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
    public void setValuesy1(double[] valuesy1) {
        this.valuesy1 = valuesy1;
    }
    public void setValuesy2(double[] valuesy2) {
        this.valuesy2 = valuesy2;
    }

    public int[] getValuesx() {
        return valuesx;
    }
    public double[] getValuesy1() {
        return valuesy1;
    }
    public double[] getValuesy2() {
        return valuesy2;
    }

    public void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}






