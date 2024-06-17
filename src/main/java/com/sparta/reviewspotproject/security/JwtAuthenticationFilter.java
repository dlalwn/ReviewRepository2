package com.sparta.reviewspotproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.reviewspotproject.dto.LoginRequestDto;
import com.sparta.reviewspotproject.entity.User;
import com.sparta.reviewspotproject.entity.UserStatus;
import com.sparta.reviewspotproject.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // 사용자의 상태를 확인하고, NON_MEMBER 상태인 경우 로그인을 허용하지 않음
            User user = userDetailsService.findByUserId(requestDto.getUserId());
            if (user != null && user.getUserStatus() == UserStatus.NON_MEMBER) {
                unsuccessfulAuthentication(request, response, new BadCredentialsException("탈퇴한 회원입니다."));
                return null;
            }

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.createToken(username); // 액세스 토큰 생성
        String refreshToken = jwtUtil.createRefreshToken(username); // 리프레시 토큰 생성
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token); // 액세스 토큰을 헤더에 추가

        userDetailsService.updateRefreshToken(username,refreshToken);

        // 리프레시 토큰을 쿠키에 저장하여 클라이언트에 전달
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(12 * 60 * 60); // 리프레시 토큰의 유효 기간 설정 (초 단위)
        refreshTokenCookie.setPath("/"); // 쿠키 경로 설정
        response.addCookie(refreshTokenCookie); // 응답에 쿠키 추가


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write("{\"message\": \"로그인에 성공하였습니다.\"}");
        } catch (IOException e) {
            log.error("Error writing to response: {}", e.getMessage());
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.error("Authentication failed: {}", failed.getMessage());
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            if (failed instanceof BadCredentialsException) {
                response.getWriter().write("{\"message\": \"탈퇴한 회원입니다.\"}");
            }else {
                response.getWriter().write("{\"message\": \"로그인에 실패하였습니다.\"}");
            }
        } catch (IOException e) {
            log.error("Error writing to response: {}", e.getMessage());
        }
    }

}