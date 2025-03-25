package com.ktb.community.domain.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String email;
    private String nickname;
    private String password;
    private String profileImageUrl;
}
