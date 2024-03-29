package com.kif.deckgen;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.authorizeHttpRequests((authorize) -> authorize
			            .requestMatchers("/create-user").hasRole("ADMIN")
			            .requestMatchers("/user-list").hasRole("ADMIN")
						.anyRequest().authenticated()
						
				)
				
				.httpBasic(withDefaults())
				.formLogin(withDefaults());
		// @formatter:on
		return http.build();
	}

	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, role,user_id from users where username=?")
        ;
    }
	
}