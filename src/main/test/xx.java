import com.mywebapp.util.CommonUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Api {
    public static void main(String args[]) {

        String user = "javagaorui5944";
        String key = "CE382F0F_422A_4466_B284_B43458664F9C";
        String str = sendGet("http://127.0.0.1:8080/proxyIpApi/" + user + "?key=" + key);
        System.out.println(str);
        /**
         *str 输出结果,json格式
         {"list":["HTTP @ /61.152.81.193:9100","HTTP @ /52.53.99.220:80","HTTP @ /121.8.98.201:8080","HTTP @ /193.107.247.118:53281","HTTP @ /36.68.90.108:8080","HTTP @ /190.205.186.198:3128","HTTP @ /5.160.3.147:8080","HTTP @ /45.123.3.105:8080","HTTP @ /37.14.112.205:8089","HTTP @ /197.242.168.194:3128"]}
         *
         */
    }

    public static String sendGet(String req_url) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(req_url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //res = new String(buffer.toString().getBytes("iso-8859-1"),"utf-8");
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}