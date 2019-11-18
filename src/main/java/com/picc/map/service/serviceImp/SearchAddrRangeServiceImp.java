package com.picc.map.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.picc.map.entity.AdminstrativeOrgan;
import com.picc.map.mapper.SearchAddrRangeMapper;
import com.picc.map.service.ISearchAddrRangeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lhx on 2019/11/14.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SearchAddrRangeServiceImp  extends ServiceImpl<SearchAddrRangeMapper,AdminstrativeOrgan> implements ISearchAddrRangeService  {


    @Override
    public List<AdminstrativeOrgan> findCityListByComcode(String comLevel,String comCode) {

        LambdaQueryWrapper<AdminstrativeOrgan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(AdminstrativeOrgan::getComCode,AdminstrativeOrgan::getComName,
                AdminstrativeOrgan::getLatitude,AdminstrativeOrgan::getLongitude).
                eq(StringUtils.isNotBlank(comCode),AdminstrativeOrgan::getComCode,comCode).
                eq(StringUtils.isNotBlank(comLevel),AdminstrativeOrgan::getComLevel,comLevel);
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<AdminstrativeOrgan> findTownListByComCode(String comLevel,String comCode,String comName ) {

        LambdaQueryWrapper<AdminstrativeOrgan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(AdminstrativeOrgan::getComCode,AdminstrativeOrgan::getComName,
                AdminstrativeOrgan::getLatitude,AdminstrativeOrgan::getLongitude,
                AdminstrativeOrgan::getUpperComCode).
                likeRight(StringUtils.isNotBlank(comCode),AdminstrativeOrgan::getComCode,comCode)
                .eq(StringUtils.isNotBlank(comName),AdminstrativeOrgan::getComName,comName)
                .eq(StringUtils.isNotBlank(comLevel),AdminstrativeOrgan::getComLevel,comLevel);
        return this.baseMapper.selectList(lambdaQueryWrapper);

    }
}
