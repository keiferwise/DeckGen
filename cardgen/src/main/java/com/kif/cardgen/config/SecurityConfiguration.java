
package com.kif.cardgen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class SecurityConfiguration {

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {

        return (webSecurity) -> webSecurity.ignoring().requestMatchers("/create-card","/create-card-for-deck");
    }
    
    /*
    @Bean
    StrictHttpFirewall httpFirewall() {
      StrictHttpFirewall strictHttpFireWall = new StrictHttpFirewall();
      strictHttpFireWall.setAllowBackSlash(true);
      //strictHttpFireWall.setAllowedHostnames(hostName ->             
      //hostName.equals("http://localhost"));
      strictHttpFireWall.setAllowedHostnames(hostName ->             
      hostName.equals("localhost"));
      //strictHttpFireWall.setAllowedHostnames(hostName ->             
      //hostName.equals("127.0.0.1"));
      return strictHttpFireWall;
    }
    */
}