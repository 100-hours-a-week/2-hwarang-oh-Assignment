package com.ktb.community.domain.comment.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ktb.community.domain.comment.model.entity.Comment;
import com.ktb.community.domain.comment.model.dto.CommentRequest;

/**
 * IMP : Comment Repository
 * TYPE : CRUD 순서로 DB 접근 로직을 구현하였다.
 */
@Repository
public class CommentRepository {

    // IMP : Constructor Injection
    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * IMP : RowMapper for Comment Entity
     * ? Why : Query의 결과를 Comment Entity와 Mapping하기 위해 사용
     */
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

    // TYPE : Create => Insert Comment
    public void create(Long id, CommentRequest request) {
        String query = "INSERT INTO comments (user_id, post_id, content, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
        jdbcTemplate.update(query, request.getUserId(), id, request.getContent());
    }

    // TYPE : Retrieve => Select Comments by Post Id
    public List<Comment> findByPostId(Long id) {
        String query = "SELECT * FROM comments WHERE post_id = ? AND deleted_at IS NULL ORDER BY created_at DESC";
        return jdbcTemplate.query(query, commentRowMapper, id);
    }

    // TYPE : Retrieve => Select Post Id by Comment Id
    public Long findPostIdByCommentId(Long id) {
        String query = "SELECT post_id FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(query, Long.class, id);
    }

    // TYPE : Update => Update Comment
    public void update(Long id, String content) {
        String query = "UPDATE comments SET content = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, content, id);
    }

    // TYPE : Delete => Delete Comment
    public void delete(Long id) {
        String query = "UPDATE comments SET deleted_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

}
