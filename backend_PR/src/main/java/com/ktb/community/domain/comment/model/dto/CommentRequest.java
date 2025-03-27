package com.ktb.community.domain.comment.model.dto;

import lombok.Getter;

@Getter
public class CommentRequest {
    private Long userId;
    private String content;
}
