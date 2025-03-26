package com.ktb.community.external.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.external.jwt.config.JwtProperties;
import com.ktb.community.external.jwt.exception.ExpiredTokenException;
import com.ktb.community.external.jwt.exception.InvalidFormatException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

/**
 * IMP : JWT Service
 */
@Component
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * IMP : Generate Access Token
     * 
     * @param user
     * @return
     */
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("nickname", user.getNickname())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.expiration().access()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * IMP : Generate Refresh Token
     * 
     * @param user
     * @return
     */
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.expiration().refresh()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * IMP : Validate Token
     * 
     * @param token
     * @return
     */
    public boolean isValidToken(String token) throws JwtException {
        if (token == null) {
            return false;
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException exception) {
            throw new InvalidFormatException();
        }
    }

    /**
     * IMP : Parse Token
     * 
     * @param token
     * @return
     */
    public Long parseToken(String token) {
        return Long.parseLong(
                Jwts.parserBuilder()
                        .setSigningKey(jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject());

    }

    public int getAccessTokenExpiration() {
        return (int) jwtProperties.expiration().access();
    }

    public Long getRefreshTokenExpiration() {
        return jwtProperties.expiration().refresh();
    }
}
