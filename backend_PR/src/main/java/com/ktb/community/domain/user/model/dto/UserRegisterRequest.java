package com.ktb.community.domain.user.model.dto;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private String email;
    private String nickname;
    private String password;
    private String profileImageUrl;
}
