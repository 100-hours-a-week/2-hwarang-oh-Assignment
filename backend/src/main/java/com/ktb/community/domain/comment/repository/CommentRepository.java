package com.ktb.community.domain.comment.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktb.community.domain.comment.model.entity.Comment;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
        Comment comment = new Comment();
        comment.setId(rs.getLong("id"));
        comment.setUserId(rs.getLong("user_id"));
        comment.setPostId(rs.getLong("post_id"));
        comment.setContent(rs.getString("content"));
        comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        comment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        comment.setDeletedAt(
                rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        return comment;
    };

    public void createComment(Comment comment) {
        String query = "INSERT INTO comments (user_id, post_id, content, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
        jdbcTemplate.update(query, comment.getUserId(), comment.getPostId(), comment.getContent());
    }

    public List<Comment> findByPostId(Long postId) {
        String query = "SELECT * FROM comments WHERE post_id = ? AND deleted_at IS NULL";
        return jdbcTemplate.query(query, commentRowMapper, postId);
    }

    public void updateComment(Long id, String content) {
        String query = "UPDATE comments SET content = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, content, id);
    }

    public void deleteComment(Long id) {
        String query = "UPDATE comments SET deleted_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

}
