package io.getarrays.userservice;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.getarrays.userservice.models.Role;
import io.getarrays.userservice.models.User;
import io.getarrays.userservice.services.UserService;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
        /* todo lo que ponga aqui se ejecutará tras arrancar la app.Empiezo creando algunos roles */
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_SUOER_ADMIN"));
			
			/* ahora creo algunos users fake */
			userService.saveUser(new User(null,"John Travolta","jonny","123456",new ArrayList<Role>()));
			userService.saveUser(new User(null,"Will Smith","will","123456",new ArrayList<Role>()));
			userService.saveUser(new User(null,"Sylvester Stallone","sylver","123456",new ArrayList<Role>()));

			/* ahora le asigno roles a los users */
			userService.addRoleToUser("jonny","ROLE_USER");
			userService.addRoleToUser("will","ROLE_USER");
			userService.addRoleToUser("sylver","ROLE_USER");
			userService.addRoleToUser("jonny","ROLE_MANAGER");
			userService.addRoleToUser("sylver","ROLE_MANAGER");
			userService.addRoleToUser("jonny","ROLE_ADMIN");


		};
	}
}
