package io.getarrays.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
				"192.168.1.160",
				"Ubuntu Linux",
				"16 GB",
				"Personal PC",
				"http://localhost:8080/server/image/server1.png",
				Status.SERVER_UP
				));
		};
		// aqui pueden ir mas serverRepo.save...
	};

}
