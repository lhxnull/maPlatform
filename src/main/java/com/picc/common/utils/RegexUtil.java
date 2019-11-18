package com.picc.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {


    /**
     * 获取地址中乡镇级信息
     * @param address
     * @return
     */
    public static String regex(String address){
        String regEx="(.*省)?(.*市)?(.*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(address);
        while(m.find()){
            String s3= m.group(3);
            if(StringUtils.isNotBlank(s3)){
                String s4=regexqx1(s3);
                if(StringUtils.isBlank(s4)){
                    //判断地址中是否包含地级市
                    String s2=m.group(2);
                    if(StringUtils.isNotBlank(s2)){
                        int shi1=s2.indexOf("市");
                        int shi2=s2.lastIndexOf("市");
                        if(shi1!=shi2){
                            return regexxz(s3);
                        }
                    }
                }
                if(StringUtils.isNotBlank(s4)){
                    //获取乡镇信息
                    return regexxz(s4);
                }
            }

            return "";
        }
        return "";
    }
    /**
     * 判断是否有区县级信息  并提取出
     * @param address
     * @return
     */
    private static String regexqx(String address){
        String regEx1="(.*县)(.*)";
        Pattern p1 = Pattern.compile(regEx1);
        Matcher m1 = p1.matcher(address);
        if(m1.find()){
            String s2= m1.group(1);
            int xian1=s2.indexOf("县");
            int xian2=s2.lastIndexOf("县");
            int qu=s2.indexOf("区");
            if(xian1!=xian2){
                return s2.substring(0,xian1+1);
            }
            //截取出的地址中同时包含区、县关键字  需要进一步截取
            if(xian1>-1&&qu>-1){
                return s2.substring(qu+1,xian1+1);
            }
            return m1.group(1);
        }
        String regEx2="(.*区)(.*)";
        Pattern p2 = Pattern.compile(regEx2);
        Matcher m2 = p2.matcher(address);
        if(m2.find()){
            String s2= m2.group(1);
            int qu1=s2.indexOf("区");
            int qu2=s2.lastIndexOf("区");
            if(qu1!=qu2){
                return s2.substring(0,qu1+1);
            }
            return m2.group(1);
        }

        return "";
    }
    /**
     * 判断是否有区县级信息   取出下级信息
     * @param address
     * @return
     */
    private static String regexqx1(String address){
        String regEx1="(.*县)(.*)";
        Pattern p1 = Pattern.compile(regEx1);
        Matcher m1 = p1.matcher(address);
        if(m1.find()){
            return m1.group(2);
        }
        String regEx2="(.*区)(.*)";
        Pattern p2 = Pattern.compile(regEx2);
        Matcher m2 = p2.matcher(address);
        if(m2.find()){
            return m2.group(2);
        }

        return "";
    }
    /**
     * 判断是否有乡镇级信息  并截取出
     * @param address
     * @return
     */
    private static String regexxz(String address){
        String regEx1="(.*镇)(.*)";
        Pattern p1 = Pattern.compile(regEx1);
        Matcher m1 = p1.matcher(address);
        if(m1.find()){
            return m1.group(1);
        }
        String regEx2="(.*乡)(.*)";
        Pattern p2 = Pattern.compile(regEx2);
        Matcher m2 = p2.matcher(address);
        if(m2.find()){
            return m2.group(1);
        }
        String regEx3="(.*街道)(.*)";
        Pattern p3 = Pattern.compile(regEx3);
        Matcher m3 = p3.matcher(address);
        if(m3.find()){
            return m3.group(1);
        }
        String regEx4="(.*团)(.*)";
        Pattern p4= Pattern.compile(regEx4);
        Matcher m4 = p4.matcher(address);
        if(m4.find()){
            return m4.group(1);
        }
        String regEx="(.*机场)(.*)";
        Pattern p= Pattern.compile(regEx);
        Matcher m = p.matcher(address);
        if(m.find()){
            return "";
        }
        String regEx5="(.*场)(.*)";
        Pattern p5= Pattern.compile(regEx5);
        Matcher m5 = p5.matcher(address);
        if(m5.find()){
            return m5.group(1);
        }
        String regEx6="(.*区)(.*)";
        Pattern p6= Pattern.compile(regEx6);
        Matcher m6 = p6.matcher(address);
        if(m6.find()){
            return m6.group(1);
        }

        return "";
    }
    /**
     * 获取地址中区县级信息
     * @param address
     * @return
     */
    public static String regex3(String address){
        String regEx="(.*省)?(.*市)?(.*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(address);
        while(m.find()){
            String s1= m.group(3);
            if(StringUtils.isNotBlank(s1)){
                return regexqx(s1);
            }
        }
        return "";
    }
    /**
     * 获取地级市
     * @param address
     * @return
     */
    public static String regexdshi(String address){
        String regEx="(.*省)?(.*市)(.*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(address);
        while(m.find()){
            String s2= m.group(2);
            int shi1=s2.indexOf("市");
            int shi2=s2.lastIndexOf("市");
            if(shi1!=shi2){
                return s2.substring(shi1+1,shi2+1);
            }
        }
        return "";
    }
    /**
     * 获取地址中市级信息
     * @param address
     * @return
     */
    public static String regex2(String address){
        String regEx="(.*省)?(.*市)(.*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(address);
        if(m.find()){
            String s2=m.group(2);
            int shi1=s2.indexOf("市");
            int shi2=s2.lastIndexOf("市");
            if(shi1==shi2){
                return s2;
            }else{
                return s2.substring(0,shi1+1);
            }
        }
        return "";
    }
    /**
     * 判断地址中是否有乡镇级信息
     * @param addr
     * @return
     */
    public static boolean include4(String addr){
        int z=addr.indexOf("镇");
        int x=addr.indexOf("乡");
        int t=addr.indexOf("团");
        int c=addr.indexOf("场");
        int dq=addr.indexOf("地区");
        int j=addr.indexOf("街");
        if(z>-1 || x>-1 || t>-1 ||c>-1 ||dq>-1 ||j>-1){
            return true;
        }
        return false;
    }
    /**
     * 判断地址中是否有区县级信息
     * @param addr
     * @return
     */
    public static boolean include3(String addr){
        int xian=addr.indexOf("县");
        int qu=addr.indexOf("区");
        int shi=addr.indexOf("市");
        if(xian>-1 ||qu>-1 ||shi>-1){
            return true;
        }
        return false;
    }
    /**
     * 获取地址中的乡镇信息
     * @param address
     * @return
     */
    public static String regexTown(String address){
        String regEx="(.*省)?(.*市)(.*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(address);
        if(m.find()){
            String s3=m.group(3);
            if(StringUtils.isNotBlank(s3)){
                return regexxz(s3);
            }
        }
        return "";
    }
    //TODO
    public static void main(String[] args) {
        //String s="江苏省南京市溧水区 溧水区白马镇建设管理服务所";
        String s="江苏省南京市南京县白马镇政府";
        s=s.replace(" ", "");
        String str=regex(s);//获取乡镇
        System.out.println(str);
        String ss=regex3(s);//获取区县
        System.out.println(ss.indexOf("区"));
        System.out.println(ss.substring(0, ss.indexOf("区")+1));
        System.out.println(ss);
        String sss=regex2(s);//获取市
        System.out.println(sss);
        String town=regexTown(s);
        System.out.println(town);
        String s3=regexdshi(s);
        System.out.println(s3);
        //MapUtil map=new MapUtil();
        //	double d=map.getDistanceFromDegreeCoords(116.403963,39.915119, 116.412985,40.055885);
        //	System.out.println(d);

        //	System.out.println(s.replace(" ", ""));
    }



}
