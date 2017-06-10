package com.mywebapp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
   
    /**
     * @author gr
     * @return
     * 
     *         构造返回json
     * 
     */
    public static JSONObject constructResponse(int code, String msg, Object data) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }

    /**
     * 返回的数据进行格式转换，并且进行 html 转义
     * 
     * @return
     */
    public static JSONObject escapeHtmlOjbect(JSONObject jo) {
        Iterator<String> keys = jo.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jo.get(key);
            if (value instanceof JSONObject) {
                return escapeHtmlOjbect((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = escapeHtmlArray((JSONArray) value);
            } else if (value instanceof String) {
                String valueStr = value.toString();
                if (valueStr != null && !"".equals(valueStr)) {
                    valueStr = valueStr.toString().replaceAll("&", "&amp;")
                            .replaceAll("\"", "&quot;")
                            .replaceAll("'", "&acute;").replaceAll("<", "&lt;")
                            .replaceAll(">", "&gt;");
                    jo.put(key, valueStr);
                }
            }
        }
        return jo;
    }
    public static JSONArray escapeHtmlArray(JSONArray ja) {
        Iterator<Object> keys = ja.iterator();
        while (keys.hasNext()) {
            Object object = keys.next();
            if (object instanceof JSONObject) {
                object = escapeHtmlOjbect((JSONObject) object);
            } else if (object instanceof JSONArray) {
                object = escapeHtmlArray((JSONArray) object);
            } else if (object instanceof String) {
                String valueStr = object.toString();
                if (valueStr != null && !"".equals(valueStr)) {
                    valueStr = valueStr.toString().replaceAll("&", "&amp;")
                            .replaceAll("\"", "&quot;")
                            .replaceAll("'", "&acute;").replaceAll("<", "&lt;")
                            .replaceAll(">", "&gt;");
                    object = valueStr;
                }
            }
        }
        return ja;
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
                                                // '-' and decimal.
    }
    public static boolean isInRange(String range, int hour, int minute) {
        try {
            if (!StringUtils.hasText(range)) {
                return false;
            }
            /** 去掉所有空格 */
            range = StringUtils.trimAllWhitespace(range);
            String[] ranges = range.split(",");
            for (String section : ranges) {
                if (!StringUtils.hasText(section)) {
                    continue;
                }
                String[] time = section.split("-");
                if (time.length < 2) {
                    continue;
                }
                String stime = time[0];
                String etime = time[1];
                if (!StringUtils.hasText(stime) || !StringUtils.hasText(etime)) {
                    continue;
                }
                String[] stimes = stime.split(":");
                String[] etimes = etime.split(":");
                int sh, eh, sm = 0, em = 0;
                if (!isNumeric(stimes[0])) {
                    continue;
                }
                sh = Integer.parseInt(stimes[0]);
                if (stimes.length > 1) {
                    if (!isNumeric(stimes[1])) {
                        continue;
                    }
                    sm = Integer.parseInt(stimes[1]);
                }
                if (!isNumeric(etimes[0])) {
                    continue;
                }
                eh = Integer.parseInt(etimes[0]);
                if (etimes.length > 1) {
                    if (!isNumeric(etimes[1])) {
                        continue;
                    }
                    em = Integer.parseInt(etimes[1]);
                }
                if (hour > sh && hour < eh) {
                    return true;
                }
                if (hour == sh && minute >= sm) {
                    return true;
                }
                if (hour == eh && minute < em) {
                    return true;
                }
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
    }
    public static boolean isInList(List<Long> ids, long id) {
        for (Long i : ids) {
            if (i.equals(id)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 待插入通配符的字符串
     * 
     * @param src
     * @return 如果src为空或者不含有非空白字符，返回"" 否则返回将src所有空格去后，首尾及两字符间均插入".*"通配符的字符串，
     */
    public static String insertWildcard(String src) {
        /** src为null,或者不含有非空白字符 */
        if (!StringUtils.hasText(src)) {
            return "";
        }
        src = StringUtils.trimAllWhitespace(src);
        StringBuilder sb = new StringBuilder(".*");
        for (int i = 0; i < src.length(); i++) {
            sb.append(src.charAt(i) + ".*");
        }
        return sb.toString();
    }
    /**
     * 首尾插入通配符
     * 
     * @param src
     * @return
     */
    public static String insertWildcardAtBothEnds(String src) {
        /** src为null,或者不含有非空白字符 */
        if (!StringUtils.hasText(src)) {
            return "";
        }
        src = StringUtils.trimAllWhitespace(src);
        StringBuilder sb = new StringBuilder(".*");
        sb.append(src + ".*");
        return sb.toString();
    }
    /**
     * 由原始图片地址得到指定大小的图片url地址
     * 
     * @param original
     * @param width
     * @return
     */
    public static String assembleMTCpPicUrl(String original, Integer width) {
        if (!StringUtils.hasText(original)) {
            return original;
        }
        try {
            String result = "";
            URI uri = new URI(original);
            if (uri.getScheme() == null) {
                return original;
            }
            result += uri.getScheme() + "://";
            if (uri.getHost() == null) {
                return original;
            }
            result += uri.getHost();
            if (uri.getPort() != -1) {
                result += ":" + uri.getPort();
            }
            result += "/" + width + ".0" + uri.getRawPath();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return original;
        }
    }
    /**
     * UUID
     * 
     * @return生成的GUID为一串32位字符组成的128位数据
     */
    public static String GUID() {
        String a = null;
        // 产生 5 个 GUID
        for (int i = 0; i < 5; i++) {
            // 创建 GUID 对象
            UUID uuid = UUID.randomUUID();
            // 得到对象产生的ID
            a = uuid.toString();
            // 转换为大写
            a = a.toUpperCase();
            // 替换 -
            a = a.replaceAll("-", "_");
            // System.out.println(a);
        }
        System.out.println(a + "a");
        return a;
    }
    /**
     * 重新上传食物图片,删除原来的图片
     * 图片路径
     * @param
     * @return boolean flag
     */
    /*
     * public boolean deleteFile(String sPath) { boolean flag = false; File file
     * = new File(sPath);
     * 
     * // 路径为文件且不为空则进行删除 if (file.isFile() && file.exists()) { file.delete();
     * 
     * flag = true; } return flag; }
     */
    public boolean deleteFile(String sPath) {
        File file = new File(sPath);
        boolean result = false;
        int tryCount = 0;
        while (!result && tryCount++ < 10) {
            System.gc();
            result = file.delete();
        }
        return result;
    }


    /**
     *
     * @return
     * @throws ParseException
     */
    public static Long getSystemTime() throws ParseException {
        java.util.Date dt = new Date();
        //System.out.println(dt.toString());   //java.util.Date的含义
        long lSysTime1 = dt.getTime() / 1000;   //得到秒数，Date类型的getTime()返回毫秒数
        
//		dateFormater.parse(dateFormater.format(Long.parseLong(data.getTalkingStartTime())))
        return  lSysTime1;
        
    }

    
    /**
     * 
     * @Title: getIpAddr 
     * @Description: 得到用户的真实ip地址
     * @param request
     * @return
     * @return: String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
        ip = request.getRemoteAddr(); 
        } 
        return ip; 
        } 
    
    /**
     * 
     * @Description: 比较两个时间的时间差,返回单位为分钟,格式:yyyy-MM-dd HH:mm:ss
     * @param  begin, end
     * @return int
     * @author gr
     */
    public static int countTime(String begin, String end){
        int hour = 0;  
        int minute = 0;  
        long total_minute = 0;  
        
      
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {  
            Date begin_date = df.parse(begin);
            Date end_date = df.parse(end);
      
            total_minute = (end_date.getTime() - begin_date.getTime())/(1000*60);  
      
            hour = (int) total_minute/60;  
            minute = (int) total_minute%60;  
      
        } catch (Exception e) {
            System.out.println("传入的时间格式不符合规定");
        }  
      
       
        return hour*60+minute;  
    } 
    /**
     * 比较时间大小
     */
    public static boolean compareDate(Date d1, Date d2){
        if (d1.getTime() > d2.getTime()) {
            //System.out.println("dt1 在dt2前");
            return true;
        } else if (d1.getTime() < d2.getTime()) {
            //System.out.println("dt1在dt2后");
            return false;
        } else {//相等
            return true;
        }
}
      // 可逆的加密算法  
    public static String KL(String inStr) {
        // String s = new String(inStr);  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++) {  
            a[i] = (char) (a[i] ^ 's');  
           
        }  
        String s = new String(a);
        //System.out.println(a);
        return s;  
    }

    //判断string变量是否为空
    public static boolean isNullOrBlank(String value){
        return value == null|| "".equals(value.trim())||value.trim().equalsIgnoreCase(null);
    }



    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            InputStream instream = conn.getInputStream();
            if(instream!=null){
                in = new BufferedReader( new InputStreamReader(instream));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return result;
    }
    /**
     * 发起http请求获取返回结果
     * @param req_url 请求地址
     * @return
     */
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

    public static List<String> patternLoiginName(String value){
        List<String> user_LoignName = new ArrayList<String>();
        Pattern pt = Pattern.compile("@\\w+([,\\.\\:\\;\\?\\!\\'\\\"]|\\s|，|。|？|；|！|‘|’|“|”)");
        Matcher mt = pt.matcher(value);
        while (mt.find()) {
            user_LoignName.add(mt.group().replace("@",""));
        }
        return user_LoignName;
    }



}