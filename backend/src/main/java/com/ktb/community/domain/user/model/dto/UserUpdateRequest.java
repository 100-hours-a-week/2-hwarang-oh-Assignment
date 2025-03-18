package com.ktb.community.domain.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String nickname;
    private String profileImageUrl;
}
