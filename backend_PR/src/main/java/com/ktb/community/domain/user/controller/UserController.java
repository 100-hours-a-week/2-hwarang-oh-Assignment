package com.ktb.community.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.domain.user.service.UserService;
import com.ktb.community.domain.user.model.dto.UserResponse;
import com.ktb.community.domain.user.model.dto.UserUpdateRequest;
import com.ktb.community.domain.user.model.dto.UserRegisterRequest;
import com.ktb.community.domain.user.model.dto.UserUpdatePasswordRequest;

/**
 * IMP : User Controller ( REST API )
 * TYPE : EndPoint : /api/users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // IMP : Register User -> registerUser Service
    @PostMapping()
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입을 성공적으로 완료 ✅");
    }

    // IMP : Get User By Id -> getUserById Service
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        UserResponse userResponse = new UserResponse(userService.getUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    // IMP : Revise User -> reviseUser Service
    @PutMapping("/{id}")
    public ResponseEntity<String> reviseUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {
        userService.reviseUser(id, request);
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정을 성공적으로 완료 ✅");
    }

    // IMP : Change User Password -> changeUserPassword Service
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changeUserPassword(@PathVariable("id") Long id,
            @RequestBody UserUpdatePasswordRequest request) {
        userService.changeUserPassword(id, request);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경을 성공적으로 완료 ✅");
    }

    // IMP : Delete User -> deleteUser Service
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 탈퇴를 성공적으로 완료 ✅");
    }

}
