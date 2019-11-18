//package com.picc.common.utils;
//
//import com.picc.map.entity.*;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class BDMapUtil {
//
//    private static final Logger log = LoggerFactory.getLogger(BDMapUtil.class);
//
//    private static final String ENCODER = "UTF-8";
//
//    // 导航策略。导航路线类型，10，不走高速；11、最少时间；12、最短路径。
//    public static final int TACTICS_TIMElESS = 11;
//    public static final int TACTICS_PATHlESS = 12;
//
//    //
//    public static final int COORD2ADDR_POIS_DEFAULT = 0;
//    public static final int POISEARCH_SCOPE_DEFAULT = 1;
//
//    public static ConcurrentLinkedQueue<String> queue=new ConcurrentLinkedQueue<String>();
//
//    private static BDMapUtil instance=new BDMapUtil();
//    //获取实例
//    public static BDMapUtil getInstance() {
//        return instance;
//    }
//
//    private BDMapUtil() {
//
//    }
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 报文返回类型
//     * @param @param  pois 是否返回附近的poi点，0不返回，默认1返回
//     * @param @param  lng 经度
//     * @param @param  lat 纬度
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getCoord2AddrUrl
//     * @Description: 获取逆地理编码的请求url（坐标-》地址）
//     * @Author: MaXiao
//     */
//    private static String getCoord2AddrUrl(String ak, String respType,
//                                           int pois, double lng, double lat) {
//        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak
//                + "&output=" + respType + "&pois=" + pois + "&location=" + lat
//                + "," + lng+"&ret_coordtype=gcj02ll&coordtype=gcj02ll";
//        log.info("获取逆地理编码的请求url（坐标-》地址）=" + url);
//        return url;
//    }
//
//    private static String getCoord2AddrUrl(String ak, String respType,
//                                           int pois, double lng, double lat, String coordtype) {
//        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak
//                + "&output=" + respType + "&pois=" + pois + "&location=" + lat
//                + "," + lng+"&ret_coordtype="+coordtype+"&coordtype="+coordtype;
//        log.info("获取逆地理编码的请求url（坐标-》地址）=" + url);
//        return url;
//    }
//
//
//
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 报文返回的类型
//     * @param @param  addr 地址
//     * @param @param  region 搜索范围
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getAddr2CoordUrl
//     * @Description: 获取地理编码的请求url（地址-》坐标）
//     * @Author: MaXiao
//     */
//    private static String getAddr2CoordUrl(String ak, String respType,
//                                           String addr, String region) {
//        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak
//                + "&output=" + respType + "&address=" + addr + "&city="
//                + region+"&ret_coordtype=gcj02ll";
//        log.info("baidu-获取地理编码的请求url（地址-》坐标）=" + url);
//        return url;
//    }
//
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 报文返回类型
//     * @param @param  fromType 源坐标类型
//     * @param @param  toType 目标坐标类型
//     * @param @param  XYs (经度,纬度;经度,纬度)
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getCoord2BDCoordUrl
//     * @Description: 获取坐标偏移的请求url（默认从gps-》百度经纬度坐标）
//     * @Author: MaXiao
//     */
//    private static String getCoord2BDCoordUrl(String ak, String respType,
//                                              int fromType, int toType, String XYs) {
//        String url = "http://api.map.baidu.com/geoconv/v1/?ak=" + ak
//                + "&output=" + respType + "&from=" + fromType + "&to=" + toType
//                + "&coords=" + XYs;
//        log.info("baidu-获取坐标偏移的请求url（默认从gps-》百度经纬度坐标）=" + url);
//        return url;
//    }
//
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 报文返回类型
//     * @param @param  scope 检索结果详细程度 （默认1基本信息，2详细信息）
//     * @param @param  pageNum 分页页数（默认0）
//     * @param @param  pageSize 每页条数 （默认20）
//     * @param @param  key 关键字
//     * @param @param  region 搜索区域
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getPOIUrl
//     * @Description: POI搜索
//     * @Author: MaXiao
//     */
//    private static String getPOIUrl(String ak, String respType, int scope,
//                                    int pageNum, int pageSize, String key, String region) {
//        String url = "http://api.map.baidu.com/place/v2/search?ak=" + ak
//                + "&output=" + respType + "&scope=" + scope + "&page_num="
//                + pageNum + "&page_size=" + pageSize + "&query=" + key
//                + "&region=" + region +"&ret_coordtype=gcj02ll";
//        log.info("baidu-POI搜索url=" + url);
//        return url;
//    }
//
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 返回报文类型
//     * @param @param  addr 关键字
//     * @param @param  region 搜索区域
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getSuggestionUrl
//     * @Description: 录入提示url
//     * @Author: MaXiao
//     */
//    private static String getSuggestionUrl(String ak, String respType,
//                                           String addr, String region) {
//        String url = "http://api.map.baidu.com/place/v2/suggestion?ak=" + ak
//                + "&output=" + respType + "&query=" + addr + "&region="
//                + region;
//        log.info("baidu-录入提示url=" + url);
//        return url;
//    }
//
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 返回报文类型
//     * @param @param  coordType 坐标类型
//     * @param @param  tactics 导航策略。导航路线类型，10，不走高速；11、最少时间；12、最短路径。
//     * @param @param  startLng 起点经度
//     * @param @param  startLat 起点纬度
//     * @param @param  endLng 终点经度
//     * @param @param  endLat 终点纬度
//     * @param @param  startRegion 地点所在区域
//     * @param @param  endRegion 终点所在区域
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getNavigateUrl
//     * @Description: 详细导航url 带路网
//     * @Author: MaXiao
//     */
//    private static String getNavigateUrl(String ak, String respType,
//                                         int coordType, int tactics, double startLng, double startLat,
//                                         double endLng, double endLat, String startRegion, String endRegion) {
//        // bd09ll（百度经纬度坐标）、bd09mc（百度摩卡托坐标）、gcj02（国测局坐标，如google，soso地图均采用该坐标）、wgs84（gps设备获取的坐标）。
//        String coord_type = "bd09ll";
//        if (coordType == MapDefaultParams.COORDTYPE_BAIDU) {
//            coord_type = "bd09ll";
//        } else if (coordType == MapDefaultParams.COORDTYPE_GOOGLE_AMAP) {
//            coord_type = "gcj02";
//        } else if (coordType == MapDefaultParams.COORDTYPE_GPS) {
//            coord_type = "wgs84";
//        }
//        String url = "http://api.map.baidu.com/direction/v1?ak=" + ak
//                + "&output=" + respType + "&coord_type=" + coord_type
//                + "&tactics=" + tactics + "&origin=" + startLat + ","
//                + startLng + "&destination=" + endLat + "," + endLng
//                + "&origin_region=" + startRegion + "&destination_region="
//                + endRegion;
//        log.info("baidu-带路网详细导航url=" + url);
//        return url;
//    }
//
//    /**
//     * @param @param  ak 请求key
//     * @param @param  respType 返回报文类型
//     * @param @param  coordType 坐标类型
//     * @param @param  tactics 导航策略。导航路线类型，10，不走高速；11、最少时间；12、最短路径。
//     * @param @param  startLng 起点经度
//     * @param @param  startLat 起点纬度
//     * @param @param  endLng 终点经度
//     * @param @param  endLat 终点纬度
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getRouteUrl
//     * @Description: (简易导航不含路网)url
//     * @Author: MaXiao
//     */
//    private static String getRouteUrl(String ak, String respType,
//                                      int coordType, int tactics, double startLng, double startLat,
//                                      double endLng, double endLat) {
//        // bd09ll（百度经纬度坐标）、bd09mc（百度摩卡托坐标）、gcj02（国测局坐标，如google，soso地图均采用该坐标）、wgs84（gps设备获取的坐标）。
//        String coord_type = "bd09ll";
//        if (coordType == MapDefaultParams.COORDTYPE_BAIDU) {
//            coord_type = "bd09ll";
//        } else if (coordType == MapDefaultParams.COORDTYPE_GOOGLE_AMAP) {
//            coord_type = "gcj02";
//        } else if (coordType == MapDefaultParams.COORDTYPE_GPS) {
//            coord_type = "wgs84";
//        }
//        String url = "http://api.map.baidu.com/direction/v1/routematrix?ak="
//                + ak + "&output=" + respType + "&coord_type=" + coord_type
//                + "&tactics=" + tactics + "&origins=" + startLat + ","
//                + startLng + "&destinations=" + endLat + "," + endLng;
//        log.info("baidu-(简易导航不含路网)url=" + url);
//        return url;
//    }
//
//
//    /**
//     * 百度poi检索
//     *
//     * @param query
//     * @param region
//     * @param tag
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    public AddressCoord[] searchPOIResp(String query, String region,
//                                        String tag, int pageNum, int pageSize) throws UnsupportedEncodingException {
//        if (query == null || "".equals(query)) {
//            return null;
//        }
//        // 根据请求的分公司，获取百度地图请求KEY
//        String ak=getAK();
//        String poi = URLEncoder.encode(query, "UTF-8");//
//
//        if ("".equals(region)) {
//            region = URLEncoder.encode("全国", "UTF-8");
//        } else {
//            Pattern pattern = Pattern.compile("[0-9]*");
//            Matcher isNum = pattern.matcher(region);
//            if (!isNum.matches()) {// 非数字,传入的是中文
//                region = URLEncoder.encode(region, ENCODER);
//            }
//        }
//        String url = getPOIUrl(ak, MapDefaultParams.RESPTYPE_JSON, POISEARCH_SCOPE_DEFAULT, pageNum,
//                pageSize, poi, region);
//        String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//        log.info("baidu-搜索POI resp=" + result);
//        AddressCoord[] coors = processAddr2CoordJsonResp(result);
//
//        return coors;
//    }
//    /**
//     * 获取ak
//     * @return
//     */
//    private String getAK(){
//        String ak=queue.poll();
//        if(ak==null){
//            String AK = SystemConfigManager.getInstance().getBDMapKey("mapKey");
//            String[] aks = AK.split(",");
//            for (int i = 0; i < aks.length; i++) {
//                queue.offer(aks[i]);
//            }
//            ak=queue.poll();
//            queue.offer(ak);
//        }else{
//            queue.offer(ak);
//        }
//        return ak;
//    }
//    /**
//     * @param resp
//     * @return
//     */
//    private AddressCoord[] processAddr2CoordJsonResp(String resp) {
//        JSONObject poijson = JSONObject.fromObject(resp);
//        if (poijson.has("status") && "0".equals(poijson.getString("status"))) {
//            if (poijson.has("results")) {
//                JSONArray resultsJson = poijson.getJSONArray("results");
//                int index = resultsJson.size();
//                AddressCoord[] addrCoords = new AddressCoord[index];
//                for (int i = 0; i < index; i++) {
//                    AddressCoord addrCoord = new AddressCoord();
//                    JSONObject resultJson = resultsJson.getJSONObject(i);
//                    if (resultJson.has("location")) {
//                        JSONObject locationJson = resultJson.getJSONObject("location");
//                        if (locationJson.has("lat")
//                                && locationJson.has("lng")) {
//                            addrCoord.latitude = locationJson.getDouble("lat");
//                            addrCoord.longitude = locationJson.getDouble("lng");
//                        }
//                    }
//                    if (resultJson.has("name")) {
//                        addrCoord.name = resultJson.getString("name");
//                    }
//                    if (resultJson.has("address")) {
//                        addrCoord.address = resultJson.getString("address");
//                    }
//                    addrCoords[i] = addrCoord;
//                }
//                return addrCoords;
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * @param @param  lng 经度
//     * @param @param  lat 纬度
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: coord2AddrResp
//     * @Description: 坐标转地址 逆地理编码
//     * @Author:
//     */
//    public Coord2Addr coord2AddrResp(Double lng, Double lat) {
//        return coord2AddrResp(lng, lat, null);
////        try {
////            if (lng == null || lat == null) {
////                return null;
////            }
////            // 根据请求的分公司，获取百度地图请求KEY
////            String AK = SystemConfigManager.getInstance().getBDMapKey("mapKey");
////            String[] aks = AK.split(",");
////            String ak = aks[0];
////            String url = getCoord2AddrUrl(ak, MapDefaultParams.RESPTYPE_JSON, POISEARCH_SCOPE_DEFAULT,
////                    lng, lat);
////            String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
////            log.info("baidu-坐标转地址 resp=" + result);
////            System.out.println("===="+result);
////            Coord2Addr coord = processCoord2AddrJsonResp(result);
////            return coord;
////        } catch (Exception e) {
////            log.error("编码失败：" + lng + "," + lat + " message:" + e.getMessage());
////            e.printStackTrace();
////        }
////        return null;
//
//    }
//
//    public Coord2Addr coord2AddrResp(Double lng, Double lat, String coordtype) {
//        try {
//            if (lng == null || lat == null) {
//                return null;
//            }
//            // 根据请求的分公司，获取百度地图请求KEY
//            String ak = getAK();
//            String url = "";
//            if(StringUtils.isNotEmpty(coordtype)){
//                coordtype = CoordType.getName(Integer.valueOf(coordtype));
//                url = getCoord2AddrUrl(ak, MapDefaultParams.RESPTYPE_JSON, POISEARCH_SCOPE_DEFAULT,
//                        lng, lat, coordtype);
//            }else{
//                url = getCoord2AddrUrl(ak, MapDefaultParams.RESPTYPE_JSON, POISEARCH_SCOPE_DEFAULT,
//                        lng, lat);
//            }
//            String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//            log.info("baidu-坐标转地址 resp=" + result);
//            Coord2Addr coord = processCoord2AddrJsonResp(result);
//            return coord;
//        } catch (Exception e) {
//            log.error("编码失败：" + lng + "," + lat + " message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    /**
//     * @param @param  resp
//     * @param @return
//     * @return AddressCoord
//     * @throws
//     * @Title: processCoord2AddrJsonResp
//     * @Description: 处理坐标转地址json string-》地址列表
//     * @Author:
//     */
//    private Coord2Addr processCoord2AddrJsonResp(String resp) {
//
//        JSONObject addrJson = JSONObject.fromObject(resp);
//        if (!addrJson.has("status")|| !"0".equals(addrJson.getString("status"))) {
//            return null;
//        }
//        if (addrJson.has("result")) {
//            Coord2Addr addr = new Coord2Addr();
//            JSONObject resultJson = addrJson.getJSONObject("result");
//
//            if (resultJson.has("formatted_address")) {
//                addr.formatted_address = resultJson.getString("formatted_address");
//            }
//            if (resultJson.has("addressComponent")) {
//                JSONObject addressComponentJson = resultJson.getJSONObject("addressComponent");
//                if (addressComponentJson.has("province")) {
//                    addr.province = addressComponentJson.getString("province");
//                }
//                if (addressComponentJson.has("city")) {
//                    addr.city = addressComponentJson.getString("city");
//                }
//                if (addressComponentJson.has("district")) {
//                    addr.district = addressComponentJson.getString("district");
//                }
//                if (addressComponentJson.has("distance")) {
//                    addr.distance = StringUtils.isNotEmpty(addressComponentJson.getString("distance")) ==true?addressComponentJson.getString("distance"):"0.0";
//                }
//            }
//            if (resultJson.has("location")) {
//                JSONObject locationJson = resultJson.getJSONObject("location");
//                if (locationJson.has("lng")) {
//                    addr.longitude = locationJson.getDouble("lng");
//                }
//                if (locationJson.has("lat")) {
//                    addr.latitude = locationJson.getDouble("lat");
//                }
//            }
//            if(resultJson.has("pois")){
//                JSONArray poisJson = resultJson.getJSONArray("pois");
//                int index = poisJson.size();
//                Poi[] pois = new Poi[index];
//                for (int i = 0; i < index; i++) {
//                    Poi poi = new Poi();
//                    JSONObject resultJson1 = poisJson.getJSONObject(i);
//                    if (resultJson1.has("addr")) {
//                        poi.setAddr(resultJson1.getString("addr"));
//                    }
//                    if (resultJson1.has("direction")) {
//                        poi.setDirection(resultJson1.getString("direction"));
//                    }
//                    if (resultJson1.has("distance")) {
//                        poi.setDistance(resultJson1.getString("distance"));
//                    }
//                    if (resultJson1.has("name")) {
//                        poi.setName(resultJson1.getString("name"));
//                    }
//                    if (resultJson1.has("poiType")) {
//                        poi.setPoiType(resultJson1.getString("poiType"));
//                    }
//                    if (resultJson1.has("point")) {
//                        JSONObject pointJson = resultJson1.getJSONObject("point");
//                        poi.setX(pointJson.getDouble("x"));
//                        poi.setY(pointJson.getDouble("y"));
//                    }
//                    if (resultJson1.has("point")) {
//                        poi.setTag(resultJson1.getString("tag"));
//                    }
//                    pois[i] = poi;
//                }
//                addr.pois = pois;
//            }
//
//            return addr;
//        }
//        return null;
//    }
//
//    /**
//     * @param @param  region
//     * @param @param  address 地址
//     * @param @return
//     * @return AddrCoord 坐标
//     * @throws
//     * @Title: addr2Coord
//     * @Description: 地址转坐标返回的坐标列表
//     * @Author:
//     */
//    public AddrCoord addr2Coord(String address, String region) {
//        try {
//            // 根据请求的分公司，获取百度地图请求KEY
//            String ak = getAK();
//            String addr = URLEncoder.encode(address, "UTF-8");
//            if ("".equals(region)) {
//                region = URLEncoder.encode("全国", ENCODER);
//            } else {
//                Pattern pattern = Pattern.compile("[0-9]*");
//                Matcher isNum = pattern.matcher(region);
//                if (!isNum.matches()) {// 非数字,传入的是中文
//                    region = URLEncoder.encode(region, ENCODER);
//                }
//            }
//            String url = getAddr2CoordUrl(ak, MapDefaultParams.RESPTYPE_JSON, addr, region);
//            String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//            log.info("baidu-地址转坐标 resp=" + result);
//            AddrCoord coords = processAddrToCoordJsonResp(result);
//            return coords;
//
//        } catch (UnsupportedEncodingException e) {
//            log.error("编码失败：" + address + " message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * @param @param  CoordString
//     * @param @return
//     * @return AddrCoord[]
//     * @throws
//     * @Title: processAddrToCoordJsonResp
//     * @Description: 处理地址转坐标返回的json string--》坐标列表
//     * @Author:
//     */
//    private AddrCoord processAddrToCoordJsonResp(String coordJsonStr) {
//        if (coordJsonStr != null) {
//            JSONObject coordJson = JSONObject.fromObject(coordJsonStr);
//            if (coordJson.has("status")
//                    && "0".equals(coordJson.getString("status"))) {
//                if (coordJson.has("result")) {
//                    AddrCoord coord = new AddrCoord();
//                    JSONObject resultJson = coordJson.getJSONObject("result");
//                    if (resultJson.has("location")) {
//                        JSONObject locationJson = resultJson
//                                .getJSONObject("location");
//                        if (locationJson.has("lat") && locationJson.has("lng")) {
//                            coord.latitude = locationJson.getDouble("lat");
//                            coord.longitude = locationJson.getDouble("lng");
//                            return coord;
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * @param @param  startRegion
//     * @param @param  endRegion
//     * @param @param  startLng 起点经度
//     * @param @param  startLat 起点纬度
//     * @param @param  endLng 终点经度
//     * @param @param  endLat 终点纬度
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: navigateResp
//     * @Description: 导航两点路径返回报文(详细导航含路网信息)
//     * @Author:
//     */
//    public String navigateResp(String startRegion, String endRegion, Double startLng, Double startLat, Double endLng,
//                               Double endLat) {
//        try {
//            // 根据请求的分公司，获取百度地图请求KEY
//            String ak = SystemConfigManager.getInstance().getBDMapKey("mapKey");
//            String sregion = URLEncoder.encode(startRegion, ENCODER);
//            if ("".equals(sregion)) {
//                sregion = URLEncoder.encode("全国", ENCODER);
//            }
//            String eregion = URLEncoder.encode(endRegion, ENCODER);
//            if ("".equals(eregion)) {
//                eregion = URLEncoder.encode("全国", ENCODER);
//            }
//            String url = getNavigateUrl(ak, MapDefaultParams.RESPTYPE_JSON, MapDefaultParams.COORDTYPE_BAIDU,
//                    TACTICS_TIMElESS, startLng, startLat, endLng, endLat,
//                    sregion, eregion);
//            String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//            log.info("baidu-详细导航两点路径 resp=" + result);
//            return result;
//        } catch (UnsupportedEncodingException e) {
//            log.error("编码失败： message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * @param @param  navResp
//     * @param @return
//     * @return Navigate[]
//     * @throws
//     * @Title: processNavigateJsonResp
//     * @Description: 百度导航返回解析
//     * @Author:
//     */
//    public Navigate[] processNavigateJsonResp(String navResp) {
//        Navigate[] navigates = new Navigate[0];
//        if (navResp != null && !"".equals(navResp)) {
//            JSONObject navJson = JSONObject.fromObject(navResp);
//            if (navJson.has("status")
//                    && "0".equals(navJson.getString("status"))) {
//                if (navJson.has("result")) {
//                    JSONObject resultJson = navJson.getJSONObject("result");
//                    if (resultJson.has("routes")) {
//                        JSONArray routesJson = resultJson
//                                .getJSONArray("routes");
//                        int index = routesJson.size();
//                        for (int i = 0; i < index; i++) {
//                            JSONObject routeJson = routesJson.getJSONObject(i);
//                            if (routeJson.has("steps")) {
//                                JSONArray stepsJson = JSONArray.fromObject(routeJson.getJSONArray("steps"));
//                                int sindex = stepsJson.size();
//                                navigates = new Navigate[sindex];
//                                for (int j = 0; j < sindex; j++) {
//                                    JSONObject stepJson = stepsJson.getJSONObject(j);
//                                    Navigate navigate = new Navigate();
//                                    if (stepJson.has("distance")) {
//                                        navigate.setDistance(stepJson.getDouble("distance"));
//                                    }
//                                    if (stepJson.has("duration")) {
//                                        navigate.setDuration(stepJson.getDouble("duration"));
//                                    }
//                                    if (stepJson.has("instructions")) {
//                                        String instructions = HtmlUtil.getTextFromHtml(stepJson.getString("instructions"));
//                                        navigate.setInstruction(instructions);
//                                    }
//                                    if (stepJson.has("path")) {
//                                        navigate.setPath(stepJson.getString("path"));
//                                    }
//                                    if (stepJson.has("turn")) {
//                                        navigate.setTurn(stepJson.getString("turn"));
//                                    }
//                                    navigates[j] = navigate;
//                                }
//                            }
//                        }
//                        return navigates;
//                    }
//                }
//            }
//        }
//        return navigates;
//    }
//
//    /**
//     * @param @param  startRegion
//     * @param @param  endRegion
//     * @param @param  startLng 起点经度
//     * @param @param  startLat 起点纬度
//     * @param @param  endLng 终点经度
//     * @param @param  endLat 终点纬度
//     * @param @return
//     * @return int[]
//     * @throws
//     * @Title: navigateResp
//     * @Description: 导航两点路径(详细导航含路网信息)返回路径长度(米)
//     * @Author:
//     */
//    public Navigate[] navigate(String startRegion, String endRegion, Double startLng,
//                               Double startLat, Double endLng, Double endLat) {
//        try {
//            if (startLng == null || startLat == null
//                    || endLng == null || endLat == null) {
//                return null;
//            }
//            // 根据请求的分公司，获取百度地图请求KEY
//            String ak = getAK();
//            String sregion = URLEncoder.encode(startRegion, ENCODER);
//            if ("".equals(sregion)) {
//                sregion = URLEncoder.encode("全国", ENCODER);
//            }
//            String eregion = URLEncoder.encode(endRegion, ENCODER);
//            if ("".equals(eregion)) {
//                eregion = URLEncoder.encode("全国", ENCODER);
//            }
//            String url = getNavigateUrl(ak, MapDefaultParams.RESPTYPE_JSON,
//                    MapDefaultParams.COORDTYPE_BAIDU, TACTICS_TIMElESS, startLng, startLat,
//                    endLng, endLat, sregion, eregion);
//            String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//            log.info("baidu-详细导航两点路径 resp=" + result);
//            Navigate[] navigates = processNavigateJsonResp(result);
//            return navigates;
//        } catch (UnsupportedEncodingException e) {
//            log.error("编码失败： message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * @param @param  startLng 起点经度
//     * @param @param  startLat 起点纬度
//     * @param @param  endLng 终点经度
//     * @param @param  endLat 终点纬度
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: routeResp
//     * @Description: 导航两点间路径(简易导航不含路网)
//     * @Author: MaXiao
//     */
//    public String routeResp(Double startLng, Double startLat, Double endLng, Double endLat) {
//        if (startLng == null || startLat == null
//                || endLng == null || endLat == null) {
//            return null;
//        }
//        // 根据请求的分公司，获取百度地图请求KEY
//        String ak = SystemConfigManager.getInstance().getBDMapKey("mapKey");
//        String url = getRouteUrl(ak, MapDefaultParams.RESPTYPE_JSON, MapDefaultParams.COORDTYPE_BAIDU,
//                TACTICS_TIMElESS, startLng, startLat, endLng, endLat);
//        String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//        log.info("baidu-简易导航两点路径 resp=" + result);
//        return result;
//    }
//
//    /**
//     * @param @param  routeResp
//     * @param @return
//     * @return int[]
//     * @throws
//     * @Title: processRouteJsonResp
//     * @Description: 处理两点间路径导航(简易导航不含路网)的json string ->路径长度(米)
//     * @Author:
//     */
//    private NavigateDistance processRouteJsonResp(String routeResp) {
//        if (routeResp != null && !"".equals(routeResp)) {
//            JSONObject routeJson = JSONObject.fromObject(routeResp);
//            if (routeJson.has("status") && "0".equals(routeJson.getString("status"))) {
//                if (routeJson.has("result")) {
//                    JSONObject resultJson = routeJson.getJSONObject("result");
//                    if (resultJson.has("elements")) {
//                        NavigateDistance dis = new NavigateDistance();
//                        JSONArray elementsJson = resultJson.getJSONArray("elements");
//                        int index = elementsJson.size();
//                        for (int i = 0; i < index; i++) {
//                            JSONObject elementJson = elementsJson.getJSONObject(i);
//                            if (elementJson.has("distance")) {
//                                JSONObject distanceJson = elementJson.getJSONObject("distance");
//                                if (distanceJson.has("value")) {
//                                    dis.distance = distanceJson.getDouble("value");
//                                }
//                            }
//                            break;
//                        }
//                        return dis;
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * @param @param  startLng 起点经度
//     * @param @param  startLat 起点纬度
//     * @param @param  endLng 终点经度
//     * @param @param  endLat 终点纬度
//     * @param @return
//     * @return int[]
//     * @throws
//     * @Title: routeResp
//     * @Description: 导航两点间路径(简易导航不含路网)-->路径长度
//     * @Author:
//     */
//    public NavigateDistance route(Double startLng, Double startLat, Double endLng, Double endLat) {
//        if (startLng == null || startLat == null
//                || endLng == null || endLat == null) {
//            return null;
//        }
//        // 根据请求的分公司，获取百度地图请求KEY
//        String ak =getAK();
//        String url = getRouteUrl(ak, MapDefaultParams.RESPTYPE_JSON,
//                MapDefaultParams.COORDTYPE_GOOGLE_AMAP, TACTICS_TIMElESS, startLng, startLat, endLng,
//                endLat);
//        String result = HttpUtils.hpptGetRequestByProxy(url, ENCODER);
//        log.info("baidu-简易导航两点路径 resp=" + result);
//
//        NavigateDistance routes = processRouteJsonResp(result);
//        return routes;
//
//    }
//
//    /**
//     *  @Author: Qws
//     *  @Description: 地图偏移 gps->baidu
//     *  @Date:14:25 2017/11/30
//     * @param coords
//     * @param ak
//     **/
//    public static List<CoordOffset> gps_to_bd(String coords,String ak){
//        String castCoord = coords.substring(0, coords.lastIndexOf(";"));
//        CoordOffset[] coordOffsets = MapToBaidu(ak, 1, castCoord);
//        List<CoordOffset> baiduList = Arrays.asList(coordOffsets);
//        return  baiduList;
//    }
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
//    /**
//     *  @Author: Qws
//     *  @Description: 地图偏移 gaode->baidu
//     *  @Date:14:25 2017/11/30
//     * @param coords
//     **/
//    public static List<CoordOffset> gd_to_bd(String[] coords){
//        List<CoordOffset> gaodeltList = new ArrayList<CoordOffset>();
//        for (int i = 0; i < coords.length; i++) {
//            CoordOffset coord = new CoordOffset();
//            String[] sp = coords[i].split(",");
//            //验证坐标有效性
//            if (MapUtil.isValid(Double.parseDouble(sp[0]), Double.parseDouble(sp[1]))) {
//                //有效 处理
//                double[] doubles = GPSUtil.gcj02_To_Bd09(Double.parseDouble(sp[1]), Double.parseDouble(sp[0]));
//                coord.setLatitude(doubles[0]);
//                coord.setLongitude(doubles[1]);
//                gaodeltList.add(coord);
//            } else {
//                //无效 不处理
//                coord.setLongitude(Double.parseDouble(sp[0]));
//                coord.setLatitude(Double.parseDouble(sp[1]));
//                gaodeltList.add(coord);
//            }
//        }
//        return gaodeltList;
//    }
//
//
//}
