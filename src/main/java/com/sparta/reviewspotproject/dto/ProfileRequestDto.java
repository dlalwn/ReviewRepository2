package com.sparta.reviewspotproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String userName;

    @NotBlank(message = "내용을 입력해주세요.")
    private String tagLine;


}