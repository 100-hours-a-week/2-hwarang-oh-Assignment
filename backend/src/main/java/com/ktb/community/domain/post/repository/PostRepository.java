package com.ktb.community.domain.post.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktb.community.domain.post.model.entity.Post;

@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setUserId(rs.getLong("user_id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setViewsCount(rs.getInt("views_count"));
        post.setLikesCount(rs.getInt("likes_count"));
        post.setCommentsCount(rs.getInt("comments_count"));
        post.setPostImageUrl(rs.getString("post_image_url"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        post.setDeletedAt(
                rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        return post;
    };

    public void createPost(
            Post post) {
        String query = "INSERT INTO posts (user_id, title, content, post_image_url, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
        jdbcTemplate.update(query, post.getUserId(), post.getTitle(), post.getContent(), post.getPostImageUrl());
    }

    public Post findById(Long id) {
        String query = "SELECT * FROM posts WHERE id = ? AND deleted_at IS NULL";
        return jdbcTemplate.queryForObject(query, postRowMapper, id);
    }

    public List<Post> findAll() {
        String query = "SELECT * FROM posts WHERE deleted_at IS NULL";
        return jdbcTemplate.query(query, postRowMapper);
    }

    public void updatePost(Long id, String title, String content, String postImageUrl) {
        String query = "UPDATE posts SET title = ?, content = ?, post_image_url = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, title, content, postImageUrl, id);
    }

    public void updateViewCount(Long id) {
        String query = "UPDATE posts SET views_count = views_count + 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public void deletePost(Long id) {
        String query = "UPDATE posts SET deleted_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public void increaseLikeCount(Long id) {
        String query = "UPDATE posts SET likes_count = likes_count + 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public void decreaseLikeCount(Long id) {
        String query = "UPDATE posts SET likes_count = likes_count - 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

}
