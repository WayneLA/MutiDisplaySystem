package cn.edu.nuaa.cs.gui.warning;

import javax.swing.*;

/**
 * Created by 85492 on 2017/12/22.
 */
public class IndoorWarning {
    public static JOptionPane jop;
    public static JDialog dialog;

    static {
        jop = new JOptionPane("非法进入！",JOptionPane.INFORMATION_MESSAGE);
        dialog = jop.createDialog("室内对象轨迹");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(false);
    }

    public static void showWarning(){
        dialog.setVisible(true);
    }

    public static void main(String[] args){
        IndoorWarning.showWarning();
    }
}
