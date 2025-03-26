package com.ktb.community.domain.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.domain.auth.model.dto.LoginRequest;
import com.ktb.community.domain.auth.model.dto.LoginResponse;
import com.ktb.community.domain.user.repository.UserRepository;
import com.ktb.community.external.jwt.service.JwtService;
import com.ktb.community.external.redis.repository.RedisRepository;
import com.ktb.community.global.util.PasswordUtil;
import com.ktb.community.global.exception.ErrorCode;
import com.ktb.community.global.exception.BaseException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private final JwtService jwtUtil;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;

    public AuthService(JwtService jwtUtil, UserRepository userRepository,
            RedisRepository redisRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.redisRepository = redisRepository;
    }

    // IMP : Login -> redisRepository.save ( Token 발급 Logic )
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        // ? : 해당 Email 주소를 가진 사용자가 존재하지 않을 경우, INVALID_USER 에러를 발생
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_USER));

        // ? : 입력된 비밀번호와 사용자의 비밀번호가 일치하지 않을 경우, INVALID_USER 에러 발생
        if (!PasswordUtil.match(loginRequest.getPassword(), user.getPassword()))
            throw new BaseException(ErrorCode.INVALID_USER);

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        // TYPE : accessToken을 Cookie에 저장
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtUtil.getAccessTokenExpiration());
        response.addCookie(cookie);

        // TYPE : refreshToken을 Redis에 저장
        redisRepository.save(user.getId(), refreshToken, jwtUtil.getRefreshTokenExpiration());
        return new LoginResponse(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImageUrl());
    }

    // IMP : Refresh -> redisRepository.get ( Token 갱신 Logic )
    public void refresh(Long userId, HttpServletResponse response) {
        Optional<String> refreshToken = redisRepository.get(userId);
        if (refreshToken.isEmpty())
            throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));

        String accessToken = jwtUtil.generateAccessToken(user);

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtUtil.getAccessTokenExpiration());
        response.addCookie(cookie);
    }

    // IMP : Logout -> redisRepository.delete ( Token 삭제 Logic )
    public void logout(Long userId, HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        redisRepository.delete(userId);
    }
}
