package nl.hu.bep.lingogame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("nl.hu.bep.lingogame.persistence.repo")
@EntityScan("nl.hu.bep.lingogame.persistence.model")
@SpringBootApplication
public class LingogameApplication {

	public static void main(String[] args) {
		SpringApplication.run(LingogameApplication.class, args);
	}

}
