package com.expo.mutualfund.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.expo.mutualfund.dao.MutualFundRepository;

@SpringBootApplication(scanBasePackages= {"com.expo.mutualfund"})
@EnableMongoRepositories(basePackageClasses=MutualFundRepository.class)
public class MutualfundApplication {

	@Autowired
	MutualFundRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(MutualfundApplication.class, args);
	}

}
