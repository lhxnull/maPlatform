package com.picc.map.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.picc.map.entity.AdminstrativeOrgan;

import java.util.List;

/**
 * Created by lhx on 2019/11/14.
 */
public interface ISearchAddrRangeService extends IService<AdminstrativeOrgan> {

    List<AdminstrativeOrgan> findCityListByComcode(String comLevel,String comCode);
    List<AdminstrativeOrgan> findTownListByComCode(String comLevel,String comCode,String comName);
}
