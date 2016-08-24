package com.joeldholmes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.netflix.hystrix.HystrixCommandProperties;

@SpringBootApplication
@EnableMongoRepositories("com.joeldholmes.repository")
public class SacredTextApiApplication {

	public static void main(String[] args) {
		
		 ApplicationContext ctx =SpringApplication.run(SacredTextApiApplication.class, args);

		 HystrixCommandProperties.Setter()
		   .withExecutionTimeoutInMilliseconds(5000);
	}
}
