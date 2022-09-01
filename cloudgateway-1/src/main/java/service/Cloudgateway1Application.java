package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Cloudgateway1Application {

	public static void main(String[] args) {
		
		
		SpringApplication.run(Cloudgateway1Application.class, args);
	}

}
