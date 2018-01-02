package cn.edu.nuaa.cs.gui.warning;

import javax.swing.*;

/**
 * Created by 85492 on 2017/12/21.
 */
public class WarningDialog {

    public static JOptionPane jop1, jop2, jop3;
    public static JDialog dialog1, dialog2, dialog3;

    static{
        jop1 = new JOptionPane("一级警报",JOptionPane.INFORMATION_MESSAGE);
        dialog1 = jop1.createDialog("眼部数据");
        dialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog1.setAlwaysOnTop(true);
        dialog1.setModal(false);

        jop2 = new JOptionPane("二级警报",JOptionPane.INFORMATION_MESSAGE);
        dialog2 = jop2.createDialog("眼部数据");
        dialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog2.setAlwaysOnTop(true);
        dialog2.setModal(false);

        jop3 = new JOptionPane("三级警报",JOptionPane.INFORMATION_MESSAGE);
        dialog3 = jop3.createDialog("眼部数据");
        dialog3.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog3.setAlwaysOnTop(true);
        dialog3.setModal(false);
    }

    public static void WaningDialog_fb (double v,double max,double min){

        if(v>max*1.3 || v<min*0.7){
            new Thread() {
                public void run() {
                    if(!dialog3.isVisible()){
                        dialog3.setVisible(true);
                    }
                }
            }.start();
        }else if (v>max*1.2 || v<min*0.8) {
            new Thread() {
                public void run() {
                    if(!dialog2.isVisible()){
                        dialog2.setVisible(true);
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(dialog2.isVisible()){
                            dialog2.setVisible(false);
                        }
                    }
                }
            }.start();
//        } else if (v>max*1.1 || v<min*0.9){
        } else if (v>max|| v<min){
            new Thread() {
                public void run() {
                    if(!dialog1.isVisible()) {
                        dialog1.setVisible(true);
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(dialog1.isVisible()){
                        dialog1.setVisible(false);
                    }
                }
            }.start();
        }
    }
}
