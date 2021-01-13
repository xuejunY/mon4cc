package com.mon4cc;

import com.mon4cc.deploy.IDeploy;
import com.mon4cc.deploy.impl.DeployImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.events.Event;


@SpringBootApplication

@MapperScan("com.mon4cc.mapper")
public class Mon4ccApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mon4ccApplication.class, args);
	}




}
