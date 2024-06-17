package com.sparta.reviewspotproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReviewSpotProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewSpotProjectApplication.class, args);
    }

}