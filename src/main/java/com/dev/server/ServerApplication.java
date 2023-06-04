package com.dev.server;

import com.dev.server.enumaration.Status;
import com.dev.server.model.Server;
import com.dev.server.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepo serverRepo){
		return args -> {
			serverRepo.save(new Server(null,"192.168.1.160","Ubuntu Linux","16 GB","Personal PC",
					"http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
			serverRepo.save(new Server(null,"192.168.1.47","Kali Linux","16 GB","Dell Tower",
					"http://localhost:8080/server/image/server2.png", Status.SERVER_UP));
			serverRepo.save(new Server(null,"192.168.1.58","MS 2008","32 GB","Web Server",
					"http://localhost:8080/server/image/server3.png", Status.SERVER_UP));
			serverRepo.save(new Server(null,"192.168.1.24","Red Hat Linux","64 GB","Mail server",
					"http://localhost:8080/server/image/server4.png", Status.SERVER_UP));
		};
	}
}
