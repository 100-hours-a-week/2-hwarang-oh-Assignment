package com.ktb.community.domain.comment.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.comment.model.dto.CommentCreateRequest;
import com.ktb.community.domain.comment.model.entity.Comment;
import com.ktb.community.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public void getComments(@RequestParam("postId") Long postId) {
        commentService.findByPostId(postId);
    }

    @PostMapping("{postId}")
    public void createComment(@RequestParam("postId") Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest) {
        Comment comment = new Comment();
        comment.setUserId(commentCreateRequest.getUserId());
        comment.setPostId(postId);
        comment.setContent(commentCreateRequest.getContent());

        commentService.createComment(comment);
    }

    @PutMapping("{id}")
    public void updateComment(@PathVariable("id") Long id, @RequestBody CommentCreateRequest commentCreateRequest) {
        commentService.updateComment(id, commentCreateRequest.getContent());
    }

    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }

}
