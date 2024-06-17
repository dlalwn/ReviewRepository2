package com.sparta.reviewspotproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;
}
