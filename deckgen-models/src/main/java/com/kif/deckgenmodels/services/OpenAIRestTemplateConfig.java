package com.kif.deckgenmodels.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIRestTemplateConfig {

    private String openaiApiKey = "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";

    @Bean
    @Qualifier("openaiRestTemplate")
    RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}