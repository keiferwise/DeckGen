package com.kif.deckgen.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service 
public class DalleClient {

	//@Autowired
    private RestTemplate restTemplate;

    public String generateImage(String prompt) {
        String url = "https://api.openai.com/v1/images/generations";
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("<YOUR_API_KEY>");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("model", "image-alpha-001");
        requestBody.add("prompt", prompt);
        requestBody.add("num_images", "1");

        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestEntity, byte[].class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            byte[] responseBody = responseEntity.getBody();
            // do something with the image data...
            return "Image generated successfully!";
        } else {
            return "Failed to generate image!";
        }
    }
}