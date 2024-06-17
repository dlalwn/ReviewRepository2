package com.sparta.reviewspotproject.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class Message {

    private int statusCode;
    private String message;

    public Message(String message, HttpStatus statusCode) {
        this.statusCode = statusCode.value();
        this.message = message;
    }
}