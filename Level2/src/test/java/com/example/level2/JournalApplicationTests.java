package com.example.level2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootTest
class JournalApplicationTests {
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl(); // empty stub
    }
	@Test
	void contextLoads() {
	}

}
