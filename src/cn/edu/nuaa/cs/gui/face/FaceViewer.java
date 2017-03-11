package cn.edu.nuaa.cs.gui.face;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import cn.edu.nuaa.cs.chart.DoubleXYLineChartPanel;
import cn.edu.nuaa.cs.chart.SingleXYLineChartPanel;
import cn.edu.nuaa.cs.gui.main.MainWindow;
import cn.edu.nuaa.cs.io.FileHelper;
import org.jfree.chart.ChartPanel;

public class FaceViewer extends JPanel implements Runnable{
	public static String curFileName = null;

	public static int[] FRAME_NUM;
	public static long[] GMT_S;
	public static double[] BLINKING;
	public static double[] BLINK_FREQ;
	public static double[] BLINK_DURATION;
	public static double[] PERCLOS;
	public static double[] PUPIL_R_DIAM;
	public static double[] PUPIL_L_DIAM;

	public static DoubleXYLineChartPanel dChart_PUPIL;
	public static SingleXYLineChartPanel sChart_PLD;
	public static SingleXYLineChartPanel sChart_ZYCS;
	public static SingleXYLineChartPanel sChart_ZYPL;

	private int pulse = 10;
	private static int j=0;

	public FaceViewer() {
		setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("瞳孔直径", createTKZJJSPane());
		tabbedPane.add("疲劳度量值", createPLDJSPane());
		tabbedPane.add("眨眼次数", createZYCSJSPane());
		tabbedPane.add("眨眼频率", createZYPLJSPane());
		add(tabbedPane, BorderLayout.CENTER);

		new Thread(this).start();
	}
	public JScrollPane createTKZJJSPane(){
		JPanel jp = new JPanel(new FlowLayout());

		dChart_PUPIL = new DoubleXYLineChartPanel(
				"左右眼瞳孔直径变化曲线", "时间", "直径/米",
				0, 200, -0.005, 0.005,
				100,"左眼","右眼");
		ChartPanel chart = dChart_PUPIL.getChartPanel();
		jp.add(chart);

		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}
	public JScrollPane createPLDJSPane(){
		JPanel jp = new JPanel(new FlowLayout());
		sChart_PLD = new SingleXYLineChartPanel(
				"疲劳度量值变化曲线", "时间", "数值",
				0, 200, -0.01, 0.01,
				100,"数值");
		ChartPanel chart = sChart_PLD.getChartPanel();
		jp.add(chart);

		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}
	public JScrollPane createZYCSJSPane(){
		JPanel jp = new JPanel(new FlowLayout());
		sChart_ZYCS = new SingleXYLineChartPanel(
				"眨眼次数变化曲线", "时间", "数值",
				0, 200, -0.01, 0.1,
				100,"数值");
		ChartPanel chart = sChart_ZYCS.getChartPanel();
		jp.add(chart);

		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}
	public JScrollPane createZYPLJSPane(){
		JPanel jp = new JPanel(new FlowLayout());
		sChart_ZYPL = new SingleXYLineChartPanel(
				"眨眼频率变化曲线", "时间", "数值",
				0, 200, -0.01, 0.1,
				100,"数值");
		ChartPanel chart = sChart_ZYPL.getChartPanel();
		jp.add(chart);

		JScrollPane jsp = new JScrollPane(jp);
		return jsp;
	}


	@Override
	public void run() {
		while(true){
			if(curFileName!=null){
				System.out.println(">>>    "+curFileName);
				getContent();
				for (int i = 0; i < FRAME_NUM.length; i++) {
					dChart_PUPIL.seriesKey1.add(dChart_PUPIL.valuesx[i], dChart_PUPIL.valuesy1[i]);
					dChart_PUPIL.seriesKey2.add(dChart_PUPIL.valuesx[i], dChart_PUPIL.valuesy2[i]);
					sChart_PLD.seriesKey.add(sChart_PLD.valuesx[i], sChart_PLD.valuesy[i]);
					sChart_ZYCS.seriesKey.add(sChart_ZYCS.valuesx[i], sChart_ZYCS.valuesy[i]);
					sChart_ZYPL.seriesKey.add(sChart_ZYPL.valuesx[i], sChart_ZYPL.valuesy[i]);
					try {
						Thread.sleep(pulse);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				continue;
			}else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void getContent(){
		File file = new File(FaceWindow.faceLabPath+curFileName);
		ArrayList<String> contents = FileHelper.readFileByLine(file);

		FRAME_NUM = new int[contents.size()-1];
		GMT_S = new long[contents.size()-1];
		BLINKING = new double[contents.size()-1];
		BLINK_FREQ = new double[contents.size()-1];
		BLINK_DURATION = new double[contents.size()-1];
		PERCLOS = new double[contents.size()-1];
		PUPIL_R_DIAM = new double[contents.size()-1];
		PUPIL_L_DIAM = new double[contents.size()-1];

		for (int i = 1; i < contents.size(); i++) {
			String[] tuple = contents.get(i).trim().split("\\s+");
			FRAME_NUM[i-1] = Integer.valueOf(tuple[0]);
			GMT_S[i-1] = Long.valueOf(tuple[1]);
			BLINKING[i-1] = Double.valueOf(tuple[2]);
			BLINK_FREQ[i-1] = Double.valueOf(tuple[3]);
			BLINK_DURATION[i-1] = Double.valueOf(tuple[4]);
			PERCLOS[i-1] = Double.valueOf(tuple[5]);
			PUPIL_R_DIAM[i-1] = Double.valueOf(tuple[6]);
			PUPIL_L_DIAM[i-1] = Double.valueOf(tuple[7]);
		}

		dChart_PUPIL.setValuesx(FRAME_NUM);
		dChart_PUPIL.setValuesy1(PUPIL_L_DIAM);
		dChart_PUPIL.setValuesy2(PUPIL_R_DIAM);

		sChart_PLD.setValuesx(FRAME_NUM);
		sChart_PLD.setValuesy(PERCLOS);

		sChart_ZYCS.setValuesx(FRAME_NUM);
		sChart_ZYCS.setValuesy(BLINKING);

		sChart_ZYPL.setValuesx(FRAME_NUM);
		sChart_ZYPL.setValuesy(BLINK_FREQ);

		if (file.isFile() && file.exists()) {
			file.delete();
		}
		curFileName = null;
	}

}
