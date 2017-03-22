import cn.edu.nuaa.cs.io.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 85492 on 2017/3/8.
 */
public class Format {

    public static void main(String[] args){
        String strName = "F:\\NUAA\\03.我的工作\\眼动仪数据显示\\眼动仪数据\\00.test";
        String saveName = strName+"\\EyeData.dat";

        File dir = new File(strName);
        String fileName = dir.list()[0];

        File file = new File(strName+"\\"+fileName);
        ArrayList<String> contents = FileHelper.readFileByLine(file);

        String lines = "";
        for (int i = 0; i < contents.size(); i++) {
            String tmp = contents.get(i).trim();
            String[] str = tmp.split("\\s+");
            lines += str[0]+"\t"+str[6]+"\t"+str[7]+"\t"+str[10]+"\t"+str[32]+"\t"+str[36]+"\n";
        }
        FileHelper.writeMethodC(saveName,lines);
    }
}
