package com.srhdp.PhotoAppApiUsers;

import com.srhdp.PhotoAppApiUsers.shared.FeignErrorDecoder;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PhotoAppApiUsersApplication {

	@Autowired
	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

//	@Bean
//	public HttpExchangeRepository httpTraceRepository() {
//		return new InMemoryHttpExchangeRepository();
//	}

	@Bean
	@Profile("!production")
	Logger.Level feignLoggerLevel()
	{
		return Logger.Level.FULL;
	}
	@Bean
	@Profile("production")
	Logger.Level feignProductionLoggerLevel(){
		return Logger.Level.NONE;
    }

	@Bean
	public FeignErrorDecoder getFeignErrorDecoder()
	{
		return new FeignErrorDecoder();
	}


	@Bean
	@Profile("production")
	public String createProductionBean(){
		System.out.print("Production Bean =" + environment.getProperty("myapplication.environment") );
		return "Production bean";
	}


	@Bean
	@Profile("!production")
	public String createNotProductionBean(){
		System.out.print("Not Production Bean = " + environment.getProperty("myapplication.environment"));
		return "Not Production bean";
	}


	@Bean
	@Profile("default")
	public String createDevelopmentBean(){
		System.out.print("Development Bean = " + environment.getProperty("myapplication.environment"));
		return "Development bean";
	}

}
