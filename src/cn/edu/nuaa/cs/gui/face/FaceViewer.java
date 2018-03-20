package cn.edu.nuaa.cs.gui.face;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import cn.edu.nuaa.cs.chart.DoubleXYLineChartPanel;
import cn.edu.nuaa.cs.chart.DoubleXYLineChartPanel01;
import cn.edu.nuaa.cs.chart.SingleXYLineChartPanel;
import cn.edu.nuaa.cs.gui.warning.WarningDialog;
import cn.edu.nuaa.cs.io.FileHelper;
import org.jfree.chart.ChartPanel;

public class FaceViewer extends JPanel{
	public static String curFileName = null;

	public static int[] FRAME_NUM;								//帧数
	public static long[] GMT_S;									//时间
	public static double[] RIGHT_CLOS_CONF, LEFT_CLOS_CONF;		//开闭精度
	public static double[] PUPIL_R_DIAM, PUPIL_L_DIAM;			//瞳孔直径
	public static double[] BLINKING;							//眨眼次数
	public static double[] BLINK_FREQ;							//眨眼频率

	public static double pupil_max = 0.006;						//瞳孔直径最大值
	public static double pupil_min = 0.004;						//瞳孔直径最小值
	public static double kbjd_max = 1;							//开闭精度最大值
	public static double kbjd_min = 0;							//开闭精度最小值
	private static int pulse = 10, timeindex=0;

	public static DoubleXYLineChartPanel01 dChart_PUPIL, dChart_KBJD;
	public static SingleXYLineChartPanel sChart_ZYCS;

	public FaceViewer() {
		setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("瞳孔直径", createTKZJJSPane());
		tabbedPane.add("开闭精度", createKBJDJSPane());
		tabbedPane.add("眨眼次数", createZYCSJSPane());
		add(tabbedPane, BorderLayout.CENTER);
	}

	public JScrollPane createTKZJJSPane(){
		JPanel jp = new JPanel(new FlowLayout());
		dChart_PUPIL = new DoubleXYLineChartPanel01(
					"左右眼瞳孔直径变化曲线", "时间", "直径/米",
					0, 200, -0.0005, 0.0125,
					100,"左眼","右眼",0.006,0.004);
		ChartPanel chart = dChart_PUPIL.getChartPanel();
		jp.add(chart);
		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}
	public JScrollPane createKBJDJSPane(){
		JPanel jp = new JPanel(new FlowLayout());
		dChart_KBJD = new DoubleXYLineChartPanel01(
				"左右眼开闭精度变化曲线", "时间", "直径/米",
				0, 100, -0.1, 1.1,
				100,"左眼","右眼",1,0);
		ChartPanel chart = dChart_KBJD.getChartPanel();
		jp.add(chart);
		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}
	public JScrollPane createZYCSJSPane(){
		JPanel jp = new JPanel(new FlowLayout());
		sChart_ZYCS = new SingleXYLineChartPanel(
				"眨眼次数变化曲线", "时间", "数值",
				0, 200, -0.1, 1.1,
				100,"数值");
		ChartPanel chart = sChart_ZYCS.getChartPanel();
		jp.add(chart);
		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}

	public static void getContent(String curFileName){
		System.out.println(">>>    "+curFileName);
		File file = new File(FaceWindow.faceLabPath+curFileName);
		ArrayList<String> contents = FileHelper.readFileByLine(file);

		FRAME_NUM 		= new int[contents.size()-1];
		GMT_S 			= new long[contents.size()-1];
		RIGHT_CLOS_CONF = new double[contents.size()-1];
		LEFT_CLOS_CONF 	= new double[contents.size()-1];
		BLINKING 		= new double[contents.size()-1];
		BLINK_FREQ 		= new double[contents.size()-1];
		PUPIL_R_DIAM 	= new double[contents.size()-1];
		PUPIL_L_DIAM 	= new double[contents.size()-1];

		for (int i = 1; i < contents.size()-1; i++) {
			String[] tuple = contents.get(i).trim().split("\\s+");
			FRAME_NUM[i-1] 			= timeindex++;
			GMT_S[i-1] 				= Long.valueOf(tuple[1]);
			RIGHT_CLOS_CONF[i-1] 	= Double.valueOf(tuple[2]);
			LEFT_CLOS_CONF[i-1] 	= Double.valueOf(tuple[3]);
			BLINKING[i-1] 			= Double.valueOf(tuple[4]);
			BLINK_FREQ[i-1] 		= Double.valueOf(tuple[5]);
			PUPIL_R_DIAM[i-1] 		= Double.valueOf(tuple[6]);
			PUPIL_L_DIAM[i-1] 		= Double.valueOf(tuple[7]);
		}
		dChart_PUPIL.setValuesx(FRAME_NUM);
		dChart_PUPIL.setValuesy1(PUPIL_L_DIAM);
		dChart_PUPIL.setValuesy2(PUPIL_R_DIAM);

		dChart_KBJD.setValuesx(FRAME_NUM);
		dChart_KBJD.setValuesy1(LEFT_CLOS_CONF);
		dChart_KBJD.setValuesy2(RIGHT_CLOS_CONF);

		sChart_ZYCS.setValuesx(FRAME_NUM);
		sChart_ZYCS.setValuesy(BLINKING);

//		if (file.isFile() && file.exists()) {
//			file.delete();
//		}
	}

	public static class FaceViewerRunnable implements Runnable{
		public static volatile boolean showflag = true;
		@Override
		public void run() {
			while(true){
				if(curFileName!=null){

					getContent(curFileName);

					for (int i = 0; i < FRAME_NUM.length;) {
						if(showflag){
							dChart_PUPIL.seriesKey1.add(dChart_PUPIL.valuesx[i], dChart_PUPIL.valuesy1[i]);
							dChart_PUPIL.seriesKey2.add(dChart_PUPIL.valuesx[i], dChart_PUPIL.valuesy2[i]);
							dChart_PUPIL.seriesKey01.add(dChart_PUPIL.valuesx[i], pupil_max);
							dChart_PUPIL.seriesKey02.add(dChart_PUPIL.valuesx[i], pupil_min);
							WarningDialog.WaningDialog_fb(dChart_PUPIL.valuesy1[i], pupil_max, pupil_min);

							dChart_KBJD.seriesKey1.add(dChart_KBJD.valuesx[i], dChart_KBJD.valuesy1[i]);
							dChart_KBJD.seriesKey2.add(dChart_KBJD.valuesx[i], dChart_KBJD.valuesy2[i]);
							dChart_KBJD.seriesKey01.add(dChart_KBJD.valuesx[i], kbjd_max);
							dChart_KBJD.seriesKey02.add(dChart_KBJD.valuesx[i], kbjd_min);

							sChart_ZYCS.seriesKey.add(sChart_ZYCS.valuesx[i], sChart_ZYCS.valuesy[i]);
							i++;
							try {
								Thread.sleep(pulse);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					curFileName = null;

					continue;

				}else{
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void setShowflag(boolean showflag) {
			this.showflag = showflag;
		}
	}
}
