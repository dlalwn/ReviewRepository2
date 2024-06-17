package com.sparta.reviewspotproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VerifyCodeRequestDto {
    @Email(message = "유효한 이메일을 입력해주세요.")
    @NotBlank(message = "Email을 입력해주세요.")
    private String email;

    @NotNull(message = "Email로 발송된 인증번호를 입력해주세요.")
    private String verificationCode;

    public VerifyCodeRequestDto(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }
}
