package com.ktb.community.domain.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
