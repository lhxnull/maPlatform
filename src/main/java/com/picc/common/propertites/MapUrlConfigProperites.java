package com.picc.common.propertites;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author lhx
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:mapUrl.properties"})
@ConfigurationProperties(prefix = "map")
public class MapUrlConfigProperites {

    private  Map<String,String> bdConfig = new HashMap<>();
    private  Map<String,String> swConfig = new HashMap<>();
    private  ConcurrentLinkedQueue<String> queue=new ConcurrentLinkedQueue<String>();

}
