package com.ktb.community.domain.user.model.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String nickname;
    private String profileImageUrl;
}
