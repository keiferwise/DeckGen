package com.kif.deckgen.services;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kif.deckgen.models.ImageResult;

@Service 
public class DalleClient {

	//@Autowired
    private RestTemplate restTemplate;
	
	@Value("${com.kif.api-key}")
    private String API_KEY;
	
	
    public ImageResult generateImage(String prompt) {

    	System.out.println("Making art for prompt: " + prompt);
    	
        String url = "https://api.openai.com/v1/images/generations";
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        
        prompt = prompt + ". Baroque Fantasy Painting.";
        
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("size", "1024x1024");
        requestBody.put("prompt", prompt);
        //requestBody.("n", 1);
        requestBody.put("response_format", "url");
        

        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
        System.out.println("Posting request..");
        ResponseEntity<ImageResult> responseEntity = restTemplate.postForEntity(url, requestEntity, ImageResult.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
           ImageResult responseBody = responseEntity.getBody();
            System.out.println(responseEntity.hasBody());
            System.out.println(responseBody.getCreated());
            System.out.println(responseBody.getData().toString());
            //System.out.println(responseEntity.toString());
            
            System.out.println(responseEntity.getBody());
            
            /*for (byte b : responseBody) {
            	System.out.print(b);
            	
            }*/
            
            
            
            // do something with the image data...
            return responseBody;
        } else {
            return null;
        }
    }
}