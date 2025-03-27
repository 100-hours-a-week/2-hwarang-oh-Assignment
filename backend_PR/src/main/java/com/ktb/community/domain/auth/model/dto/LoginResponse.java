package com.ktb.community.domain.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;
}
