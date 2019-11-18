package com.picc.map.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lhx on 2019/11/12.
 */
@Data
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class InputSuggest implements Serializable{

    /**
     * POI名称
     * */
    private String name;
    /**
     * 城市区县
     * */
    private String district;
    /**
     * 区县编码
     * */
    private String adcode;
    /**
     * POI经度坐标
     * */
    private Double longitude;
    /**
     * POI纬度坐标
     * */
    private Double latitude;

    private String province;
    private String city;// 所在城市
    private String formatted_address;// 详细地址
    private String distance;//
    private Poi[] pois;

    private Double duration;
    private String instruction;
    private String path;
    private String turn;

}
