//package com.picc.rule;
//
//import com.picc.rule.entity.PrpClaimInfo;
//import com.picc.rule.service.IPrpclaimService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ExitCodeEvent;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * Created by lhx on 2019/10/9.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CRUDDemo {
//
//    @Autowired
//    private IPrpclaimService iPrpclaimService;
//
//    @Test
//    public void selectById() throws Exception {
//
//        PrpClaimInfo prpClaimInfo = iPrpclaimService.getById("17f1bc4e-28dc-4c0e-ae4a-2f790ace5b34");
//        System.out.println(prpClaimInfo);
//    }
//    @Test
//    public void removeById() throws Exception {
//        iPrpclaimService.removeById("id");
//    }
//    @Test
//    public void save() throws Exception {
//        PrpClaimInfo prpClaimInfo  = new PrpClaimInfo();
//        iPrpclaimService.save(prpClaimInfo);
//    }
//    @Test
//    public void update() throws Exception {
//        PrpClaimInfo prpClaimInfo  = new PrpClaimInfo();
//        iPrpclaimService.update();
//    }
//}
