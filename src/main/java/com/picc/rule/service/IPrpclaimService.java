package com.picc.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.picc.rule.entity.PrpClaimInfo;

/**
 * Created by lhx on 2019/9/29.
 */
public interface IPrpclaimService extends IService<PrpClaimInfo> {

    PrpClaimInfo findbyId(String ID);

    PrpClaimInfo findbyMapperId(String Id);
}
