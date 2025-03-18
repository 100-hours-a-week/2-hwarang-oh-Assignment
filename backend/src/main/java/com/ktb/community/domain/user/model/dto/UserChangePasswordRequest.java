package com.ktb.community.domain.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
