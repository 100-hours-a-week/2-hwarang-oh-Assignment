package com.ktb.community.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.comment.model.entity.Comment;
import com.ktb.community.domain.comment.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void createComment(Comment comment) {
        commentRepository.createComment(comment);
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void updateComment(Long id, String content) {
        commentRepository.updateComment(id, content);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteComment(id);
    }

}
