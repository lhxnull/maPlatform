//package com.picc.common.utils;
//
//import com.picc.map.entity.Coord2Addr;
//import org.apache.commons.lang.StringUtils;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class AddressCityUtils {
//    /**
//     * 判断坐标是否有效
//     * @param longitude
//     * @param latitude
//     * @return
//     */
//    public static boolean isValid(Double longitude, Double latitude){
//        if (longitude == null || latitude == null) {
//            return false;
//        }
//        if (longitude > 73.55 && longitude < 135.08333333333333333 && latitude > 3.85 && latitude < 53.55) {
//            return true;
//        }
//        return false;
//    }
//    /**
//     * 获取地址中区县级信息
//     * @param address
//     * @return
//     */
//    public static String regex3(String address){
//        String regEx="(.*省)?(.*市)?(.*)";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(address);
//        while(m.find()){
//            String s1= m.group(3);
//            if(StringUtils.isNotBlank(s1)){
//                return regexqx(s1);
//            }
//        }
//        return "";
//    }
//    /**
//     * 获取地址中乡镇级信息
//     * @param address
//     * @return
//     */
//    public static String regex(String address){
//        String regEx="(.*省)?(.*市)?(.*)";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(address);
//        while(m.find()){
//            String s3= m.group(3);
//            if(StringUtils.isNotBlank(s3)){
//                String s4=regexqx1(s3);
//                if(StringUtils.isBlank(s4)){
//                    //判断地址中是否包含地级市
//                    String s2=m.group(2);
//                    if(StringUtils.isNotBlank(s2)){
//                        int shi1=s2.indexOf("市");
//                        int shi2=s2.lastIndexOf("市");
//                        if(shi1!=shi2){
//                            return regexxz(s3);
//                        }
//                    }
//                }
//                if(StringUtils.isNotBlank(s4)){
//                    //获取乡镇信息
//                    return regexxz(s4);
//                }
//            }
//
//            return "";
//        }
//        return "";
//    }
//    /**
//     * 判断是否有区县级信息   取出下级信息
//     * @param address
//     * @return
//     */
//    private static String regexqx1(String address){
//        String regEx1="(.*县)(.*)";
//        Pattern p1 = Pattern.compile(regEx1);
//        Matcher m1 = p1.matcher(address);
//        if(m1.find()){
//            return m1.group(2);
//        }
//        String regEx2="(.*区)(.*)";
//        Pattern p2 = Pattern.compile(regEx2);
//        Matcher m2 = p2.matcher(address);
//        if(m2.find()){
//            return m2.group(2);
//        }
//
//        return "";
//    }
//    /**
//     * 判断是否有乡镇级信息  并截取出
//     * @param address
//     * @return
//     */
//    private static String regexxz(String address){
//        String regEx1="(.*镇)(.*)";
//        Pattern p1 = Pattern.compile(regEx1);
//        Matcher m1 = p1.matcher(address);
//        if(m1.find()){
//            return m1.group(1);
//        }
//        String regEx2="(.*乡)(.*)";
//        Pattern p2 = Pattern.compile(regEx2);
//        Matcher m2 = p2.matcher(address);
//        if(m2.find()){
//            return m2.group(1);
//        }
//        String regEx3="(.*街道)(.*)";
//        Pattern p3 = Pattern.compile(regEx3);
//        Matcher m3 = p3.matcher(address);
//        if(m3.find()){
//            return m3.group(1);
//        }
//        String regEx4="(.*团)(.*)";
//        Pattern p4= Pattern.compile(regEx4);
//        Matcher m4 = p4.matcher(address);
//        if(m4.find()){
//            return m4.group(1);
//        }
//        String regEx="(.*机场)(.*)";
//        Pattern p= Pattern.compile(regEx);
//        Matcher m = p.matcher(address);
//        if(m.find()){
//            return "";
//        }
//        String regEx5="(.*场)(.*)";
//        Pattern p5= Pattern.compile(regEx5);
//        Matcher m5 = p5.matcher(address);
//        if(m5.find()){
//            return m5.group(1);
//        }
//        String regEx6="(.*区)(.*)";
//        Pattern p6= Pattern.compile(regEx6);
//        Matcher m6 = p6.matcher(address);
//        if(m6.find()){
//            return m6.group(1);
//        }
//
//        return "";
//    }
//    /**
//     * 判断是否有区县级信息  并提取出
//     * @param address
//     * @return
//     */
//    private static String regexqx(String address){
//        String regEx1="(.*县)(.*)";
//        Pattern p1 = Pattern.compile(regEx1);
//        Matcher m1 = p1.matcher(address);
//        if(m1.find()){
//            String s2= m1.group(1);
//            int xian1=s2.indexOf("县");
//            int xian2=s2.lastIndexOf("县");
//            int qu=s2.indexOf("区");
//            if(xian1!=xian2){
//                return s2.substring(0,xian1+1);
//            }
//            //截取出的地址中同时包含区、县关键字  需要进一步截取
//            if(xian1>-1&&qu>-1){
//                return s2.substring(qu+1,xian1+1);
//            }
//            return m1.group(1);
//        }
//        String regEx2="(.*区)(.*)";
//        Pattern p2 = Pattern.compile(regEx2);
//        Matcher m2 = p2.matcher(address);
//        if(m2.find()){
//            String s2= m2.group(1);
//            int qu1=s2.indexOf("区");
//            int qu2=s2.lastIndexOf("区");
//            if(qu1!=qu2){
//                return s2.substring(0,qu1+1);
//            }
//            return m2.group(1);
//        }
//
//        return "";
//    }
//
//    /**
//     * 获取地级市
//     * @param address
//     * @return
//     */
//    public static String regexdshi(String address){
//        String regEx="(.*省)?(.*市)(.*)";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(address);
//        while(m.find()){
//            String s2= m.group(2);
//            int shi1=s2.indexOf("市");
//            int shi2=s2.lastIndexOf("市");
//            if(shi1!=shi2){
//                return s2.substring(shi1+1,shi2+1);
//            }
//        }
//        return "";
//    }
//    /**
//     * 获取地址中市级信息
//     * @param address
//     * @return
//     */
//    public static String regex2(String address){
//        String regEx="(.*省)?(.*市)(.*)";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(address);
//        if(m.find()){
//            String s2=m.group(2);
//            int shi1=s2.indexOf("市");
//            int shi2=s2.lastIndexOf("市");
//            if(shi1==shi2){
//                return s2;
//            }else{
//                return s2.substring(0,shi1+1);
//            }
//        }
//        return "";
//    }
//
//}
