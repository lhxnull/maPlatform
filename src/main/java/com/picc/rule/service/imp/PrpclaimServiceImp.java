package com.picc.rule.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.picc.rule.entity.PrpClaimInfo;
import com.picc.rule.mapper.PrpclaimMapper;
import com.picc.rule.service.IPrpclaimService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lhx on 2019/9/29.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PrpclaimServiceImp extends ServiceImpl<PrpclaimMapper, PrpClaimInfo> implements IPrpclaimService {

    @Override
    public PrpClaimInfo findbyId(String ID) {
        QueryWrapper<PrpClaimInfo> queryWrapper = new QueryWrapper<PrpClaimInfo>();
        queryWrapper.lambda().eq(PrpClaimInfo:: getUuid, ID);
        return this.getOne(queryWrapper);
    }

    @Override
    public PrpClaimInfo findbyMapperId(String Id) {
        return this.baseMapper.findbyMapperId(Id);
    }


}
