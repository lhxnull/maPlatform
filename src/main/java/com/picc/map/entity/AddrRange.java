package com.picc.map.entity;

import lombok.Data;

/**
 * Created by lhx on 2019/11/14.
 */
@Data
public class AddrRange {

    /**
     * 城市
     */
    private String city;
    /**
     * 城市code
     */
    private String cityCode;
    /**
     * 城市中心经纬度
     */
    private String cityLocation;
    /**
     * 区县
     */
    private String district;
    /**
     * 区县code
     */
    private String adcode;
    /**
     * 区县经纬度
     */
    private String districtLocation;
    /**
     * 乡镇
     */
    private String town;
    /**
     * 乡镇code
     */
    private String townCode;
    /**
     * 乡镇经纬度
     */
    private String townLocation;
    /**
     * 上级机构
     */
    private String upComCode;
}
