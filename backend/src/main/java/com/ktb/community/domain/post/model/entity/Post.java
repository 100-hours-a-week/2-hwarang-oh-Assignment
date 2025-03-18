package com.ktb.community.domain.post.model.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private int likesCount;
    private int viewsCount;
    private int commentsCount;
    private String postImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
