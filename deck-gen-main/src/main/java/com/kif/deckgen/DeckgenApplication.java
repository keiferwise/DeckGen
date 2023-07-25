package com.kif.deckgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.kif.deckgen", "com.kif.deckgenmodels"})
public class DeckgenApplication {


	
	public static void main(String[] args) {
		
		SpringApplication.run(DeckgenApplication.class, args);
	}

}
