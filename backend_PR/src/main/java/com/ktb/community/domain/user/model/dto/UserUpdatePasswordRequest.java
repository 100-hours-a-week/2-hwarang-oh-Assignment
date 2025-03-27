package com.ktb.community.domain.user.model.dto;

import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
