package com.ktb.community.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.comment.model.dto.CommentRequest;
import com.ktb.community.domain.comment.model.entity.Comment;
import com.ktb.community.domain.comment.repository.CommentRepository;
import com.ktb.community.domain.post.repository.PostRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // IMP : Create Comment -> create
    public void createComment(Long postId, CommentRequest request) {
        commentRepository.create(postId, request);
        postRepository.increaseCommentCount(postId);
    }

    // IMP : Get Comments -> findByPostId
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // IMP : Update Comment -> update
    public void updateComment(Long commentId, CommentRequest request) {
        commentRepository.update(commentId, request.getContent());
    }

    // IMP : Delete Comment -> delete
    public void deleteComment(Long commentId) {
        Long postId = commentRepository.findPostIdByCommentId(commentId);
        commentRepository.delete(commentId);
        postRepository.decreaseCommentCount(postId);
    }
}
