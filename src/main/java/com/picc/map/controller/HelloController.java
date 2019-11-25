package com.picc.map.controller;

import com.picc.common.utils.FebsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lhx on 2019/11/20.
 */
@Controller
public class HelloController {

    @RequestMapping(value = {"/","/index","/login"})
    public  String helloWorld() {

        return FebsUtil.view("hello");
    }
}
