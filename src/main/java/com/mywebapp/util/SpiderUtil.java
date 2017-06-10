package com.mywebapp.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderUtil {

    public static List<String> Spider(String htmlStr,String format) {

        //String filepath = "d:/124.html";

        String url_str = htmlStr;//"https://movie.douban.com/";
        URL url = null;
        try {
            url = new URL(url_str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String charset = "utf-8";
        int sec_cont = 1000;
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("31.14.133.222", 80));
            URLConnection url_con = url.openConnection(proxy);

            url_con.setDoOutput(true);
            url_con.setReadTimeout(10 * sec_cont);
            url_con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            InputStream htm_in = url_con.getInputStream();

            String htm_str = InputStream2String(htm_in, charset);

            return img(htm_str, format);
            //saveHtml(filepath, htm_str);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    /**
     * Method: InputStream2String
     * Description: make InputStream to String
     *
     * @param in_st   inputstream which need to be converted
     * @param charset encoder of value
     * @throws IOException if an error occurred
     */
    public static String InputStream2String(InputStream in_st, String charset) throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while ((line = buff.readLine()) != null) {
            res.append(line);
        }
        return res.toString();
    }


    public static List<String> img(String htmlStr,String format){
        List<String> pics = new ArrayList<String>();
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        Matcher m = p.matcher(htmlStr);
        System.out.println(m.find());
        System.out.println(m.groupCount());
        while(m.find()){

            String img = m.group(1);
            //System.out.println(m.group()+"-------------↓↓↓↓↓↓");
            if(img.indexOf("."+format) != -1){

                pics.add(m.group(1));
                System.out.println(img);

            }

        }
        return pics;
    }
}