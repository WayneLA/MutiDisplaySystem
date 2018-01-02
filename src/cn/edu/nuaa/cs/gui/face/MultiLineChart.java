package cn.edu.nuaa.cs.gui.face;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

/**
 * Created by 85492 on 2017/12/17.
 */
public class MultiLineChart implements Runnable{
    private ChartPanel chartPanel;

    private String title = "生物状态监测显示";
    private String xAxisLabel = "时间";
    private String yAxisLabel = "数值";
    private double lowerx = 0;
    private double upperx = 100;
    private double lowery = -100;
    private double uppery = 100;
    private double autoRange = 100;

    public XYSeries seriesKey1 = new XYSeries("心电");
    public XYSeries seriesKey2 = new XYSeries("脑电");
    public XYSeries seriesKey3 = new XYSeries("r波");
    public XYSeries seriesKey4 = new XYSeries("β波");
    public XYSeries seriesKey5 = new XYSeries("α波");
    public XYSeries seriesKey6 = new XYSeries("θ波");
    public XYSeries seriesKey7 = new XYSeries("δ波");


    public int[] valuesx;
    public double[] valuesy1;
    public double[] valuesy2;
    public double[] valuesy3;
    public double[] valuesy4;
    public double[] valuesy5;
    public double[] valuesy6;
    public double[] valuesy7;

    private int width = 400, height = 300;
    private Thread thread;
    private int pulse = 10;

    public MultiLineChart(){
        XYSeriesCollection xyCollection1 = new XYSeriesCollection(this.seriesKey1);
        XYSeriesCollection xyCollection2 = new XYSeriesCollection(this.seriesKey2);
        XYSeriesCollection xyCollection3 = new XYSeriesCollection(this.seriesKey3);
        XYSeriesCollection xyCollection4 = new XYSeriesCollection(this.seriesKey4);
        XYSeriesCollection xyCollection5 = new XYSeriesCollection(this.seriesKey5);
        XYSeriesCollection xyCollection6 = new XYSeriesCollection(this.seriesKey6);
        XYSeriesCollection xyCollection7 = new XYSeriesCollection(this.seriesKey7);

        JFreeChart jfreechart = ChartFactory.createXYLineChart(
                this.title, this.xAxisLabel, this.yAxisLabel, xyCollection1,
                PlotOrientation.VERTICAL, true, true, false);

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
        xyplot.setDataset(2, xyCollection3);
        xyplot.setDataset(3, xyCollection4);
        xyplot.setDataset(4, xyCollection5);
        xyplot.setDataset(5, xyCollection6);
        xyplot.setDataset(6, xyCollection7);

        configFont(jfreechart,14);

        this.chartPanel = new ChartPanel(jfreechart);
        this.chartPanel.setPreferredSize(new Dimension(this.width,this.height));
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
        //坐标轴1--曲线颜色
        StandardXYItemRenderer renderer0 = new StandardXYItemRenderer();
        renderer0.setSeriesPaint(0, Color.BLUE);
        renderer0.setLegendTextPaint(0, Color.BLUE);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer1 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.GREEN);
        renderer1.setLegendTextPaint(0, Color.GREEN);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.GREEN);
        renderer1.setLegendTextPaint(0, Color.GREEN);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer3 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.CYAN);
        renderer1.setLegendTextPaint(0, Color.CYAN);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer4 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.MAGENTA);
        renderer1.setLegendTextPaint(0, Color.MAGENTA);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer5 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.GRAY);
        renderer1.setLegendTextPaint(0, Color.GRAY);// 设置对应图例字体颜色

        StandardXYItemRenderer renderer6 = new StandardXYItemRenderer();
        renderer1.setSeriesPaint(0, Color.ORANGE);
        renderer1.setLegendTextPaint(0, Color.ORANGE);// 设置对应图例字体颜色

        plot.setRenderer(0, renderer0);
        plot.setRenderer(1, renderer1);
        plot.setRenderer(2, renderer2);
        plot.setRenderer(3, renderer3);
        plot.setRenderer(4, renderer4);
        plot.setRenderer(5, renderer5);
        plot.setRenderer(6, renderer6);
    }

    public void startThread() {
        this.thread.start();
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public void run() {
        while (true) {
            if(this.valuesx!=null
                    && this.valuesy1!=null && this.valuesy2!=null
                    && this.valuesy3!=null && this.valuesy4!=null
                    && this.valuesy5!=null && this.valuesy6!=null
                    && this.valuesy7!=null){
                for(int i=0;i<this.valuesx.length;i++) {
                    try {
                        seriesKey1.add(this.valuesx[i], this.valuesy1[i]);
                        seriesKey2.add(this.valuesx[i], this.valuesy2[i]);
                        seriesKey3.add(this.valuesx[i], this.valuesy3[i]);
                        seriesKey4.add(this.valuesx[i], this.valuesy4[i]);
                        seriesKey5.add(this.valuesx[i], this.valuesy5[i]);
                        seriesKey6.add(this.valuesx[i], this.valuesy6[i]);
                        seriesKey7.add(this.valuesx[i], this.valuesy7[i]);
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
}
