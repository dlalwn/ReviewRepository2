package com.sparta.reviewspotproject.security;

import com.sparta.reviewspotproject.entity.User;
import com.sparta.reviewspotproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public void updateRefreshToken(String userId, String refreshToken) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    public User findByUserId(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(()->
                new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return user;
    }

    public String getRefreshToken(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getRefreshToken();
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + userId));

        return new UserDetailsImpl(user);
    }
}
