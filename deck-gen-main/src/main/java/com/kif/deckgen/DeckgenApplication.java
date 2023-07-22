package com.kif.deckgen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kif.deckgen.daos.MinioDao;

@SpringBootApplication
public class DeckgenApplication {


	
	public static void main(String[] args) {
		
		SpringApplication.run(DeckgenApplication.class, args);
	}

}
