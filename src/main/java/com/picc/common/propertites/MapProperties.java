package com.picc.common.propertites;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lhx
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs.properties"})
@ConfigurationProperties(prefix = "config")
public class MapProperties {

//    private boolean autoOpenBrowser = true;
//    private String[] autoOpenBrowserEnv = {};
    private SwaggerProperties swagger = new SwaggerProperties();

    private Map<String,String> data = new HashMap<>() ;

//    private  String sign;//是否开启代理
//    private  String proxy;//代理地址
//    private  int prot;//端口
//    private  String unify_door_interface;
//    private  String unify_door_back;
//    private  String unify_door_password;
//    private  String host;//地址
//    private  String ignoreAreas;



}
