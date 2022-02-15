package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.example.demo.repository.AppUserRepository;

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
