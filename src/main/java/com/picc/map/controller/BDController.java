package com.picc.map.controller;

/**
 * Created by lhx on 2019/11/8.
 */

import com.picc.common.annotation.ControllerEndpoint;
import com.picc.common.entity.ResultResponse;
import com.picc.common.utils.*;
import com.picc.map.entity.AddrRange;
import com.picc.map.entity.AdminstrativeOrgan;
import com.picc.map.entity.InputSuggest;
import com.picc.map.service.ISearchAddrRangeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/baidu/")
public  class BDController {

    @Autowired
    ISearchAddrRangeService iSearchAddrRangeService;

    /**
     * 百度录入提示接口
     * @param city
     * @param keyWord
     * @return
     */
    @GetMapping("inputSuggest/{city}/{keyWord}")
    @ControllerEndpoint(operation ="百度录入提示接口",exceptionMessage ="调用百度录入提示异常")
    public ResultResponse getInputSuggestion (@PathVariable String city,
                                             @PathVariable String keyWord) throws Exception {

        log.info("test");
        //验证参数有效性
        if (StringUtils.isBlank(keyWord)) {
            return new ResultResponse().error().message("参数无效");
        }

        List<InputSuggest> inputSuggests = InputSuggestionUtils.inputSuggestion(city,
                keyWord, MapUtil.MAP_BAIDU);
        return new ResultResponse().success().data(inputSuggests);

    }

    /**
     * 百度地理编码接口
     * @param city
     * @param keyWord
     * @return
     * @throws Exception
     */
    @GetMapping("addr2Coord/{city}/{keyWord}")
    @ControllerEndpoint(operation ="百度地理编码接口",exceptionMessage ="调用百度地理编码接口异常")
    public ResultResponse Addr2CoordResp (@PathVariable String city,
                                         @PathVariable String keyWord) throws Exception {

        //验证参数有效性
        if (StringUtils.isBlank(keyWord)) {
            return new ResultResponse().error().message("参数无效");
        }
        keyWord=keyWord.replace(" ", "");

        InputSuggest inputSuggest = InputSuggestionUtils.getAddr2Coord(city,
                keyWord, MapUtil.MAP_BAIDU);

        return new ResultResponse().success().data(inputSuggest);
    }

    /**
     * 百度逆地理编码接口
     * @param longitude
     * @param latitude
     * @return
     * @throws Exception
     */
    @GetMapping("coord2Addr/{longitude}/{latitude}")
    @ControllerEndpoint(operation ="百度逆地理编码接口",exceptionMessage ="调用百度逆地理编码接口异常")
    public ResultResponse Coord2AddrResp (@PathVariable String longitude,
                                         @PathVariable String latitude) throws Exception {

        //验证参数有效性
        if (!AnalyticUtil.isValid(longitude,latitude)) {
            return new ResultResponse().error().message("参数无效");
        }

        InputSuggest inputSuggest = InputSuggestionUtils.coord2AddrResp(longitude,
                latitude, MapUtil.MAP_BAIDU);

        return new ResultResponse().success().data(inputSuggest);
    }


    /**
     * 坐标偏移接口，可批量偏移
     * @param longLat 坐标以；分割请求时需要对;UrlEncode转码%3b
     * @param fromType
     * @param toType
     *
     * @return
     */
    @GetMapping("coordOff/{longLat}/{fromType}/{toType}")
    @ControllerEndpoint(operation ="坐标偏移接口",exceptionMessage ="调用坐标偏移接口异常")
    public ResultResponse getCoordOffset (@PathVariable String longLat,@PathVariable String fromType,@PathVariable
                                          String toType) {

        if (StringUtils.isBlank(longLat) || StringUtils.isBlank(fromType) || StringUtils.isBlank(toType)) {
            return new ResultResponse().error().message("参数无效");
        }

        String[] split = longLat.split(";");
        List<String> coordList = Arrays.asList(split);

        List<InputSuggest> category = GPSUtil.category(fromType, toType, coordList);
        return new ResultResponse().success().data(category);
    }

    /**
     * 百度获取导航总长度
     * @param startLatLong
     * @param endLatLong
     * @param coordType
     * @return
     */
    @GetMapping("getDistince/{startLatLong}/{endLatLong}/{coordType}")
    @ControllerEndpoint(operation ="获取百度导航总长度接口",exceptionMessage ="获取百度导航总长度接口异常")
    public ResultResponse getDistince (@PathVariable String startLatLong,@PathVariable String endLatLong,@PathVariable String coordType) {

        InputSuggest distince = InputSuggestionUtils.getDistince(startLatLong, endLatLong, coordType);
        return new ResultResponse().success().data(distince);
    }

    /**
     * 获取导航路径
     * @param startLatLong
     * @param endLatLong
     * @param startCity
     * @param endCity
     * @param coordType
     * @return
     */
    @GetMapping("getNavigate/{startCity}/{endCity}/{startLatLong}/{endLatLong}/{coordType}")
    @ControllerEndpoint(operation ="获取导航路径接口",exceptionMessage ="获取导航路径接口异常")
    public ResultResponse getNavigate (@PathVariable String startCity,@PathVariable String endCity,@PathVariable String startLatLong,@PathVariable String endLatLong,
                                      @PathVariable String coordType) {

        InputSuggest[] navigate = InputSuggestionUtils.getNavigate(startLatLong, endLatLong, startCity, endCity, coordType);
        return new ResultResponse().success().data(navigate);

    }

    @GetMapping("getAddrRange/{cityCode}/{address}")
    @ControllerEndpoint(operation ="地图平台范围检索接口",exceptionMessage ="地图平台范围检索接口异常")
    public ResultResponse getAddrRangeMsg (@PathVariable String cityCode,@PathVariable String address) {

        if (StringUtils.isBlank(address) || StringUtils.isBlank(cityCode) ) {
            return new ResultResponse().error().message("参数无效");
        }
        address=address.replace(" ", "");
        cityCode=cityCode.length() > 4?cityCode.substring(0, 4):cityCode;
        //区县 3级
        String adName = RegexUtil.regex3(address);
        if(StringUtils.isBlank(adName)){
            //获取地市信息
            adName=RegexUtil.regexdshi(address);
        }
        //镇 4级
        String townName = RegexUtil.regex(address);
        if(StringUtils.isBlank(townName)){
            townName=RegexUtil.regexTown(address);
        }
        //获取市信息
        List<AdminstrativeOrgan> cityList = iSearchAddrRangeService.findCityListByComcode("2",cityCode);
        AddrRange addrRange = new AddrRange();
        cityList.forEach(item->{
            addrRange.setCity(item.getComName());
            addrRange.setCityCode(item.getComCode());
            addrRange.setCityLocation(item.getLongitude()+","+item.getLatitude());

        });

        //根据名称及市code模糊获取镇信息
        List<AdminstrativeOrgan> townListByComCode = iSearchAddrRangeService.findTownListByComCode("4", addrRange.getCityCode(), townName);
        List<AdminstrativeOrgan> townListByComCode1;
        if(townListByComCode !=null && townListByComCode.size() == 1) {
            townListByComCode.forEach(item->{
                addrRange.setTown(item.getComName());
                addrRange.setTownCode(item.getComCode());
                addrRange.setTownLocation(item.getLongitude()+","+item.getLatitude());
                addrRange.setUpComCode(item.getUpperComCode());
            });
            List<AdminstrativeOrgan> cityListByComcode = iSearchAddrRangeService.findCityListByComcode("3", addrRange.getUpComCode());
            cityListByComcode.forEach(item->{
                addrRange.setDistrict(item.getComName());
                addrRange.setAdcode(item.getComCode());
                addrRange.setDistrictLocation(item.getLongitude()+","+item.getLatitude());
            });
            if(StringUtils.isNotBlank(adName)){
                String name=adName.substring(0,adName.length()-1);
                String dis=addrRange.getDistrict();
                String name1=dis.substring(0, dis.length()-1);
                if(!name.equals(name1)){
                    //如果查出的区县级信息与地址中的区县级信息不一致  不返回乡镇信息
                    addrRange.setTown("");
                    addrRange.setTownCode("");
                    addrRange.setTownLocation("");
                    townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), adName);
                    if(townListByComCode1 == null || townListByComCode1.isEmpty()){
                        String xName = name + "县";
                        townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), xName);
                        if(townListByComCode1 == null || townListByComCode1.isEmpty()){
                            String qName = name + "区";
                            townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), qName);
                            if(townListByComCode1 == null || townListByComCode1.isEmpty()){
                                String sName = name + "市";
                                townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), sName);
                            }
                        }
                    }
                }

            }
            return new ResultResponse().success().data(addrRange);
        }else {
            if (StringUtils.isNotBlank(adName)) {
                //通过解析出的adName和所属于层级的关系
                //县区市轮循判断
                String name = adName.substring(0, adName.length() - 1);
                townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), adName);
                if (townListByComCode1 == null || townListByComCode1.isEmpty()) {
                    String xName = name + "县";
                    townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), xName);
                    if (townListByComCode1 == null || townListByComCode1.isEmpty()) {
                        String qName = name + "区";
                        townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), qName);
                        if (townListByComCode1 == null || townListByComCode1.isEmpty()) {
                            String sName = name + "市";
                            townListByComCode1 = iSearchAddrRangeService.findTownListByComCode("3", addrRange.getCityCode(), sName);
                        }
                    }
                }
                if (townListByComCode1 != null && !townListByComCode1.isEmpty()) {
                    townListByComCode1.forEach(item->{
                        addrRange.setDistrict(item.getComName());
                        addrRange.setAdcode(item.getComCode());
                        addrRange.setDistrictLocation(item.getLongitude()+","+item.getLatitude());
                    });
                }
                townListByComCode = iSearchAddrRangeService.findTownListByComCode("4", addrRange.getAdcode(), townName);
                townListByComCode.forEach(item->{
                    addrRange.setTown(item.getComName());
                    addrRange.setTownCode(item.getComCode());
                    addrRange.setTownLocation(item.getLongitude()+","+item.getLatitude());
                });
            }
            return new ResultResponse().success().data(addrRange);
        }
    }
}

