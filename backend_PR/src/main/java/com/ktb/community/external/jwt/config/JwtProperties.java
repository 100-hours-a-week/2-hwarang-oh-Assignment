package com.ktb.community.external.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * IMP : JWT의 설정값을 관리하는 Record
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, Expiration expiration) {
    public record Expiration(long access, long refresh) {
    }
}