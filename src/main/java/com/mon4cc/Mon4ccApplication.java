package com.mon4cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = {"com.mon4cc"})
@MapperScan("com.mon4cc.mapper")
public class Mon4ccApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mon4ccApplication.class, args);
	}

}
