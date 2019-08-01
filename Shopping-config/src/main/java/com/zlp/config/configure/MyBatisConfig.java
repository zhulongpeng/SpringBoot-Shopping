package com.zlp.config.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zlp.mbg.mapper")
public class MyBatisConfig {
}
