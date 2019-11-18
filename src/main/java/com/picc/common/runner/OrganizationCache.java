package com.picc.common.runner;


import lombok.Data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

/**
 * Created by lhx on 2019/11/11.
 * 项目缓存数据
 */
@Data
public class OrganizationCache {

    public static final String fileName = "zoneAddr2CodeGD.csv";
    //单例
    private static OrganizationCache instance = new OrganizationCache();
    public static OrganizationCache getInstance(){
        return instance;
    }
    //百度url
    public static Map<String,String> BDConfig ;
    //百度key
    public static ConcurrentLinkedQueue<String> BDqueue;
    //四维url
    public static Map<String,String> SWConfig ;
    //获取市区编码
    public static Map<String, String> zoneAddr2codeGDMap ;
    //基本配置
    public static Map<String,String> baseConfigMap;

    /**
     * 轮流获取百度key
     * @return
     */
    public  static String getAK() {

        String value=BDqueue.poll();
        BDqueue.offer(value);
        return value;
    }

    public static  String getBDConfigValue(String key) {
        return BDConfig.get(key);
    }
    public static String getBaeConfigValue(String key) {
        return baseConfigMap.get(key);
    }
    public static String getSWConfigValue(String key) {
        return SWConfig.get(key);
    }

    /**
     * 读取csv文件。
     * @param fileName
     * @return
     */
    public static Map<String, String> getZoneAddr2CodeMap(String fileName) {
        InputStream in;
        Map<String, String> map = new HashMap<String, String>();
        try {
            in = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(fileName);
            BufferedReader bufferedreader = new BufferedReader(
                    new InputStreamReader(in, "GBK"));

            String stemp;
            while ((stemp = bufferedreader.readLine()) != null) {
                String[] strList = stemp.split(",");
                String addr = strList[0];
                String code = strList[1];
                if (strList.length == 2) {
                    map.put(addr, code);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return map;
    }

    /**
     * @param @param  addr
     * @param @return
     * @return String
     * @throws
     * @Title: getZoneCodeFromZoneAddr
     * @Description: 区域描述-》高德区域代码
     * @Author: MaXiao
     */
    public static String getZoneCodeFromZoneAddr(String addr) {

       return zoneAddr2codeGDMap.getOrDefault(addr,"");

    }
}
