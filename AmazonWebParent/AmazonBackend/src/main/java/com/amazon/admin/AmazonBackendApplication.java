package com.amazon.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.amazon.common.entity","com.amazon.admin.user"})
public class AmazonBackendApplication {
	
	static{
		System.out.println("Main class load");	
		}
		{
			System.out.println("Main class ob created");
		}
	
	public static void main(String[] args) {
		SpringApplication.run(AmazonBackendApplication.class, args);
	}


}
