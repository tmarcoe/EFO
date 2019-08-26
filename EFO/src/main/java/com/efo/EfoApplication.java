package com.efo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EfoApplication implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(EfoApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Using dataSource " + dataSource);
		
	}
	
}
