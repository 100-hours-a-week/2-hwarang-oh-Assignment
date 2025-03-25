package com.ktb.community.domain.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.likes.model.LikeStatus;
import com.ktb.community.domain.post.model.entity.Post;
import com.ktb.community.domain.post.service.PostService;
import com.ktb.community.domain.post.model.dto.PostCreateRequest;
import com.ktb.community.domain.post.model.dto.PostUpdateRequest;

/**
 * IMP : PostController ( REST API )
 * TYPE : EndPoint : /api/posts
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // IMP : Create Post -> createPost Service
    @PostMapping()
    public ResponseEntity<String> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(postCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("게시물 작성을 성공적으로 완료 ✅");
    }

    // IMP : Get Posts -> getPosts Service
    @GetMapping()
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts());
    }

    // IMP : Get Post By Id -> getPostById Service
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
    }

    // IMP : Revise Post -> revisePost Service
    @PutMapping("/{postId}")
    public ResponseEntity<String> revisePost(@PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest) {

        postService.revisePost(postId, postUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("게시물 수정을 성공적으로 완료 ✅");
    }

    // IMP : Remove Post -> deletePost Service
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("게시물 삭제를 성공적으로 완료 ✅");
    }

    // IMP : Like Post -> toggleLike Service
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable("postId") Long postId, @RequestParam("userId") Long userId) {
        LikeStatus likeStatus = postService.toggleLike(postId, userId);
        if (likeStatus == LikeStatus.UNLIKED)
            return ResponseEntity.ok("게시물 좋아요 취소 👎");
        else
            return ResponseEntity.ok("게시물 좋아요 👍");
    }
}
