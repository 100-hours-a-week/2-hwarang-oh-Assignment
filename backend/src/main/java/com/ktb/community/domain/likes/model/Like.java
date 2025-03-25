package com.ktb.community.domain.likes.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;
}
