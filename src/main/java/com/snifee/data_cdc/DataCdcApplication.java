package com.snifee.data_cdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DataCdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCdcApplication.class, args);

//		new SpringApplicationBuilder(DataCdcApplication.class)
//				.web(WebApplicationType.NONE)
//				.build()
//				.run(args);
	}

}
