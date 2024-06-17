package com.sparta.reviewspotproject.service;

import com.sparta.reviewspotproject.dto.PasswordRequestDto;
import com.sparta.reviewspotproject.dto.ProfileRequestDto;
import com.sparta.reviewspotproject.dto.ProfileResponseDto;
import com.sparta.reviewspotproject.entity.User;
import com.sparta.reviewspotproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 프로필 조회
    public ProfileResponseDto getProfile(User user) {
        User currentUser = findById(user);
        ProfileResponseDto responseDto = new ProfileResponseDto(user);
        return responseDto;
    }

    // 사용자 프로필 수정
    @Transactional
    public void updateProfile (ProfileRequestDto requestDto, User user) {
        User currentUser = findById(user);
        currentUser.update(requestDto);
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(PasswordRequestDto requestDto, User user) {
        User currentUser = findById(user);
        String password = requestDto.getPassword();
        String changePassword = requestDto.getChangePassword();

        // 현재 비밀번호가 사용자의 비밀번호와 맞는지 검증
        if (!passwordEncoder.matches(password, currentUser.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호와 사용자의 비밀번호가 일치하지 않습니다.");
        }

        // 변경할 비밀번호와 현재 비밀번호가 동일한지 검증
        if (passwordEncoder.matches(changePassword, currentUser.getPassword())) {
            throw new IllegalArgumentException("동일한 비밀번호로는 변경할 수 없습니다.");
        }
        // 변경할 비밀번호로 수정
        currentUser.setPassword(passwordEncoder.encode(changePassword));
    }

    private User findById(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자는 존재하지 않습니다.")
        );
    }


}
