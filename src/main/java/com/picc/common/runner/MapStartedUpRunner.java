package com.picc.common.runner;


import com.picc.common.function.IRedisService;
import com.picc.common.propertites.MapUrlConfigProperites;
import com.picc.common.propertites.MapProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author lhx
 * @author FiseTch
 */

@Component
public class MapStartedUpRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(MapStartedUpRunner.class.getName());// slf4j日志记录器

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private MapProperties febsProperties;
    @Autowired
    private MapUrlConfigProperites mapUrlConfigProperites;
    @Autowired
    private IRedisService redisService;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;
    @Value("${proxy}")
    private String proxy;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {

            // 测试 Redis连接是否正常
            redisService.exists("febs_test");
        } catch (Exception e) {

            log.error(" ____   __    _   _ ");
            log.error(" ____   __    _   _ ");
            log.error(" ____   __    _   _ ");
            log.error("maPlatform启动失败，{}", e.getMessage());
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            // 关闭 FEBS
            context.close();
        }
        //百度url
        OrganizationCache.BDConfig = mapUrlConfigProperites.getBdConfig();
        //百度key
        OrganizationCache.BDqueue = mapUrlConfigProperites.getQueue();
        //获取项目基本配置
        OrganizationCache.baseConfigMap = febsProperties.getData();
        OrganizationCache.baseConfigMap.put("proxy",proxy);
        //获取市区编码
        OrganizationCache.zoneAddr2codeGDMap = OrganizationCache.getZoneAddr2CodeMap(OrganizationCache.fileName);
        //四维url
        OrganizationCache.SWConfig = mapUrlConfigProperites.getSwConfig();

    }
}
