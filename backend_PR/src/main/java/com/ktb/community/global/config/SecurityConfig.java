package com.ktb.community.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import com.ktb.community.external.jwt.handler.JwtAuthenticationEntryPoint;
import com.ktb.community.global.filter.JwtAuthenticationFilter;

/**
 * IMP : Security Configuration
 * * CSRF Protection Disable -> Token 기반 인증 사용하기에 CSRF Protection 비활성화
 * * CORS -> 기본 설정으로 활성화, 추가 설정은 필요에 따라 설정
 * * Session Management -> Session을 생성하지 않는 Stateless로 설정
 * * Request Authorization -> /api/users ( 회원 가입 ), /auth/login, /auth/refresh
 * * Form Login, HTTP Basic -> 비활성화
 * * Custom Filter -> JWTAuthenticationFilter을
 * * UsernamePasswordAuthenticationFilter 이전에 동작하도록 지정함.
 */

// TODO : JWT Token을 검증하는 Custom Filter 추가
// TODO : AUTH 로직이 완료되면 anyRequest().authenticated()로 변경
@Configuration
public class SecurityConfig {
        // TYPE : JWT Token을 검증하는 Custom Filter
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final CorsConfigurationSource corsConfigurationSource;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                        CorsConfigurationSource corsConfigurationSource,
                        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.corsConfigurationSource = corsConfigurationSource;
                this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // IMP : CSRF Protection Disable -> Token 기반 인증 사용
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(requesetConfigurer -> requesetConfigurer
                                                .requestMatchers("/api/users", "auth/login", "auth/refresh").permitAll()
                                                // .anyRequest().permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(login -> login.disable())
                                .httpBasic(basic -> basic.disable())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(handling -> handling
                                                .authenticationEntryPoint(jwtAuthenticationEntryPoint));
                return http.build();
        }
}
