package com.picc.rule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.picc.rule.entity.PrpClaimInfo;

/**
 * Created by lhx on 2019/9/29.
 */

public interface PrpclaimMapper extends BaseMapper<PrpClaimInfo> {

    PrpClaimInfo findbyMapperId(String Id);
}
