package com.ktb.community.domain.user.model.dto;

import com.ktb.community.domain.user.model.entity.User;

import lombok.Getter;

@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
