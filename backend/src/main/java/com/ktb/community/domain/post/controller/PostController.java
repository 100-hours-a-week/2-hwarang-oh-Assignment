package com.ktb.community.domain.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.likes.model.LikeStatus;
import com.ktb.community.domain.post.model.dto.PostCreateRequest;
import com.ktb.community.domain.post.model.dto.PostUpdateRequest;
import com.ktb.community.domain.post.model.entity.Post;
import com.ktb.community.domain.post.service.PostService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // IMP : Create Post -> createPost Service
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        Post post = new Post();
        post.setTitle(postCreateRequest.getTitle());
        post.setContent(postCreateRequest.getContent());
        post.setPostImageUrl(postCreateRequest.getPostImageUrl());
        postService.createPost(postCreateRequest.getUserId(), post);
        return ResponseEntity.ok("Post created successfully");
    }

    // IMP : Get Posts -> getPosts Service
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    // IMP : Get Post By Id -> getPostById Service
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // IMP : Revise Post -> revisePost Service
    @PutMapping("/{id}")
    public ResponseEntity<String> revisePost(@PathVariable("id") Long id,
            @RequestBody PostUpdateRequest postUpdateRequest) {
        Post post = new Post();
        post.setTitle(postUpdateRequest.getTitle());
        post.setContent(postUpdateRequest.getContent());
        post.setPostImageUrl(postUpdateRequest.getPostImageUrl());
        postService.revisePost(id, post.getTitle(), post.getContent(), post.getPostImageUrl());
        return ResponseEntity.ok("Post revised successfully");

    }

    // IMP : Remove Post -> removePost Service
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePost(@PathVariable("id") Long id) {
        postService.removePost(id);
        return ResponseEntity.ok("Post removed successfully");
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable("id") Long id, @RequestParam Long userId) {
        LikeStatus likeStatus = postService.toggleLike(id, userId);
        if (likeStatus == LikeStatus.UNLIKED)
            return ResponseEntity.ok("Post unliked successfully");
        else
            return ResponseEntity.ok("Post liked successfully");
    }
}
