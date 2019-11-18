package com.picc.common.utils;

import com.picc.common.runner.OrganizationCache;
import com.picc.map.entity.InputSuggest;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputSuggestionUtils {

    public static final String ENCODER = "UTF-8";

    //单例
    private static InputSuggestionUtils instance = new InputSuggestionUtils();
    public static InputSuggestionUtils getInstance(){
        return instance;
    }


    /**
     * 处理编码格式
     * @param encode
     * @param value
     * @return
     */
    private static String encode(String encode,String value) {

        try {
            return URLEncoder.encode(value, encode);
        } catch (UnsupportedEncodingException e) {
            return value;
        }

    }

    /**
     * 录入提示功能
     * @param city
     * @param keyword
     * @param mapType
     * @return
     * @throws Exception
     */
    public static List<InputSuggest> inputSuggestion (String city,
                                         String keyword, String mapType) throws Exception {

        keyword =  encode(ENCODER,keyword);
        city = StringUtils.isNotBlank(city)?encode(ENCODER,city):encode(ENCODER,"全国");
        String urlKey= "";

        if(MapUtil.MAP_BAIDU.equals(mapType)){

            urlKey = OrganizationCache.getBDConfigValue("inputUrl")+"&ak="+OrganizationCache.getAK()+"&query=" + keyword + "&region="+city;
            String result = HttpUtils.hpptGetRequestByProxy(urlKey);
            return AnalyticUtil.getInstance().processSuggestionRespBD(result);
        }else {

            urlKey = OrganizationCache.getSWConfigValue("inputUrl")+"&keywords="+keyword+ "&city="+city;
            String result = HttpUtils.sendGet(urlKey);
            return AnalyticUtil.getInstance().processSuggestionRespSW(result);
        }

    }

    /**
     * 地理编码接口
     * @param city
     * @param keyword
     * @param mapType
     * @return
     * @throws Exception
     */
    public static InputSuggest getAddr2Coord (String city,
                                        String keyword, String mapType) throws Exception {

        keyword = URLEncoder.encode(keyword, ENCODER);
        city = StringUtils.isNotBlank(city)?encode(ENCODER,city):encode(ENCODER,"全国");
        String urlKey= "";

        if(MapUtil.MAP_BAIDU.equals(mapType)){

            urlKey =  OrganizationCache.getBDConfigValue("geoadd2coder")+"&ak="+OrganizationCache.getAK()+"&address=" + keyword + "&city="+city;
            String result = HttpUtils.hpptGetRequestByProxy(urlKey);
            return AnalyticUtil.getInstance().processAddrToCoordJsonResp(result);
        }else {

            urlKey = OrganizationCache.getSWConfigValue("geoadd2coder")+"&keywords="+keyword+"&city="+city;
            String result = HttpUtils.sendGet(urlKey);
            return AnalyticUtil.getInstance().processAddrToCoordJsonRespTOSW(result);
        }


    }

    /**
     * 逆地理编码接口
     * @param longitude
     * @param latitude
     * @param mapType
     * @return
     */
    public static InputSuggest coord2AddrResp(String longitude, String latitude, String mapType) throws Exception{

        if (!AnalyticUtil.isValid(longitude,latitude)) {
            return null;
        }
        String urlKey="";
        if(MapUtil.MAP_BAIDU.equals(mapType)){

            urlKey = OrganizationCache.getBDConfigValue("geocoder2add") +"&ak="+OrganizationCache.getAK()+"&location="+latitude
                    + "," + longitude;
            String result = HttpUtils.hpptGetRequestByProxy(urlKey);
            return AnalyticUtil.getInstance().processCoord2AddrJsonResp(result);
        }else {
            urlKey = OrganizationCache.getSWConfigValue("geocoder2add")+"&location="+longitude+","+latitude;
            String result = HttpUtils.sendGet(urlKey);
            return AnalyticUtil.getInstance().processCoord2AddrJsonRespToSW(result);
        }


    }


    /**
     * 导航长度
     * @param startlatlong
     * @param endlatlong
     * @param mapType
     * @return
     */

    public static InputSuggest getDistince(String startlatlong, String endlatlong,String mapType) {
        String coord_type = MapUtil.BD_0911;
        if(mapType.equals(MapUtil.MAP_BAIDU)){
            coord_type = MapUtil.BD_0911;
        } else if (mapType.equals(MapUtil.MAP_GAODE)||mapType.equals(MapUtil.MAP_SIWEI)) {
            coord_type = MapUtil.GCJ_02;
        }else {
            coord_type = MapUtil.WGS_84;
        }
        String[] startcoords = startlatlong.split(",");
        String[] endcoords = endlatlong.split(",");
        if(!AnalyticUtil.isValid(startcoords[1],startcoords[0])
                || !AnalyticUtil.isValid(endcoords[1],endcoords[0])){
            return null;

        }
        String urlKey="";
        if(MapUtil.MAP_BAIDU.equals(mapType)){

            urlKey = OrganizationCache.getBDConfigValue("getDistince") +"&ak="+OrganizationCache.getAK()+"&coord_type="+coord_type
                    +"&origins="+startlatlong+"&destinations="+endlatlong;
            String result = HttpUtils.hpptGetRequestByProxy(urlKey);
            return AnalyticUtil.getInstance().processRouteJsonResp(result);
        }else {
            urlKey = OrganizationCache.getSWConfigValue("getDistince") + startcoords[1] + "," + startcoords[0] + "&ePoint=" + endcoords[1] + ","
                    + endcoords[0];
            String result = HttpUtils.sendGet(urlKey);
            return AnalyticUtil.getInstance().processRouteJsonRespToSW(result);
        }


    }

    /**
     * 导航路径
     * @param startLatLong
     * @param endLatLong
     * @param startCity
     * @param endCity
     * @param mapType
     * @return
     */
    public static InputSuggest[] getNavigate(String startLatLong, String endLatLong, String startCity, String endCity,String mapType) {

        String coord_type = MapUtil.BD_0911;
        if(mapType.equals(MapUtil.MAP_BAIDU)){
            coord_type = MapUtil.BD_0911;
        } else if (mapType.equals(MapUtil.MAP_GAODE)||mapType.equals(MapUtil.MAP_SIWEI)) {
            coord_type = MapUtil.GCJ_02;
        }else {
            coord_type = MapUtil.WGS_84;
        }
        String[] startcoords = startLatLong.split(",");
        String[] endcoords = endLatLong.split(",");
        if(!AnalyticUtil.isValid(startcoords[1],startcoords[0])
                || !AnalyticUtil.isValid(endcoords[1],endcoords[0])){
            return null;

        }
        if (StringUtils.isBlank(startCity)) {
            startCity = "全国";
        }
        startCity = StringUtils.isNotBlank(startCity)?encode(ENCODER,startCity):encode(ENCODER,"全国");
        endCity = StringUtils.isNotBlank(endCity)?encode(ENCODER,endCity):encode(ENCODER,"全国");

        String urlKey="";
        if(MapUtil.MAP_BAIDU.equals(mapType)){

            urlKey = OrganizationCache.getBDConfigValue("getNavigate") +"&ak="+OrganizationCache.getAK()+"&coord_type="+coord_type
                    +"&origin="+startLatLong+"&destination="+endLatLong+"&origin_region="+startCity+"&destination_region="+endCity;
            String result = HttpUtils.hpptGetRequestByProxy(urlKey);
            return AnalyticUtil.getInstance().processNavigateJsonResp(result);
        }else {
            urlKey = OrganizationCache.getSWConfigValue("getDistince") + startcoords[1] + "," + startcoords[0] + "&ePoint=" + endcoords[1] + ","
                    + endcoords[0];
            String result = HttpUtils.hpptGetRequestByProxy(urlKey);
            return AnalyticUtil.getInstance().processNavigateJsonRespToSW(result);
        }

    }
}
