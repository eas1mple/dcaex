package com.dcaex.spbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dcaex.spbc.dao")
public class SpbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpbcApplication.class, args);
	}
	
}
