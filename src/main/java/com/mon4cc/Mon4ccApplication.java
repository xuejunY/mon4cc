package com.mon4cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = {"com.mon4cc.database.mapper"})
public class Mon4ccApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mon4ccApplication.class, args);
	}

}
