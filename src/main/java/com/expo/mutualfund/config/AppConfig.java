package com.expo.mutualfund.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public String filePath(@Value("${file.path}") String value) {
		return value;
	}
}
