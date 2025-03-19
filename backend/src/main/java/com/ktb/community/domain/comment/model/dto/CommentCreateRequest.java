package com.ktb.community.domain.comment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {
    private Long userId;
    private String content;
}
