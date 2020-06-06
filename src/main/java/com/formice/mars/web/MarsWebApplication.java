package com.formice.mars.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
@MapperScan("com.formice.mars.web.dao")
public class MarsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarsWebApplication.class, args);
	}

}
