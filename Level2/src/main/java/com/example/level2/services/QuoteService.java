package com.example.level2.services;


import com.example.level2.entity.QuoteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class QuoteService {

    private static final String REDIS_KEY = "daily_quote";

    @Value("${api.quote.key}")
    private String apiKey;

    @Value("${api.quote.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public QuoteEntity getQuote() {

        QuoteEntity cachedQuote = (QuoteEntity) redisTemplate.opsForValue().get(REDIS_KEY);
        if (cachedQuote != null) {
            Long ttl = redisTemplate.getExpire(REDIS_KEY, TimeUnit.SECONDS);
            log.info("Redis TTL = {} seconds", ttl);
            return cachedQuote;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<QuoteEntity[]> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, QuoteEntity[].class);

        QuoteEntity[] quotes = response.getBody();
        if (quotes == null || quotes.length == 0) {
            return null;
        }

        QuoteEntity quote = quotes[0];
        redisTemplate.opsForValue().set(REDIS_KEY, quote, 1, TimeUnit.MINUTES);

        return quote;
    }
}
