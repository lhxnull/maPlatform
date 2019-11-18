package com.picc.map.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lhx on 2019/11/11.
 */
@Slf4j
@RestController
@RequestMapping("/api/siwei/")
public class SWController {
    @Autowired
    ISearchAddrRangeService iSearchAddrRangeService;

    /**
     * 四维录入提示接口
     * @param city
     * @param keyWord
     * @return
     */
    @GetMapping("inputSuggest/{city}/{keyWord}")
    @ControllerEndpoint(operation ="四维录入提示接口",exceptionMessage ="调用四维度录入提示异常")
    public ResultResponse getInputSuggestion (@PathVariable String city,
                                              @PathVariable String keyWord) throws Exception {

        //验证参数有效性
        if (StringUtils.isBlank(keyWord)) {
            return new ResultResponse().error().message("参数无效");
        }
        keyWord=keyWord.replace(" ", "");
        List<InputSuggest> inputSuggests = InputSuggestionUtils.inputSuggestion(city,
                keyWord, MapUtil.MAP_SIWEI);
        return new ResultResponse().success().data(inputSuggests);

    }

    /**
     * 四维地理编码接口
     * @param city
     * @param keyWord
     * @return
     * @throws Exception
     */
    @GetMapping("addr2Coord/{city}/{keyWord}")
    @ControllerEndpoint(operation ="四维地理编码接口",exceptionMessage ="调用四维地理编码接口异常")
    public ResultResponse Addr2CoordResp (@PathVariable String city,
                                          @PathVariable String keyWord) throws Exception {

        //验证参数有效性
        if (StringUtils.isBlank(keyWord)) {
            return new ResultResponse().error().message("参数无效");
        }
        keyWord=keyWord.replace(" ", "");

        InputSuggest inputSuggest = InputSuggestionUtils.getAddr2Coord(city,
                keyWord, MapUtil.MAP_SIWEI);

        return new ResultResponse().success().data(inputSuggest);
    }

    /**
     * 四维逆地理编码接口
     * @param longitude
     * @param latitude
     * @return
     * @throws Exception
     */
    @GetMapping("coord2Addr/{longitude}/{latitude}")
    @ControllerEndpoint(operation ="四维逆地理编码接口",exceptionMessage ="四维百度逆地理编码接口异常")
    public ResultResponse Coord2AddrResp (@PathVariable String longitude,
                                          @PathVariable String latitude) throws Exception {

        //验证参数有效性
        if (!AnalyticUtil.isValid(longitude,latitude)) {
            return new ResultResponse().error().message("参数无效");
        }

        InputSuggest inputSuggest = InputSuggestionUtils.coord2AddrResp(longitude,
                latitude, MapUtil.MAP_SIWEI);

        return new ResultResponse().success().data(inputSuggest);
    }



    /**
     * 四维获取导航总长度
     * @param startLatLong
     * @param endLatLong
     * @return
     */
    @GetMapping("getDistince/{startLatLong}/{endLatLong}/{coordType}")
    @ControllerEndpoint(operation ="获取四维导航总长度接口",exceptionMessage ="获取四维导航总长度接口异常")
    public ResultResponse getDistince (@PathVariable String startLatLong,@PathVariable String endLatLong,@PathVariable String coordType) {

        InputSuggest distince = InputSuggestionUtils.getDistince(startLatLong, endLatLong, MapUtil.MAP_SIWEI);
        return new ResultResponse().success().data(distince);
    }

    /**
     * 四维获取导航路径
     * @param startLatLong
     * @param endLatLong
     * @param startCity
     * @param endCity
     * @param coordType
     * @return
     */
    @GetMapping("getNavigate/{startCity}/{endCity}/{startLatLong}/{endLatLong}/{coordType}")
    @ControllerEndpoint(operation ="获取导航路径接口",exceptionMessage ="获取导航路径接口异常")
    public ResultResponse getNavigate (@PathVariable String startCity,@PathVariable String endCity,@PathVariable String startLatLong,
                                       @PathVariable String endLatLong,
                                       @PathVariable String coordType) {

        InputSuggest[] navigate = InputSuggestionUtils.getNavigate(startLatLong, endLatLong, startCity, endCity, coordType);
        return new ResultResponse().success().data(navigate);

    }


}
