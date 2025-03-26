package com.ktb.community.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.auth.model.dto.LoginRequest;
import com.ktb.community.domain.auth.model.dto.LoginResponse;
import com.ktb.community.domain.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * IMP : Auth Controller ( REST API )
 * TYPE : EndPoint : /auth
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // IMP : Login -> login Service
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginRequest, response));
    }

    // IMP : Refresh -> refresh Service
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestParam("userId") Long userId, HttpServletResponse response) {
        authService.refresh(userId, response);
        return ResponseEntity.ok("토큰 갱신 성공 ✅");
    }

    // IMP : Logout -> logout Service
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("userId") Long userId, HttpServletResponse response) {
        authService.logout(userId, response);
        return ResponseEntity.ok("로그아웃 성공 ✅");
    }
}
