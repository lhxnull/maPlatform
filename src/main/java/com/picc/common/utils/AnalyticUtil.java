package com.picc.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.picc.common.entity.ResultResponse;
import com.picc.common.runner.OrganizationCache;
import com.picc.map.entity.InputSuggest;
import com.picc.map.entity.Poi;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.StringUtils;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by lhx on 2019/11/12.
 */
public class AnalyticUtil {


    //单例
    private static AnalyticUtil instance = new AnalyticUtil();
    public static AnalyticUtil getInstance(){
        return instance;
    }


        /**
     * 判断坐标是否有效
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static boolean isValid(String longitude, String latitude) {
        if (StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude)) {
            return false;
        }
        if (Double.parseDouble(longitude) > 73.55 && Double.parseDouble(longitude) < 135.08333333333333333
                && Double.parseDouble(latitude) > 3.85 && Double.parseDouble(latitude) < 53.55) {
            return true;
        }
        return false;
    }


    /**
     *  @Author: Qws
     *  @Description: 录入提示百度解析JSON
     *  @Date:14:16 2017/11/30
     * @param jsonResp
     **/
    public static List<InputSuggest> processSuggestionRespBD(String jsonResp) {
        if (StringUtils.isNotBlank(jsonResp)) {
            JSONObject suggestionJson = JSONObject.parseObject(jsonResp);
            if (suggestionJson.containsKey("status")
                    && "0".equals(suggestionJson.getString("status"))) {
                if (suggestionJson.containsKey("result")) {
                    JSONArray resultsJson = suggestionJson
                            .getJSONArray("result");
                    List<InputSuggest> list = new ArrayList<>(resultsJson.size());

                    resultsJson.forEach(item->{
                        InputSuggest inputSuggest = new InputSuggest();
                        JSONObject resultJson = (JSONObject) item;
                        if (resultJson.containsKey("location")) {
                            JSONObject locationJson = resultJson
                                    .getJSONObject("location");
                            if (locationJson.containsKey("lat")
                                    && locationJson.containsKey("lng")) {
//                                //百度录入提示坐标偏移成为高德坐标(火星)
//                                double[] doubles = GPSUtil.bd09_To_Gcj02(locationJson.getDouble("lat"), locationJson.getDouble("lng"));
//                                inputSuggest.setLatitude(doubles[0]);
//                                inputSuggest.setLongitude(doubles[1]);
                                inputSuggest.setLatitude(locationJson.getDouble("lat"));
                                inputSuggest.setLongitude(locationJson.getDouble("lng"));
                            }
                        }
                        if (resultJson.containsKey("name")) {
                            inputSuggest.setName(resultJson.getString("name"));
                        }
                        if (resultJson.containsKey("district")) {
                            inputSuggest.setDistrict(resultJson.getString("city") + resultJson.getString("district"));
                            String address = resultJson.getString("city") + " " + resultJson.getString("district");
                            String adcode = "";
                            if (StringUtils.isNotBlank(address)) {
                                adcode = OrganizationCache.getZoneCodeFromZoneAddr(address);
                            }
                            inputSuggest.setAdcode(adcode);
                        }
                        list.add(inputSuggest);
                    });
                    return list;
                }
            }
        }
        return null;
    }

    /**
     * 四维录入提示
     * @param jsonResp
     * @return
     */
    public List<InputSuggest> processSuggestionRespSW(String jsonResp) {

        if (StringUtils.isNotBlank(jsonResp)) {
            JSONObject suggestionJson = JSONObject.parseObject(jsonResp);
            if (suggestionJson.containsKey("errcode")) {
                if (suggestionJson.containsKey("data")) {
                    JSONObject dataJson = suggestionJson.getJSONObject("data");
                    JSONArray resultsJson = dataJson
                            .getJSONArray("rows");
                    List<InputSuggest> list =new ArrayList<>(resultsJson.size());
                    resultsJson.forEach(item->{
                        JSONObject resultJson = (JSONObject) item;
                        InputSuggest inputSuggest = new InputSuggest();
                        if (resultJson.containsKey("location")) {
                            String locationJson = resultJson
                                    .getString("location");
                            inputSuggest.setLatitude(Double.parseDouble(locationJson.split(",")[1]));
                            inputSuggest.setLongitude(Double.parseDouble(locationJson.split(",")[0]));

                        }
                        if (resultJson.containsKey("name")) {
                            inputSuggest.setName(resultJson.getString("name"));
                        }
                        if (resultJson.containsKey("district")&&resultJson.containsKey("city")) {
                            inputSuggest.setDistrict(resultJson.getString("city") + resultJson.getString("district"));
                            String address = resultJson.getString("city") + " " + resultJson.getString("district");
                            String adcode = "";
                            if (address != null && !"".equals(address)) {
                                adcode = OrganizationCache.getZoneCodeFromZoneAddr(address);
                            }
                            if (adcode == null) {
                                adcode = "";
                            }
                            inputSuggest.setAdcode(adcode);
                        }
                        list.add(inputSuggest);

                    });
                    return list;
                }
            }
        }
        return null;
    }
    /**
     * 地理编码百度解析JSON
     * @param coordJsonStr
     * @return
     */
    public InputSuggest processAddrToCoordJsonResp(String coordJsonStr) {
        if (StringUtils.isNotBlank(coordJsonStr)) {
            JSONObject suggestionJson = JSONObject.parseObject(coordJsonStr);
            if (suggestionJson.containsKey("status")
                    && "0".equals(suggestionJson.getString("status"))) {
                if (suggestionJson.containsKey("result")) {
                    JSONObject resultJson = suggestionJson.getJSONObject("result");
                    if (resultJson.containsKey("location")) {
                        JSONObject locationJson = resultJson
                                .getJSONObject("location");
                        if (locationJson.containsKey("lat") && locationJson.containsKey("lng")) {
                            InputSuggest inputSuggest = new InputSuggest();
                            inputSuggest.setLatitude(locationJson.getDouble("lat"));
                            inputSuggest.setLongitude(locationJson.getDouble("lng"));
                            return inputSuggest;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 地理编码四维解析JSON
     * @param coordJsonStr
     * @return
     */
    public InputSuggest processAddrToCoordJsonRespTOSW(String coordJsonStr) {
        if (StringUtils.isNotBlank(coordJsonStr)) {
            JSONObject coordJson = JSONObject.parseObject(coordJsonStr);
            if (coordJson.containsKey("errcode")&&
                    "0".equals(coordJson.getString("errcode"))){
                if (coordJson.containsKey("data")) {
                    InputSuggest coord = new InputSuggest();
                    JSONObject resultJson = coordJson.getJSONObject("data");
                    JSONArray dataJson = resultJson.getJSONArray("rows");
                    JSONObject data = dataJson.getJSONObject(0);
                    if (data.containsKey("location")) {
                        String locationJson= data.getString("location");
                        if (StringUtils.isNotBlank(locationJson)) {
                            coord.setLatitude(Double.parseDouble(locationJson.split(",")[1]));
                            coord.setLongitude(Double.parseDouble(locationJson.split(",")[0]));
                            return coord;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param @param  resp
     * @param @return
     * @return AddressCoord
     * @throws
     * @Title: processCoord2AddrJsonResp
     * @Description: 百度处理逆地理编码json string-》地址列表
     * @Author:
     */
    public  InputSuggest processCoord2AddrJsonResp(String resp) {

        JSONObject addrJson = JSONObject.parseObject(resp);
        if (!addrJson.containsKey("status")|| !"0".equals(addrJson.getString("status"))) {
            return null;
        }
        if (addrJson.containsKey("result")) {
            InputSuggest addr = new InputSuggest();
            JSONObject resultJson = addrJson.getJSONObject("result");

            if (resultJson.containsKey("formatted_address")) {
                addr.setFormatted_address(resultJson.getString("formatted_address")) ;
            }
            if (resultJson.containsKey("addressComponent")) {
                JSONObject addressComponentJson = resultJson.getJSONObject("addressComponent");
                if (addressComponentJson.containsKey("province")) {
                    addr.setProvince(addressComponentJson.getString("province"));
                }
                if (addressComponentJson.containsKey("city")) {
                    addr.setCity(addressComponentJson.getString("city"));
                }
                if (addressComponentJson.containsKey("district")) {
                    addr.setDistrict(addressComponentJson.getString("district"));
                }
                if (addressComponentJson.containsKey("distance")) {
                    addr.setDistance(StringUtils.isNotEmpty(addressComponentJson.getString("distance"))?addressComponentJson.getString("distance"):"0.0");
                }
            }
            if (resultJson.containsKey("location")) {
                JSONObject locationJson = resultJson.getJSONObject("location");
                if (locationJson.containsKey("lng")) {
                    addr.setLongitude(locationJson.getDouble("lng"));
                }
                if (locationJson.containsKey("lat")) {
                    addr.setLatitude(locationJson.getDouble("lat"));
                }
            }
            if(resultJson.containsKey("pois")){
                JSONArray poisJson = resultJson.getJSONArray("pois");
                int index = poisJson.size();
                Poi[] pois = new Poi[index];
                for (int i = 0; i < index; i++) {
                    Poi poi = new Poi();
                    JSONObject resultJson1 = poisJson.getJSONObject(i);
                    if (resultJson1.containsKey("addr")) {
                        poi.setAddr(resultJson1.getString("addr"));
                    }
                    if (resultJson1.containsKey("direction")) {
                        poi.setDirection(resultJson1.getString("direction"));
                    }
                    if (resultJson1.containsKey("distance")) {
                        poi.setDistance(resultJson1.getString("distance"));
                    }
                    if (resultJson1.containsKey("name")) {
                        poi.setName(resultJson1.getString("name"));
                    }
                    if (resultJson1.containsKey("poiType")) {
                        poi.setPoiType(resultJson1.getString("poiType"));
                    }
                    if (resultJson1.containsKey("point")) {
                        JSONObject pointJson = resultJson1.getJSONObject("point");
                        poi.setX(pointJson.getDouble("x"));
                        poi.setY(pointJson.getDouble("y"));
                    }
                    if (resultJson1.containsKey("point")) {
                        poi.setTag(resultJson1.getString("tag"));
                    }
                    pois[i] = poi;
                }
                addr.setPois(pois);
            }

            return addr;
        }
        return null;
    }
    /**
     * @param result
     * @return AddressCoord
     * @throws
     * @Title: processCoord2AddrJsonResp
     * @Description: 四维处理逆地理编码json string-》地址列表
     * @Author:
     */
    public InputSuggest processCoord2AddrJsonRespToSW(String result) {
        if(StringUtils.isNotBlank(result)){
            InputSuggest addr = new InputSuggest();
            //转换成json对象
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson.containsKey("errcode")&&
                    "0".equals(resultJson.getString("errcode"))) {
                JSONObject datatJson = resultJson.getJSONObject("data");
                //formatted_address
                String[] fStrings = {"province","city","dist","town","village","roadname","restName"};
                StringBuilder formatted_address = new StringBuilder();
                for (int i = 0; i < fStrings.length; i++) {
                    if(datatJson.containsKey(fStrings[i])){
                        if (!"直辖市".equals(datatJson.getString(fStrings[i]))) {
                            formatted_address.append(datatJson.getString(fStrings[i]));
                        }
                    }
                }
                addr.setFormatted_address(formatted_address.toString());
                // city
                if(datatJson.containsKey("city")){
                    addr.setCity(datatJson.getString("city"));
                }
                // province
                if(datatJson.containsKey("province")){
                    addr.setProvince(datatJson.getString("province"));
                }
                //district
                if(datatJson.containsKey("dist")){
                    addr.setDistrict(datatJson.getString("dist"));
                }
                //distance
                if(resultJson.containsKey("distance")){
                    addr.setDistance(datatJson.getString("distance"));
                }
            }

            return addr;
        }
        return null;
    }
    /**
     * @param @param  routeResp
     * @param @return
     * @return int[]
     * @throws
     * @Title: processRouteJsonResp
     * @Description: 处理两点间路径导航(简易导航不含路网)的json string ->路径长度(米)
     * @Author:
     */
    public InputSuggest processRouteJsonResp(String routeResp) {
        if (StringUtils.isNotBlank(routeResp)) {
            JSONObject routeJson = JSONObject.parseObject(routeResp);
            if (routeJson.containsKey("status") && "0".equals(routeJson.getString("status"))) {
                if (routeJson.containsKey("result")) {
                    JSONObject resultJson = routeJson.getJSONObject("result");
                    if (resultJson.containsKey("elements")) {
                        InputSuggest dis = new InputSuggest();
                        JSONArray elementsJson = resultJson.getJSONArray("elements");
                        int index = elementsJson.size();
                        elementsJson.forEach(item->{
                            JSONObject elementJson = (JSONObject) item;
                                if (elementJson.containsKey("distance")) {
                                JSONObject distanceJson = elementJson.getJSONObject("distance");
                                if (distanceJson.containsKey("value")) {
                                    dis.setDistance(distanceJson.getString("value"));
                                }
                            }

                        });
                        return dis;
                    }

                }
            }
        }
        return null;
    }

    /**
     * @Description: 四维处理两点间路径导航string ->路径长度(米)
     * @Author:
     */
    public InputSuggest processRouteJsonRespToSW(String result) {
        if(result != null){
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson.containsKey("errcode") && "0".equals(resultJson.getString("errcode"))) {
                if (resultJson.containsKey("data")) {
                    JSONObject dataJson = resultJson.getJSONObject("data");
                    if (dataJson.containsKey("rows")) {
                        InputSuggest dis = new InputSuggest();
                        JSONArray rowsJson = dataJson.getJSONArray("rows");
//						int index = rowsJson.size();
//						for (int i = 0; i < index; i++) {
//							JSONObject rowJson = rowsJson.getJSONObject(i);
//							if(rowJson.has("distance")){
//								dis.distance = rowJson.getDouble("distance");
//							}
//							break;
//						}
                        JSONObject rowJson = rowsJson.getJSONObject(0);
                        if(rowJson.containsKey("distance")){
                            dis.setDistance(rowJson.getString("distance"));
                        }
                        return dis;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 处理百度地图导航路径
     * @param navResp
     * @return
     */
    public InputSuggest[] processNavigateJsonResp(String navResp) {
        if (navResp != null && !"".equals(navResp)) {
            JSONObject navJson = JSONObject.parseObject(navResp);
            if (navJson.containsKey("status")
                    && "0".equals(navJson.getString("status"))) {
                if (navJson.containsKey("result")) {
                    JSONObject resultJson = navJson.getJSONObject("result");
                    if (resultJson.containsKey("routes")) {
                        JSONArray routesJson = resultJson
                                .getJSONArray("routes");
                        InputSuggest[] navigates= new InputSuggest[0];
                        for (int i = 0,index = routesJson.size(); i < index; i++) {
                            JSONObject routeJson = routesJson.getJSONObject(i);
                            if (routeJson.containsKey("steps")) {
                                JSONArray stepsJson = routeJson.getJSONArray("steps");
                                int sindex = stepsJson.size();
                                navigates = new InputSuggest[sindex];
                                for (int j = 0; j < sindex; j++) {
                                    JSONObject stepJson = stepsJson.getJSONObject(j);
                                    InputSuggest navigate = new InputSuggest();
                                    if (stepJson.containsKey("distance")) {
                                        navigate.setDistance(stepJson.getString("distance"));
                                    }
                                    if (stepJson.containsKey("duration")) {
                                        navigate.setDuration(stepJson.getDouble("duration"));
                                    }
                                    if (stepJson.containsKey("instructions")) {
                                        String instructions = HtmlUtil.getTextFromHtml(stepJson.getString("instructions"));
                                        navigate.setInstruction(instructions);
                                    }
                                    if (stepJson.containsKey("path")) {
                                        navigate.setPath(stepJson.getString("path"));
                                    }
                                    if (stepJson.containsKey("turn")) {
                                        navigate.setTurn(stepJson.getString("turn"));
                                    }
                                    navigates[j] = navigate;
                                }
                            }
                        }
                        return navigates;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param result
     * @return Navigate[]
     * @Title: processNavigateJsonResp
     * @Description: 四维导航返回解析
     * @Author:
     */
    public InputSuggest[] processNavigateJsonRespToSW(String result) {
        InputSuggest[] navigates = new InputSuggest[0];
        if(result != null){
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson.containsKey("errcode") &&
                    "0".equals(resultJson.getString("errcode"))) {
                if (resultJson.containsKey("data")) {
                    JSONObject dataJson = resultJson.getJSONObject("data");
                    if (dataJson.containsKey("rows")) {
                        JSONArray rowsJson = dataJson.getJSONArray("rows");
                        int index = rowsJson.size();
                        for (int i = 0; i < index; i++) {
                            JSONObject rowJson = rowsJson.getJSONObject(i);
                            if (rowJson.containsKey("item")) {
                                JSONArray itemsJson = rowJson.getJSONArray("item");
                                int iIndex = itemsJson.size();
                                navigates = new InputSuggest[iIndex];
                                for (int j = 0; j < iIndex; j++) {
                                    JSONObject itemJson = itemsJson.getJSONObject(j);
                                    InputSuggest navigate = new InputSuggest();
                                    if (itemJson.containsKey("strguide")) {
                                        navigate.setInstruction(itemJson.getString("strguide"));
                                    }
                                    if (itemJson.containsKey("distance")) {
                                        navigate.setDistance(itemJson.getString("distance"));
                                    }
                                    if (itemJson.containsKey("routelatlon")) {
                                        navigate.setPath(itemJson.getString("routelatlon"));
                                    }
                                    if (itemJson.containsKey("turncode")) {
                                        navigate.setTurn(itemJson.getString("turncode"));
                                    }
                                    navigates[j] = navigate;
                                }
                            }
                        }
                        return navigates;
                    }
                }
            }
        }
        return null;
    }



}
