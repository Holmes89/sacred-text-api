package com.ferrumlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SacredTextApiApplication {

	public static void main(String[] args) {
		
		 ApplicationContext ctx =SpringApplication.run(SacredTextApiApplication.class, args);

	}
}
