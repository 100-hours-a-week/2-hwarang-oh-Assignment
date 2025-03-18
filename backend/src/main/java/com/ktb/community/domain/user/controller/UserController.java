package com.ktb.community.domain.user.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.user.model.dto.UserChangePasswordRequest;
import com.ktb.community.domain.user.model.dto.UserUpdateRequest;
import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.domain.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // IMP : Register User -> registerUser Service
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 성공적으로 완료 ✅");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생 🚨");
        }
    }

    // IMP : Get User By Id -> getUserById Service
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // IMP : Revise User -> reviseUser Service
    @PutMapping("/{id}")
    public ResponseEntity<String> reviseUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {
        try {
            userService.reviseUser(id, request.getNickname(), request.getProfileImageUrl());
            return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정이 성공적으로 완료 ✅");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found (사용자 없음)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생 🚨");
        }
    }

    // IMP : Change User Password -> changeUserPassword Service
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changeUserPassword(@PathVariable("id") Long id,
            @RequestBody UserChangePasswordRequest request) {
        try {
            userService.changeUserPassword(id, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경이 성공적으로 완료 ✅");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // 401 Unauthorized (비밀번호 불일치)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생 🚨");
        }
    }

}
