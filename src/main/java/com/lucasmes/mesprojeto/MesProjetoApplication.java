package com.lucasmes.mesprojeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.lucasmes.repository")
@EntityScan("com.lucasmes.entity")
public class MesProjetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesProjetoApplication.class, args);
	}

}
