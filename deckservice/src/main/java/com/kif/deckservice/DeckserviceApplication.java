package com.kif.deckservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.kif.deckservice.config.AppProperties;

//import com.kif.deckservice.config.AppProperties;

@SpringBootApplication
@ComponentScan({"com.kif.deckservice", "com.kif.deckgenmodels"})
@EnableConfigurationProperties(AppProperties.class)
@ConfigurationPropertiesScan("com.kif.deckservice.config")
public class DeckserviceApplication {

	//@Value("${spring.datasource.password}")
	//private static String p;
	//@Value("${com.kif.sharedsecret}")
	//private static String s;
	public static void main(String[] args) {
		//System.out.println("The secret is "+s +" and "+p);
		SpringApplication.run(DeckserviceApplication.class, args);
	}
}
