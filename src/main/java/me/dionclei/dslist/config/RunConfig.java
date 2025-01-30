package me.dionclei.dslist.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import me.dionclei.dslist.entities.GameUser;
import me.dionclei.dslist.entities.enums.UserRole;
import me.dionclei.dslist.repositories.UserRepository;

@Configuration
public class RunConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		GameUser u1 = new GameUser("Dionclei de Souza Pereira", "dionclei2@gmail.com", "12341234", UserRole.ADMIN);
		GameUser u2 = new GameUser("Pedro Zeus", "pedrozeus@gmail.com", "321321321", UserRole.USER);
		userRepository.saveAll(Arrays.asList(u1, u2));
	}

}
