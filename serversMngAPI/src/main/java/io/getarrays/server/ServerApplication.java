package io.getarrays.server;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.getarrays.server.enumerations.Status;
import io.getarrays.server.models.Server;
import io.getarrays.server.repositories.ServerRepo;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepo serverRepo){
		return args -> {
			serverRepo.save(new Server(
				null,
				"192.168.1.76",
				"Ubuntu Linux",
				"32 GB",
				"Personal PC",
				"http://localhost:8080/server/image/server1.jpg",
				Status.SERVER_DOWN
				));
			serverRepo.save(new Server(
				null,
				"192.168.1.35",
				"Huawei Phone",
				"16 GB",
				"Mobile Phone",
				"http://localhost:8080/server/image/server3.jpg",
				Status.SERVER_DOWN
				));
				serverRepo.save(new Server(
					null,
					"192.168.1.43",
					"LG TV",
					"4 GB",
					"Smart TV",
					"http://localhost:8080/server/image/server2.jpg",
					Status.SERVER_DOWN));
		};
		// aqui pueden ir mas serverRepo.save...
	};

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration(); 
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:3000"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Content-Type","Accept","Jwt-Token","Authorization","Origin, Accept","X-Requested-With","Access-Control-Request-Method","Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept","Jwt-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
 