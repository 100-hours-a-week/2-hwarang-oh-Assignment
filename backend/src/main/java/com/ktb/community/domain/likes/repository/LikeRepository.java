package com.ktb.community.domain.likes.repository;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * IMP : Like Repository
 * TYPE : CRUD 순서로 DB 접근 로직을 구현하였다.
 */
@Repository
public class LikeRepository {

    private final JdbcTemplate jdbcTemplate;

    public LikeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TYPE : Create => Insert Like
    public void addLike(Long postId, Long userId) {
        String sql = "INSERT INTO likes (post_id, user_id, created_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, postId, userId);
    }

    // TYPE : Retrieve => Select Like by User Id and Post Id
    public boolean existsByPostIdandUserId(Long postId, Long userId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM likes WHERE post_id = ? AND user_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, postId, userId);
    }

    // TYPE : Delete => Delete Like
    public void removeLike(Long postId, Long userId) {
        String sql = "DELETE FROM likes WHERE post_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, postId, userId);
    }
}
