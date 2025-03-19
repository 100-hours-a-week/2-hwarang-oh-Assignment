package com.ktb.community.domain.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ktb.community.domain.post.model.entity.Post;
import com.ktb.community.domain.post.model.dto.PostCreateRequest;
import com.ktb.community.domain.post.model.dto.PostUpdateRequest;

/**
 * IMP : Post Repository
 * TYPE : CRUD 순서로 DB 접근 로직을 구현하였다.
 */
@Repository
public class PostRepository {

    // IMP : Constructor Injection
    private final JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * IMP : RowMapper for Post Entity
     * ? Why : Query의 결과를 Post Entity와 Mapping하기 위해 사용
     */
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

    // TYPE : Create => Insert Post
    public void create(PostCreateRequest request) {
        String query = "INSERT INTO posts (user_id, title, content, post_image_url, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
        jdbcTemplate.update(query, request.getUserId(), request.getTitle(), request.getContent(),
                request.getPostImageUrl());
    }

    /**
     * TYPE : Retrieve => Select Post by Id
     * ? Why : 게시글의 정보를 조회하기 위해 사용
     * IMP : Optional ( Retrieve Method -> Nullable )
     * 
     * @param id
     * @return
     */
    public Optional<Post> findById(Long id) {
        String query = "SELECT * FROM posts WHERE id = ? AND deleted_at IS NULL";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, postRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * TYPE : Retrieve => Check Existence of Post by Id
     * ? Why : 게시글의 존재 여부를 확인하기 위해 사용
     * 
     * @return
     */
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(*) FROM posts WHERE id = ? AND deleted_at IS NULL";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, id);
        return count != null && count > 0;
    }

    /**
     * TYPE : Retrieve => Select All Posts
     * ? Why : 모든 게시글의 정보를 조회하기 위해 사용
     * IMP : List ( Retrieve Method -> Multiple )
     * 
     * @return
     */
    public List<Post> findAll() {
        String query = "SELECT * FROM posts WHERE deleted_at IS NULL ORDER BY created_at DESC";
        return jdbcTemplate.query(query, postRowMapper);
    }

    // TYPE : Update => Update Post by Id
    // TODO : UserId의 일치 여부를 확인하는 로직을 추가해야 하는가?
    public void update(Long id, PostUpdateRequest request) {
        String query = "UPDATE posts SET title = ?, content = ?, post_image_url = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, request.getTitle(), request.getContent(), request.getPostImageUrl(), id);
    }

    // TYPE : Delete => Delete Post by Id
    // TODO : UserId의 일치 여부를 확인하는 로직을 추가해야 하는가?
    public void delete(Long id) {
        String query = "UPDATE posts SET deleted_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    // TYPE : Update View Count
    public void updateViewCount(Long id) {
        String query = "UPDATE posts SET views_count = views_count + 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    // TYPE : Increase Update of Like Count
    public void increaseLikeCount(Long id) {
        String query = "UPDATE posts SET likes_count = likes_count + 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    // TYPE : Decrease Update of Like Count
    public void decreaseLikeCount(Long id) {
        String query = "UPDATE posts SET likes_count = likes_count - 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    // TYPE : Increase Update of Comment Count
    public void increaseCommentCount(Long id) {
        String query = "UPDATE posts SET comments_count = comments_count + 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    // TYPE : Decrease Update of Comment Count
    public void decreaseCommentCount(Long id) {
        String query = "UPDATE posts SET comments_count = comments_count - 1 WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

}
