package com.sparta.reviewspotproject.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestDtoTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 SignupRequestDto 검증(성공)")
    void testValidSignupRequestDto() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUserId("abc123");
        dto.setPassword("Abcd123$%");
        dto.setUserName("최지연");
        dto.setEmail("abc@gmail.com");

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        assertEquals(0, violations.size(), "유효한 DTO(성공)");
    }

    @Test
    @DisplayName("잘못된 사용자 ID 검증")
    public void testInvalidUserId() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUserId("abc");
        dto.setPassword("Abcd123$%");
        dto.setUserName("최지연");
        dto.setEmail("abc@gmail.com");

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "잘못된 사용자 ID입니다.");
    }

    @Test
    @DisplayName("잘못된 패스워드 검증")
    public void testInvalidPassword() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUserId("abc123");
        dto.setPassword("abcd123");
        dto.setUserName("최지연");
        dto.setEmail("abc@gmail.com");

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "잘못된 패스워드입니다.");
    }

    @Test
    @DisplayName("잘못된 이메일 검증")
    public void testInvalidEmail() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUserId("abc123");
        dto.setPassword("Abcd123$%");
        dto.setUserName("최지연");
        dto.setEmail("abcgmail.com"); // 잘못된 이메일

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "잘못된 이메일입니다.");
    }

    @Test
    @DisplayName("빈 사용자 이름 검증")
    public void testBlankUserName() {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUserId("abc123");
        dto.setPassword("Abcd123$%");
        dto.setUserName(""); // 빈 이름
        dto.setEmail("abc@gmail.com");

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size(), "빈 이름입니다.");
    }
}