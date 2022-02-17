package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EntityScan(basePackageClasses = AppUserRepository.class)
// @EnableJpaRepositories(basePackageClasses = AppUserRepository.class)
// @EnableJpaRepositories(basePackages = {"com.example.demo.*"})
// @ComponentScan(basePackages = { "com.example.demo.*" })
// @EntityScan(basePackages = {"com.example.demo.*"})
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
