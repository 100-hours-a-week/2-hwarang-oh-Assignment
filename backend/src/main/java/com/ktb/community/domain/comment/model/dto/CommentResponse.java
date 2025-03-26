package com.ktb.community.domain.comment.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentResponse {
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
