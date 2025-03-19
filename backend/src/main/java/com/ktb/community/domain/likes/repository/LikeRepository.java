package com.ktb.community.domain.likes.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {

    private final JdbcTemplate jdbcTemplate;

    public LikeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Long userId, Long postId) {
        String sql = "INSERT INTO likes (user_id, post_id, created_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, userId, postId);
    }

    public boolean findByPostIdandUserId(Long userId, Long postId) {
        String sql = "SELECT EXISTS (SELECT * FROM likes WHERE user_id = ? AND post_id = ?)";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Boolean.class, userId, postId)).orElse(false);
    }

    public void removeLike(Long userId, Long postId) {
        String sql = "DELETE FROM likes WHERE user_id = ? AND post_id = ?";
        jdbcTemplate.update(sql, userId, postId);
    }
}
