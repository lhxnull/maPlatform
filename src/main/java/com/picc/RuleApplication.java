package com.picc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by lhx on 2019/9/29.
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement//事务支持
@MapperScan("com.picc.*.mapper")
public class RuleApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RuleApplication.class).run(args);
    }





}
