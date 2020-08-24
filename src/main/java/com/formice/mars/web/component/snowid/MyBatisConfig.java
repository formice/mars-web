package com.formice.mars.web.component.snowid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MyBatisConfig {
    //@Autowired
    //private List<SqlSessionFactory> sqlSessionFactoryList;
 
 
    @Bean
    @Primary
    public SnowflakeIdWorker snowflakeIdWorker(){
        return new SnowflakeIdWorker(1,1);
    }
}