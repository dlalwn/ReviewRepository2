package com.sparta.reviewspotproject.security;

import com.sparta.reviewspotproject.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(req);

        if (StringUtils.hasText(tokenValue)) {
            String refreshToken = jwtUtil.getRefreshTokenFromCookie(req);
            Claims refreshTokenInfo = jwtUtil.getUserInfoFromToken(refreshToken);
            String username = refreshTokenInfo.getSubject();

            if (!refreshToken.equals(userDetailsService.getRefreshToken(username))) {
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                try {
                    res.getWriter().write("{\"message\": \"재로그인이 필요합니다.\"}");
                    return;
                } catch (IOException e) {
                    log.error("Error writing to response: {}", e.getMessage());
                }
            }

            if (!jwtUtil.validateAccessToken(tokenValue)) {
                // 토큰이 만료된 경우 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급

                String newAccessToken = jwtUtil.createToken(username);
                res.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken); // 새로운 액세스 토큰을 헤더에 추가
                log.info("새로 발행한 액세스 토큰: {}", newAccessToken);


            } else {
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    res.getWriter().write("{\"message\": \" " + e.getMessage() + "\"}");
                    log.error(e.getMessage());
                    return;
                }
            }
        }

        filterChain.doFilter(req, res);
    }


    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
