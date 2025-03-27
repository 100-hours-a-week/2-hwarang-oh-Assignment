package com.ktb.community.domain.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.comment.model.entity.Comment;
import com.ktb.community.domain.comment.service.CommentService;
import com.ktb.community.domain.comment.model.dto.CommentRequest;

/**
 * IMP : CommentController ( REST API )
 * TYPE : EndPoint : /api/comments
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // IMP : Create Comment -> createComment Service
    @PostMapping("/{postId}")
    public ResponseEntity<String> createComment(@PathVariable("postId") Long postId,
            @RequestBody CommentRequest request) {
        commentService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글 작성을 성공적으로 완료 ✅");
    }

    // IMP : Get Comments -> getComments Service
    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("postId") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findByPostId(postId));
    }

    // IMP : Update Comment -> updateComment Service
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Long commentId,
            @RequestBody CommentRequest request) {
        commentService.updateComment(commentId, request);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 수정을 성공적으로 완료 ✅");
    }

    // IMP : Delete Comment -> deleteComment Service
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글 삭제를 성공적으로 완료 ✅");
    }

}
