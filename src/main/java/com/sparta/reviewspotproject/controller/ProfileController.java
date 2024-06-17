package com.sparta.reviewspotproject.controller;

import com.sparta.reviewspotproject.dto.PasswordRequestDto;
import com.sparta.reviewspotproject.dto.ProfileRequestDto;
import com.sparta.reviewspotproject.dto.ProfileResponseDto;
import com.sparta.reviewspotproject.security.UserDetailsImpl;
import com.sparta.reviewspotproject.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 사용자의 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 사용자의 프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody @Valid ProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        profileService.updateProfile(requestDto, userDetails.getUser());
        return new ResponseEntity<>("프로필이 성공적으로 수정되었습니다.", HttpStatus.OK);
    }

    // 비밀번호 변경
    @PutMapping("/profile/password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        profileService.updatePassword(requestDto,userDetails.getUser());
        return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.", HttpStatus.OK);
    }


}