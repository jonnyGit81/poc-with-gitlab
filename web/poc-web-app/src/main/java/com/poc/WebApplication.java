package com.poc;

import com.poc.support.dto.MsgInfo;
import com.poc.support.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebApplication {
	private static final Logger LOG = LoggerFactory.getLogger(WebApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
		LogUtil.INFO.apply(LOG,
				MsgInfo.of("Connect To : {}" ,
						context.getEnvironment().getProperty("spring.datasource.url")));
	}
}
