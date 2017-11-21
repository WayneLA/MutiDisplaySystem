package cn.edu.nuaa.cs.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 85492 on 2017/10/19.
 */
public class HTMLHelper {
    public static String urlpath = "http://lovesport.duapp.com/GetLocation2Show";



    public static String getUrlData(String urlpath) throws Exception{
        URL url =  new URL(urlpath);
        HttpURLConnection httpurl = (HttpURLConnection)url.openConnection();
        InputStream is = httpurl.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
        String line = br.readLine();
        return line;
    }

    public static void main(String[] args){
        HTMLHelper hh = new HTMLHelper();
        while(true){
            try {
                Thread.sleep(1000);
                System.out.println(hh.getUrlData(urlpath));
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }

    }
}
