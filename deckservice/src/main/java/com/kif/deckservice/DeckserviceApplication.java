package com.kif.deckservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.kif.deckservice", "com.kif.deckgenmodels"})
public class DeckserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeckserviceApplication.class, args);
	}
}
