package com.example.level2.services;


import com.example.level2.entity.QuoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class QuoteService {

    @Value("${api.quote.key}")
    private String apiKey;

    @Value("${api.quote.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public QuoteEntity[] getQuotes(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<QuoteEntity[]> response = restTemplate.exchange(url, HttpMethod.GET,entity,QuoteEntity[].class);
        return response.getBody();
    }

}
