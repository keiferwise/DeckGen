package com.kif.deckservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.kif.deckservice.config.AppProperties;

@SpringBootApplication
@ComponentScan({"com.kif.deckservice", "com.kif.deckgenmodels"})
//@EnableConfigurationProperties(AppProperties.class)
//@ConfigurationPropertiesScan("com.kif.deckservice.config")
public class DeckserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeckserviceApplication.class, args);
	}
}
