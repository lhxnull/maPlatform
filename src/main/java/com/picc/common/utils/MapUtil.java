package com.picc.common.utils;
//
//import com.picc.map.entity.Constant;
//import com.picc.map.entity.CoordOffset;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.List;
//
public class MapUtil {
//
//
//
//    private static final Logger log = LoggerFactory.getLogger(MapUtil.class);

    public static final String MAP_BAIDU="baidu";
    public static final String MAP_GAODE="gaode";
    public static final String MAP_SIWEI="siwei";
    public static final String MAP_GPS="gps";
    public static final String BD_0911="bd09ll";
    public static final String GCJ_02="gcj02";
    public static final String WGS_84="wgs84";
//
//
//    /**
//     * 判断坐标是否有效
//     *
//     * @param longitude
//     * @param latitude
//     * @return
//     */
//    public static boolean isValid(Double longitude, Double latitude) {
//        if (longitude == null || latitude == null) {
//            return false;
//        }
//        if (longitude > 73.55 && longitude < 135.08333333333333333 && latitude > 3.85 && latitude < 53.55) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @param mapType
//     * @param fromType
//     * @Author: Qws
//     * @Description: 判断坐标类型是否属于 baidu  gaode  gps;
//     * @Date:13:45 2017/10/31
//     **/
//    public static boolean hasMap(String mapType, String fromType) {
//        boolean flag = false;
//        if (StringUtils.isBlank(mapType) || StringUtils.isBlank(fromType)) {
//            return false;
//        }
//        if (Constant.MAP_BAIDU.equals(mapType) || Constant.MAP_GAODE.equals(mapType) || Constant.MAP_SW.equals(mapType)) {
//            flag = true;
//        } else {
//            return  false;
//        }
//        if (Constant.MAP_GAODE.equals(fromType) || Constant.MAP_BAIDU.equals(fromType) || Constant.MAP_GPS.equals(fromType) || Constant.MAP_SW.equals(fromType)) {
//            flag = true;
//        } else {
//            return  false;
//        }
//        return flag;
//    }
//
//    /**
//     * @param ak
//     * @param fromType
//     * @param XYs
//     * @Author: Qws
//     * @Description: 其他类型转换成百度类型
//     * @Date:14:29 2017/10/31
//     **/
//    public static CoordOffset[] MapToBaidu(String ak, int fromType, String XYs) {
//        String url = "http://api.map.baidu.com/geoconv/v1/?ak=" + ak
//                + "&output=json&from=" + fromType + "&to=5&coords=" + XYs;
//        log.info("baidu-获取坐标偏移的请求url（默认从gps-》百度经纬度坐标）=" + url);
//        System.out.println(url);
//        String result = HttpUtils.hpptGetRequestByProxy(url, "UTF-8");
//        CoordOffset[] coordOffsets = CoordOffsetJsonResp(result);
//        return coordOffsets;
//    }
//
//    private static CoordOffset[] CoordOffsetJsonResp(String resp) {
//        if (resp != null) {
//            JSONObject resultJson = JSONObject.fromObject(resp);
//            if (resultJson.has("status")
//                    && "0".equals(resultJson.getString("status"))) {
//                // 解析json格式不同
//                // "{status : 0,result :[{x : 114.23075114005,y : 29.579088074495}]}";
//                if (resultJson.has("result")) {
//                    JSONArray coordJsonArray = resultJson
//                            .getJSONArray("result");
//                    int index = coordJsonArray.size();
//                    CoordOffset[] coords = new CoordOffset[index];
//                    for (int i = 0; i < index; i++) {
//                        CoordOffset coord = new CoordOffset();
//                        JSONObject coordJson = coordJsonArray.getJSONObject(i);
//                        if (coordJson.has("x") && coordJson.has("y")) {
//                            coord.setLongitude(coordJson.getDouble("x"));
//                            coord.setLatitude(coordJson.getDouble("y"));
//                        }
//                        coords[i] = coord;
//                    }
//                    return coords;
//                }
//            }
//        }
//        return null;
//    }
//
//
//    public static void main(String[] args) {
//        CoordOffset[] coordOffsets = MapUtil.MapToBaidu("fIVvbjUzX45IiGSZuVtz6b5CFHhWZOY2", 5, "119.034692,31.657237;119.034692,31.657237");
//        List<CoordOffset> strings = Arrays.asList(coordOffsets);
//        System.out.println(strings.toString());
//
//    }
//
}
