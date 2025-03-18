package com.ktb.community.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (API 사용 시 필요)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 🔥 모든 요청 인증 없이 허용
                )
                .formLogin(login -> login.disable()) // 기본 로그인 폼 비활성화
                .httpBasic(basic -> basic.disable()); // 기본 HTTP Basic 인증 비활성화

        return http.build();
    }
}
