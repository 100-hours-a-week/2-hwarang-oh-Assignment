package com.ktb.community.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.domain.user.repository.UserRepository;
import com.ktb.community.domain.user.model.dto.UserUpdateRequest;
import com.ktb.community.domain.user.model.dto.UserRegisterRequest;
import com.ktb.community.domain.user.model.dto.UserUpdatePasswordRequest;

import com.ktb.community.global.util.PasswordUtil;
import com.ktb.community.global.exception.ErrorCode;
import com.ktb.community.global.exception.BaseException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // IMP : Register User -> save
    public void registerUser(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(ErrorCode.DUPLICATED_EMAIL);
        }
        request.setPassword(PasswordUtil.encode(request.getPassword()));
        userRepository.save(request);
    }

    // IMP : Get User By Id -> findById
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));
    }

    // IMP : Revise User -> update
    public void reviseUser(Long id, UserUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new BaseException(ErrorCode.NOT_EXIST_USER);

        userRepository.update(id, request);
    }

    // IMP : Change Password -> updatePassword
    public void changeUserPassword(Long id, UserUpdatePasswordRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new BaseException(ErrorCode.NOT_EXIST_USER);

        User user = optionalUser.get();
        if (!PasswordUtil.match(request.getOldPassword(), user.getPassword()))
            throw new BaseException(ErrorCode.INVALID_PASSWORD);

        userRepository.updatePassword(id, PasswordUtil.encode(request.getNewPassword()));
    }

    // IMP : Delete User -> delete
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new BaseException(ErrorCode.NOT_EXIST_USER);
        userRepository.delete(id);
    }
}
