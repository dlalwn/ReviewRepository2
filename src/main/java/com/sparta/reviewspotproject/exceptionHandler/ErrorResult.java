package com.sparta.reviewspotproject.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String statusCode;
    private String message;

}