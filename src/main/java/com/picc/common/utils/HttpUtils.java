package com.picc.common.utils;


import com.picc.common.runner.OrganizationCache;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.Map;

@Slf4j
public class HttpUtils {

    private static final int TIME = 10000;



    /**
     * 通过代理get请求
     * @param url
     * @return
     */

    public static String hpptGetRequestByProxy(String url) {


        InputStream in=null;
        try{
            URL curl = new URL(url);
            if (url != null) {

                InetSocketAddress addr = new InetSocketAddress(OrganizationCache.getBaeConfigValue("proxy").toString(),Integer.parseInt(OrganizationCache.getBaeConfigValue("prot")));
                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
                // 如果我们知道代理server的名字, 可以直接使用
                URLConnection conn = curl.openConnection(proxy);
                conn.setConnectTimeout(TIME);//连接超时时间
                conn.setReadTimeout(TIME);
                in = conn.getInputStream();
                //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY, PORT));
                StringBuffer sb = new StringBuffer();
                int len = -1;
                byte[] buff = new byte[1024];
                while((len = in.read(buff)) != -1){
                    sb.append(new String(buff,0,len,InputSuggestionUtils.ENCODER));
                }
                return sb.toString();
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(in!=null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return null;
    }


    /**
     * 使用Get方式获取数据
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        log.info("url:{}",url);
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),InputSuggestionUtils.ENCODER));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送 GET 请求出现异常:{}",e);
        }
        // 使用finally块来关闭输入流
        finally {
           closeStream(null,in);
        }
        return result.toString();
    }

    /**
     * POST请求，字符串形式数据
     * @param url 请求地址
     * @param param 请求数据
     */
    public static String sendPostUrl(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            if(null!=param){
                out.print(param);
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),InputSuggestionUtils.ENCODER));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
//            log.error("发送 POST 请求出现异常:",e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            closeStream(out,in);
        }
        return result.toString();
    }

    /**
     * POST请求，Map形式数据
     * @param url 请求地址
     * @param param 请求数据
     * @throws UnsupportedEncodingException
     */
    public static String sendPost(String url, Map<String, String> param){
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        StringBuffer buffer = new StringBuffer();
        try {
            if (param != null && !param.isEmpty()) {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),InputSuggestionUtils.ENCODER)).append("&");
                }
                buffer.deleteCharAt(buffer.length() - 1);
            }
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(buffer);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),InputSuggestionUtils.ENCODER));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
//            log.error("发送 POST 请求出现异常:",e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            closeStream(out,in);
        }
        return result.toString();
    }

    /**
     *
     * @param out
     * @param in
     */
    private static void closeStream(PrintWriter out,BufferedReader in) {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
//                log.error("关闭流异常:",ex);
        }
    }



    /**
     * 获取实际IP地址
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
