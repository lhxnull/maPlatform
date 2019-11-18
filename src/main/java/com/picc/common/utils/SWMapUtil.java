//package com.picc.common.utils;
//
//import com.picc.map.entity.*;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//
//public class SWMapUtil {
//    private static final Logger log = LoggerFactory.getLogger(SWMapUtil.class);
//
//    private static final String ENCODER = "UTF-8";
//
//    private static final String DETAIL = "1";
//
//    private static final String ZOOM = "11";
//
//    public static ConcurrentLinkedQueue<String> queue=new ConcurrentLinkedQueue<String>();
//
//    private static SWMapUtil instance=new SWMapUtil();
//
//    private static SystemConfigManager configManager = SystemConfigManager.getInstance();
//
//    //获取实例
//    public static SWMapUtil getInstance() {
//        return instance;
//    }
//
//    private SWMapUtil() {
//
//    }
//    public AddressCoord[] searchPOIRespSW(String query, String region, String tag, int pageNum, int pageSize) {
//
//        try {
//            if (query == null || "".equals(query)) {
//                return null;
//            }
//            // 根据请求的分公司，获取百度地图请求KEY
//            String keyWord = URLEncoder.encode(query, ENCODER);
//
//            if ("".equals(region)) {
//                region = URLEncoder.encode("全国", ENCODER);
//            } else {
//                Pattern pattern = Pattern.compile("[0-9]*");
//                Matcher isNum = pattern.matcher(region);
//                if (!isNum.matches()) {// 非数字,传入的是中文
//                    region = URLEncoder.encode(region, ENCODER);
//                }
//            }
//            String url = getPOIUrl(pageNum,pageSize, keyWord, region);
//            String result = HttpUtils.hpptGetRequest(url, ENCODER);
//            log.info("四维-搜索POI resp=" + result);
//            AddressCoord[] coors = processAddr2CoordJsonResp(result);
//
//            return coors;
//        } catch (UnsupportedEncodingException e) {
//            log.error("编码失败：" + query + " message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//    /**
//     * @param @param  pageNum 分页页数（默认0）
//     * @param @param  pageSize 每页条数 （默认20）
//     * @param @param  keyWord 关键字
//     * @param @param  region 搜索区域
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getPOIUrl
//     * @Description: POI搜索
//     * @Author: MaXiao
//     */
//    private static String getPOIUrl(int pageNum, int pageSize, String keyWord, String region) {
//        String url = configManager.getSWUrl("searchpoi") + keyWord +"&pageCount="
//                + pageSize + "&pageNumber=" + pageNum + "&city=" + region ;
//        log.info("四维-POI搜索url=" + url);
//        return url;
//    }
//    /**
//     * @param @param  pageNum 分页页数（默认0）
//     * @param @param  pageSize 每页条数 （默认20）
//     * @param @param  keyWord 关键字
//     * @param @param  region 搜索区域
//     * @param @return
//     * @return String
//     * @throws
//     * @Title: getPOIUrl
//     * @Description: POI搜索
//     * @Author: MaXiao
//     */
//    public  String getInputsuggestionUrl() {
//
//        return  configManager.getSWUrl("inputsuggestion");
//    }
//
//    /**四维地图解析返回参数
//     * @param resp
//     * @return
//     */
//    private AddressCoord[] processAddr2CoordJsonResp(String resp) {
//        JSONObject poijson = JSONObject.fromObject(resp);
//        if (poijson.has("errcode")&&"0".equals(poijson.getString("errcode")) ) {
//            if (poijson.has("data")) {
//                JSONObject dataJson = poijson.getJSONObject("data");
//                JSONArray resultsJson = dataJson.getJSONArray("rows");
//                int index = resultsJson.size();
//                AddressCoord[] addrCoords = new AddressCoord[index];
//                for (int i = 0; i < index; i++) {
//                    AddressCoord addrCoord = new AddressCoord();
//                    JSONObject resultJson = resultsJson.getJSONObject(i);
//                    String locationJson = resultJson .getString("location");
//                    if (org.apache.commons.lang.StringUtils.isNotBlank(locationJson)) {
//                        addrCoord.longitude = Double.parseDouble(locationJson.split(",")[0]);
//                        addrCoord.latitude = Double.parseDouble(locationJson.split(",")[1]);
//                    }
//
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
//    /**逆地理编码
//     * @return
//     */
//    public Coord2Addr coord2AddrResp(Double lng, Double lat) {
//        try {
//            if (lng == null || lat == null) {
//                return null;
//            }
//            String url = getCoord2AddrUrl(lng,lat);
//            String result = HttpUtils.hpptGetRequest(url, ENCODER);
//            log.info("siwei-坐标转地址 resp=" + result);
//            Coord2Addr coord = processCoord2AddrJsonResp(result);
//            return coord;
//        } catch (Exception e) {
//            log.error("编码失败：" + lng + "," + lat + " message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//    /**
//     * 获取Coord2AddrUrl(逆地理编码)
//     * */
//    private String getCoord2AddrUrl(Double lng, Double lat) {
//        String url = configManager.getSWUrl("coord2addr")+lng+","+lat;
//        log.info("获取逆地理编码的请求url（坐标-》地址）=" + url);
//        return url;
//    }
//
//    /**
//     * @return AddressCoord
//     * @throws
//     * @Title: processCoord2AddrJsonResp
//     * @Description: 处理坐标转地址json string-》地址列表
//     * @Author:
//     */
//    private Coord2Addr processCoord2AddrJsonResp(String result) {
//        if(result != null){
//            Coord2Addr addr = new Coord2Addr();
//            //转换成json对象
//            JSONObject resultJson = JSONObject.fromObject(result);
//            if (resultJson.has("errcode")&&"0".equals(resultJson.getString("errcode"))) {
//                JSONObject datatJson = resultJson.getJSONObject("data");
//                //formatted_address
//                String[] fStrings = {"province","city","dist","town","village","roadname","restName"};
//                StringBuilder formatted_address = new StringBuilder();
//                for (int i = 0; i < fStrings.length; i++) {
//                    if(datatJson.has(fStrings[i])){
//                        if (!"直辖市".equals(datatJson.getString(fStrings[i]))) {
//                            formatted_address.append(datatJson.getString(fStrings[i]));
//                        }
//                    }
//                }
//                addr.formatted_address = formatted_address.toString();
//                // city
//                if(datatJson.has("city")){
//                    addr.city = datatJson.getString("city");
//                }
//                // province
//                if(datatJson.has("province")){
//                    addr.province = datatJson.getString("province");
//                }
//                //district
//                if(datatJson.has("dist")){
//                    addr.district = datatJson.getString("dist");
//                }
//                //distance
//                if(resultJson.has("distance")){
//                    addr.distance = datatJson.getString("distance");
//                }
//            }
//
//            return addr;
//        }
//        return null;
//    }
//
//    /**
//     * 四维导航
//     * @param startLng 起点经度
//     * @param startLat 起点纬度
//     * @param endLng 终点经度
//     * @param endLat 终点纬度
//     * @return Navigate[]
//     * @Title: navigate
//     */
//    public Navigate[] navigate(Double startLng, Double startLat, Double endLng,
//                               Double endLat) {
//        try {
//            if (startLng == null || startLat == null
//                    || endLng == null || endLat == null) {
//                return null;
//            }
//            String url = getNavigateUrl(startLng, startLat, endLng, endLat);
//            String result = HttpUtils.hpptGetRequest(url, ENCODER);
//            log.info("siwei-详细导航两点路径 resp=" + result);
//            Navigate[] navigates = processNavigateJsonResp(result);
//            return navigates;
//        } catch (Exception e) {
//            log.error("异常：", e);
//            log.error("导航失败：" + "&x1=" + startLng + "&y1="
//                    + startLat + "&x2=" + endLng + "&y2="
//                    + endLat + " message:" + e.getMessage());
//        }
//        return null;
//    }
//
//    /**
//     * 四维导航url
//     * @param startLng 起点经度
//     * @param startLat 起点纬度
//     * @param endLng 终点经度
//     * @param endLat 终点纬度
//     */
//    private String getNavigateUrl(Double startLng, Double startLat,
//                                  Double endLng, Double endLat) {
//        // String url = "http://10.133.216.173/service/route/driving2?"
//        // + "token=25cc55a69ea742218ffa93&source=1"
//        // + "&sPoint="+ startLng +","+ startLat +"&ePoint="+ endLng +","+
//        // endLat
//        // + "&st=4";
//        String url = configManager.getSWUrl("navigation")
//                + startLng + "," + startLat + "&ePoint=" + endLng + ","
//                + endLat;
//        log.info("siwei-带路网详细导航url=" + url);
//        return url;
//    }
//
//    /**
//     * @param result
//     * @return Navigate[]
//     * @Title: processNavigateJsonResp
//     * @Description: 四维导航返回解析
//     * @Author:
//     */
//    private Navigate[] processNavigateJsonResp(String result) {
//        Navigate[] navigates = new Navigate[0];
//        if(result != null){
//            JSONObject resultJson = JSONObject.fromObject(result);
//            if (resultJson.has("errcode") && "0".equals(resultJson.getString("errcode"))) {
//                if (resultJson.has("data")) {
//                    JSONObject dataJson = resultJson.getJSONObject("data");
//                    if (dataJson.has("rows")) {
//                        JSONArray rowsJson = dataJson.getJSONArray("rows");
//                        int index = rowsJson.size();
//                        for (int i = 0; i < index; i++) {
//                            JSONObject rowJson = rowsJson.getJSONObject(i);
//                            if (rowJson.has("item")) {
//                                JSONArray itemsJson = rowJson.getJSONArray("item");
//                                int iIndex = itemsJson.size();
//                                navigates = new Navigate[iIndex];
//                                for (int j = 0; j < iIndex; j++) {
//                                    JSONObject itemJson = itemsJson.getJSONObject(j);
//                                    Navigate navigate = new Navigate();
//                                    if (itemJson.has("strguide")) {
//                                        navigate.setInstruction(itemJson.getString("strguide"));
//                                    }
//                                    if (itemJson.has("distance")) {
//                                        navigate.setDistance(itemJson.getDouble("distance"));
//                                    }
//                                    if (itemJson.has("routelatlon")) {
//                                        navigate.setPath(itemJson.getString("routelatlon"));
//                                    }
//                                    if (itemJson.has("turncode")) {
//                                        navigate.setTurn(itemJson.getString("turncode"));
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
//        return null;
//    }
//
//    /**
//     * 位置偏移
//     * @param xys   gps->sw
//     * @return
//     */
//    private static List<CoordOffset> coordOffset(String xys)
//    {
//        String url = "http://10.133.216.176:8007/decode/encode_xml.jsp?lonlat=" + xys;
//        String result = HttpUtils.hpptGetRequest(url, ENCODER);
//        CoordOffset[] coordOffsets = CoordOffsetJsonResp(result);
//        List<CoordOffset> swList = Arrays.asList(coordOffsets);
//        return swList;
//    }
//
//    /**
//     * 位置偏移返回解析
//     * @param
//     * @return
//     */
//    private static CoordOffset[] CoordOffsetJsonResp(String resp) {
//        if (resp != null) {
//            JSONObject resultJson = JSONObject.fromObject(resp);
//            if (resultJson.has("item")) {
//                JSONObject fromObject = JSONObject.fromObject(resultJson.getString("item"));
//                JSONArray lonlats = fromObject.getJSONArray("lonlat");
//                int index = lonlats.size();
//                CoordOffset[] coords = new CoordOffset[index];
//                for (int i = 0; i < index; i++) {
//                    String lonlat = lonlats.getJSONObject(i)
//                            .getString("lonlat");
//                    String[] lonlata = lonlat.split(",");
//                    if (lonlata.length > 0) {
//                        CoordOffset coord = new CoordOffset();
//                        coord.setLongitude(Double.valueOf(lonlata[0]));
//                        coord.setLatitude(Double.valueOf(lonlata[1]));
//                        coords[i] = coord;
//                    }
//                }
//                return coords;
//            }
//        }
//        return null;
//    }
//
//    /**
//     *  @Description: gps坐标转换sw坐标
//     *  @param coords
//     **/
//    public static List<CoordOffset> gps_to_sw(String coords){
//        List<CoordOffset> coordOffsets = coordOffset(coords);
//        return coordOffsets;
//    }
//
//    /**
//     * 四维地理编码请求
//     * @param address
//     * @param city
//     * @return
//     */
//    public AddrCoord addr2Coord(String address, String city) {
//        try {
//            // 根据请求的分公司，获取百度地图请求KEY
//            String addr = URLEncoder.encode(address, "UTF-8");
//            if ("".equals(city)) {
//                city = URLEncoder.encode("全国", ENCODER);
//            } else {
//                Pattern pattern = Pattern.compile("[0-9]*");
//                Matcher isNum = pattern.matcher(city);
//                if (!isNum.matches()) {// 非数字,传入的是中文
//                    city = URLEncoder.encode(city, ENCODER);
//                }
//            }
//            String url = getAddr2CoordUrl(addr, city);
//            String result = HttpUtils.hpptGetRequest(url, ENCODER);
//            log.info("siwei-地址转坐标 resp=" + result);
//            AddrCoord coords = processAddrToCoordJsonResp(result);
//            return coords;
//        } catch (UnsupportedEncodingException e) {
//            log.error("编码失败：" + address + " message:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//    /**
//     * 四维地理编码返回参数解析
//     * @param result
//     * @return
//     */
//    private AddrCoord processAddrToCoordJsonResp(String result) {
//        if (result != null) {
//            JSONObject coordJson = JSONObject.fromObject(result);
//            if (coordJson.has("errcode")&&"0".equals(coordJson.getString("errcode")))
//            {
//                if (coordJson.has("data")) {
//                    AddrCoord coord = new AddrCoord();
//                    JSONObject resultJson = coordJson.getJSONObject("data");
//                    JSONArray dataJson = resultJson.getJSONArray("rows");
//                    JSONObject data = dataJson.getJSONObject(0);
//                    if (data.has("location")) {
//                        String locationJson= data.getString("location");
//                        if (StringUtils.isNotBlank(locationJson)) {
//                            coord.latitude = Double.parseDouble(locationJson.split(",")[1]);
//                            coord.longitude = Double.parseDouble(locationJson.split(",")[0]);
//                            return coord;
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//    /**
//     * 获取地理编码地址
//     * @param addr
//     * @param city
//     * @return
//     */
//    private String getAddr2CoordUrl(String addr, String city) {
//        String url = configManager.getSWUrl("searchaddr")+addr+"&city="+city;
//        log.info("四维-获取地理编码的请求url（地址-》坐标）=" + url);
//        return url;
//    }
//
//    /**
//     * @Description: 两点间路径导航string ->路径长度(米)
//     * @Author:
//     */
//    public NavigateDistance route(Double startLng, Double startLat, Double endLng,
//                                  Double endLat) {
//        if (startLng == null || startLat == null
//                || endLng == null || endLat == null) {
//            return null;
//        }
//        String url = getNavigateUrl(startLng, startLat, endLng, endLat);
//        String result = HttpUtils.hpptGetRequest(url, ENCODER);
//        log.info("四维-简易导航两点路径 resp=" + result);
//        NavigateDistance routes = processRouteJsonResp(result);
//        return routes;
//    }
//
//    /**
//     * @Description: 处理两点间路径导航string ->路径长度(米)
//     * @Author:
//     */
//    private NavigateDistance processRouteJsonResp(String result) {
//        if(result != null){
//            JSONObject resultJson = JSONObject.fromObject(result);
//            if (resultJson.has("errcode") && "0".equals(resultJson.getString("errcode"))) {
//                if (resultJson.has("data")) {
//                    JSONObject dataJson = resultJson.getJSONObject("data");
//                    if (dataJson.has("rows")) {
//                        NavigateDistance dis = new NavigateDistance();
//                        JSONArray rowsJson = dataJson.getJSONArray("rows");
////						int index = rowsJson.size();
////						for (int i = 0; i < index; i++) {
////							JSONObject rowJson = rowsJson.getJSONObject(i);
////							if(rowJson.has("distance")){
////								dis.distance = rowJson.getDouble("distance");
////							}
////							break;
////						}
//                        JSONObject rowJson = rowsJson.getJSONObject(0);
//                        if(rowJson.has("distance")){
//                            dis.distance = rowJson.getDouble("distance");
//                        }
//                        return dis;
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//}
