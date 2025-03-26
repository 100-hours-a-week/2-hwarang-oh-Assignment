package com.ktb.community.domain.post.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCreateRequest {
    private Long userId;
    private String title;
    private String content;
    private String postImageUrl;
}
