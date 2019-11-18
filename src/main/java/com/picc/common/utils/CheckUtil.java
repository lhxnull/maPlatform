//package com.picc.common.utils;
//
//import com.picc.map.entity.AdminstrativeOrgan;
//import com.picc.map.entity.Coord2Addr;
//import com.picc.map.entity.MapDefaultParams;
//import org.apache.commons.lang.StringUtils;
//
//public class CheckUtil {
//
//
//    /**
//     * 地址中包含乡镇信息  判断解析出的坐标是否满足要求
//     * @param address
//     * @param longitude
//     * @param latitude
//     * @return
//     */
//    public static boolean check4Addr(String address,Double longitude,Double latitude,String mapType){
//        if (checkIgnoreAreas(address)) {
//            return true;
//        }
//
//        AdminstrativeOrganService adminstrativeOrganService=SpringBeanUtil.getInstance().getBean(AdminstrativeOrganServiceImpl.class);
//        String addr= RegexUtil.regex(address);
//        if(RegexUtil.include4(addr)){ //地址中包含乡镇级信息！！！
//            String addr3=RegexUtil.regex3(address);
//            if(StringUtils.isBlank(addr3)){
//                addr3=RegexUtil.regexdshi(address);
//            }
//            if(StringUtils.isNotBlank(addr3)){//地址中包含乡镇级信息的同时也包含区县级信息！！！
//                if("辖区".equals(addr3)){//特殊处理市辖区
//                    addr3="市辖区";
//                }
//                String addr2=RegexUtil.regex2(address);
//                if(StringUtils.isNotBlank(addr2)){//地址中存在区县信息同时包含市级信息
//                    AdminstrativeOrgan adcity=adminstrativeOrganService.findByName(addr2, 2);
//                    if(adcity!=null){
//                        AdminstrativeOrgan ad=adminstrativeOrganService.findByCode(addr3,adcity.getComCode(),3);
//                        if(ad==null){
//                            String name = addr3.substring(0,addr3.length()-1);
//                            ad=adminstrativeOrganService.findByCode(name+"区",adcity.getComCode(),3);
//                            if(ad==null){
//                                ad=adminstrativeOrganService.findByCode(name+"县",adcity.getComCode(),3);
//                                if(ad==null){
//                                    ad=adminstrativeOrganService.findByCode(name+"市",adcity.getComCode(),3);
//                                }
//                            }
//                        }
//                        if(ad!=null){
//                            AdminstrativeOrgan a=adminstrativeOrganService.findByCode(addr, ad.getComCode(), 4);
//                            if(a!=null){
//                                Double lat= a.getLatitude();
//                                Double lon= a.getLongitude();
//                                if(MapUtil.isValid(lon, lat)){
//                                    //两坐标间距离 distance
//                                    double dis= MapUtil.getDistanceFromDegreeCoords(lon, lat,longitude,latitude);
//                                    if(dis<=30000){
//                                        //两点间距离满足要求！！！
//                                        return true;
//                                    }else{
//                                        boolean b=check3Addr(address,longitude,latitude,mapType);
//                                        return b;
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//
//        }
//        return true;
//    }
//    /**
//     * 地址中包含区县信息  判断解析出的坐标是否满足要求
//     * @param address
//     * @param longitude
//     * @param latitude
//     * @return
//     */
//    public static boolean check3Addr(String address,Double longitude,Double latitude,String mapType){
//        if (checkIgnoreAreas(address)) {
//            return true;
//        }
//
//        String xian=RegexUtil.regex3(address);
//        if(StringUtils.isBlank(xian)){
//            xian=RegexUtil.regexdshi(address);
//        }
//        if(StringUtils.isNotBlank(xian)){
//            if("辖区".equals(xian)){//特殊处理市辖区
//                xian="市辖区";
//            }
//            String addr2=RegexUtil.regex2(address);
//            if(StringUtils.isNotBlank(addr2)){//地址中存在区县信息同时包含市级信息
//                AdminstrativeOrganService adminstrativeOrganService=SpringBeanUtil.getInstance().getBean(AdminstrativeOrganServiceImpl.class);
//                AdminstrativeOrgan add2=adminstrativeOrganService.findByName(addr2, 2);
//                if(add2!=null){
//                    AdminstrativeOrgan ad=adminstrativeOrganService.findByCode(xian,add2.getComCode(),3);
//                    if(ad==null){
//                        String name = address.substring(0,xian.length()-1);
//                        ad=adminstrativeOrganService.findByCode(name+"区",add2.getComCode(),3);
//                        if(ad==null){
//                            ad=adminstrativeOrganService.findByCode(name+"县",add2.getComCode(),3);
//                            if(ad==null){
//                                ad=adminstrativeOrganService.findByCode(name+"市",add2.getComCode(),3);
//                            }
//                        }
//                    }
//                    if(ad!=null){
//                        Coord2Addr addr=new Coord2Addr();
//                        if(mapType.equals(MapDefaultParams.MAPTYPE_NAME_GD)){
//                            addr=GDMapUtil.getInstance().coord2Address(longitude, latitude);
//                        }else if (mapType.equals(MapDefaultParams.MAPTYPE_NAME_BD)) {
//                            addr=BDMapUtil.getInstance().coord2AddrResp(longitude,latitude);
//                        }else {
//                            addr=SWMapUtil.getInstance().coord2AddrResp(longitude,latitude);
//                        }
//                        if(addr!=null){
//                            Double lat= addr.getLatitude();
//                            Double lon= addr.getLongitude();
//                            if(MapUtil.isValid(lon, lat)){
//                                //两坐标间距离 distance
//                                double dis= MapUtil.getDistanceFromDegreeCoords(lon, lat,longitude,latitude);
//                                if(dis<=30000){
//                                    //两点间距离满足要求！！！
//                                    return true;
//                                }
//                            }
//                        }
////						 String xianf=RegexUtil.regex3(addr.getFormatted_address());
////						 if(StringUtils.isBlank(xianf)){
////							 xianf=RegexUtil.regexdshi(addr.getFormatted_address());
////						 }
////						 if(check(xian,xianf)){
////							 //坐标反地理编码地址中包含区县信息，并且跟提供地址中区县信息一致！反地理编码区县级信息是：xianf 地址中包含区县级信息是：xian
////							 return true;
////						 }else{
////							 return false;
////						 }
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//
//    //校验区县级信息是否一致
//    private static boolean check(String addr,String address){
//        boolean flag=false;
//        String name=addr.substring(0,addr.length()-1);
//        flag=address.equals(addr);
//        if(!flag){
//            flag=address.equals(name+"县");
//            if(!flag){
//                flag=address.equals(name+"区");
//                if(!flag){
//                    flag=address.equals(name+"市");
//                }
//            }
//        }
//        return flag;
//    }
//
//    /**
//     *
//     * @Description: 将包含配置文件中忽略的区域的查勘地址不进行校验
//     * @return
//     * boolean
//     * @throws
//     * @author lxw
//     * @date 2018年4月10日
//     */
//    private static boolean checkIgnoreAreas(String address){
//        String  IgnoreAreas = SystemConfigManager.getInstance().getUnifiedConfig("IgnoreAreas");
//        String area[] = IgnoreAreas.split(";");
//        for (int i = 0; i < area.length; i++) {
//            if (address.contains(area[i])) {
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//}
