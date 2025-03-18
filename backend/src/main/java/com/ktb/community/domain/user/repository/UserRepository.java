package com.ktb.community.domain.user.repository;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktb.community.domain.user.model.entity.User;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setNickname(rs.getString("nickname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setProfileImageUrl(rs.getString("profile_image_url"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        user.setDeletedAt(
                rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        return user;
    };

    // TYPE : Create => Insert User into users table
    public void createUser(User user) {
        String query = "INSERT INTO users (nickname, email, password, profile_image_url, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
        jdbcTemplate.update(query, user.getNickname(), user.getEmail(), user.getPassword(),
                user.getProfileImageUrl());
    }

    // TYPE : Retrieve => Select User by id from users table
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ? AND deleted_at IS NULL";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // TYPE : Retrieve => Select User by email from users table ( Duplicate Check )
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ? AND deleted_at IS NULL";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // TYPE : Update => Update User by id from users table
    public void updateUser(User user) {
        String query = "UPDATE users SET nickname = ?, profile_image_url = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, user.getNickname(), user.getProfileImageUrl(), user.getId());
    }

    // TYPE : Update => Update User Password by id from users table
    public void updateUserPassword(Long id, String password) {
        String query = "UPDATE users SET password = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, password, id);
    }

}
