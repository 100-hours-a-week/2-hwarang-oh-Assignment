package com.ktb.community.global.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.domain.user.repository.UserRepository;
import com.ktb.community.external.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JWTAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ① 쿠키에서 Access Token 추출
        String accessToken = extractTokenFromCookie(request);

        // ② 토큰 유효성 검증
        if (accessToken != null) {
            Long userId = jwtUtil.validateToken(accessToken);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // ③ 유저 조회 및 인증 정보 생성
                User user = userRepository.findById(userId)
                        .orElse(null);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user.getId(), // Principal (보통 username이나 userId)
                            null,
                            List.of() // 권한이 필요하면 user.getRole() 등에서 추출
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // ④ 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
