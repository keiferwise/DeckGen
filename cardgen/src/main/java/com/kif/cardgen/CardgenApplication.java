package com.kif.cardgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.kif.cardgen", "com.kif.deckgenmodels"})
public class CardgenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardgenApplication.class, args);
	}

}
