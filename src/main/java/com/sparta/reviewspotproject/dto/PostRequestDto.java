package com.sparta.reviewspotproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    @NotBlank
    private String contents;
}
