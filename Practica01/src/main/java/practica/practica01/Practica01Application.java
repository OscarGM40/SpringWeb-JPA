package practica.practica01;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import practica.practica01.models.Role;
import practica.practica01.models.User;
import practica.practica01.services.UserService;

@SpringBootApplication
public class Practica01Application {

	public static void main(String[] args) {
		SpringApplication.run(Practica01Application.class, args);
	}

		@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			/* creo un par de roles */
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			/* creo un par de users */
			userService.saveUser(new User(null,"John Travolta","jonny","123456",new ArrayList<Role>()));
			userService.saveUser(new User(null,"Will Smith","will","123456",new ArrayList<Role>()));
			/* le asigno roles a los users */
			userService.addRoleToUser("John Travolta","ROLE_USER");
			userService.addRoleToUser("Will Smith","ROLE_USER");
			userService.addRoleToUser("John Travolta","ROLE_ADMIN");
		};
	}
}
