package com.picc.rule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by lhx on 2019/9/29.
 */
@Data
@TableName("PrpLisClaimInfo")
public class PrpClaimInfo {
    /**
     * 请求唯一码
     */
//    @Id
    @TableId(value = "uuid",type = IdType.UUID)
    private String uuid;

    /**
     * 查勘市级组织机构代码
     */

    private String cityComCode;

    /**
     * 注销
     * 1注销  0不注销
     */
    private String cancelflag;
    /**
     * 报案日期
     */
    private Date reportDate;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 报案地点属性
     */
    private String registAddType;

    /**
     * 出险区域代码
     */
    private String damageAddrCode;

    /**
     * 联系人名称
     */
    private String linkerName;
    /**
     * 联系人电话
     */
    private String phoneNumber;
    /**
     * 报案时间
     */
    private String reportHour;
    /**
     * 报案人手机
     */
    private String reportorMobile;
    /**
     * 报案人名称
     */
    private String reportorName;
    /**
     * 报案人电话
     */
    private String reportorNumber;
    /**
     * 报案号
     */
    private String registNo;
    /**
     * 事故号
     */
    private String accidentNo;
    /**
     * 目前事故车在查勘属地方位
     */
    private String registPosition;
    /**
     * 事故管界
     */
    private String section;
    /**
     * 玻璃单独破碎车
     */
    private String glassFlag;
    /**
     * 有无车损
     */
    private String lossFlag;
    /**
     * 是否报案现场
     */
    private String registFlag;
    /**
     * 查勘属地
     */
    private String checkAreaCode;
    /**
     * 出险次数
     */
    private String caseNumber;
    /**
     * 出险地机构
     */
    private String makeCom;
    /**
     * 查勘地点
     */
    private String checkAddress;
    /**
     * 出险地点
     */
    private String damageAddress;
    /**
     * 是否双方人保
     */
    private String isCasualty;
    /**
     * 是否已查勘
     */
    private String isSurvey;
    /**
     * 有无人员伤亡
     */
    private String personLossFlag;
    /**
     * 是否现场查勘
     */
    private String sceneSurvey;
    /**
     * 是否自助理赔
     */
    private String selfClaim;
    /**
     * 手机自助查勘
     */
    private String selfSurvey;
    /**
     * 是否单方事故
     */
    private String singleFlag;
    /**
     * 有无物损
     */
    private String thingLoseFlag;
    /**
     * 出险地点分类
     */
    private String damageAddressType;
    /**
     * 出险区域代码
     */
    private String damageAreaCode;
    /**
     * 出险区域说明
     */
    private String damageAreaName;
    /**
     * 出险原因代码
     */
    private String damageCode;
    /**
     * 出险日期
     */
    private String damageDate;
    /**
     * 出险时间
     */
    private String damageHour;
    /**
     * 出险原因说明
     */
    private String damageName;
    /**
     * 事故原因代码
     */
    private String damageTypeCode;
    /**
     * 操作员代码
     */
    private String operatorCode;
    /**
     * 出险经过
     */
    private String remark;
    /**
     * 赔案类别
     */
    private String claimType;
    /**
     * 请求时间
     */
    private String flowInTime;
    /**
     * 交强险保单号
     */
    private String qPolicyNo;


    /**
     * 报案类型
     */
    private String lFlag;
    /**
     * 处理信息
     */
    private String handleInfo;
    /**
     * 处理部门
     */
    private String solutionUnit;

    private String geoAddress;


    /**
     * 调度信息
     */
    private String dispatchInfo;

    /**
     * 查勘类型
     */
    private String checkType;

    /**
     * 驻点编码
     */
    private String positionCodes;

    /**
     * 驻点名称
     */
    private String positionNames;

    /**
     * 客户等级
     */
    private String customerLevel;

    /**
     * 是否VIP
     * 1是；2否
     */
    private String isVip;
    /*
    * 出险省级代码
    */
    private String damageProvinceCode;

    /**
     * 出险市级代码
     */
    private String damageCityCode;

    /**
     * 出险提示代码
     */
    private String damageAdCode;

    /**
     * 查勘省级代码
     */
    private String checkProvinceCode;

    /**
     * 查勘市级代码
     */
    private String checkCityCode;

    /**
     * 查勘提示代码
     */
    private String checkAdCode;

    /**
     * 是否点选
     * 1点选2点选。
     */
    private String isClick;
}

