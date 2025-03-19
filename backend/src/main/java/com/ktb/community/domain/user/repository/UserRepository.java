package com.ktb.community.domain.user.repository;

import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ktb.community.domain.user.model.dto.UserRegisterRequest;
import com.ktb.community.domain.user.model.dto.UserUpdateRequest;
import com.ktb.community.domain.user.model.entity.User;

/**
 * IMP : User Repository
 * TYPE : CRUD 순서로 DB 접근 로직을 구현하였다.
 */
@Repository
public class UserRepository {

    // IMP : Constructor Injection
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * IMP : RowMapper for User Entity
     * ? Why : Query의 결과를 User Entity와 Mapping하기 위해 사용
     */
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

    // TYPE : Create => Insert User
    public void save(UserRegisterRequest request) {
        String query = "INSERT INTO users (nickname, email, password, profile_image_url, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
        jdbcTemplate.update(query, request.getNickname(), request.getEmail(), request.getPassword(),
                request.getProfileImageUrl());
    }

    /**
     * TYPE : Retrieve => Select User by Id
     * ? Why : 사용자의 정보를 조회하기 위해 사용
     * IMP : Optional ( Retrieve Method -> Nullable )
     * 
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ? AND deleted_at IS NULL";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * TYPE : Retrieve => Select User by Email
     * ? Why : 로그인 시 사용자의 존재 여부를 확인하기 위해 사용
     * IMP : Optional ( Retrieve Method -> Nullable )
     * 
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ? AND deleted_at IS NULL";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // TYPE : Update => Update User by Id
    public void update(Long id, UserUpdateRequest request) {
        String query = "UPDATE users SET nickname = ?, profile_image_url = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, request.getNickname(), request.getProfileImageUrl(), id);
    }

    // TYPE : Update => Update User Password by Id
    public void updatePassword(Long id, String password) {
        String query = "UPDATE users SET password = ?, updated_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, password, id);
    }

    // TYPE : Delete => Delete User by Id
    public void delete(Long id) {
        String query = "UPDATE users SET deleted_at = NOW() WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
