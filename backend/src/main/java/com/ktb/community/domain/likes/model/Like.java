package com.ktb.community.domain.likes.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Like {
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;
}
