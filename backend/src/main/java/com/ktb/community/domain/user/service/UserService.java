package com.ktb.community.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.domain.user.repository.UserRepository;
import com.ktb.community.global.util.PasswordUtil;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // IMP : Register User -> createUser
    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 가입된 이메일입니다. 🚨");
        }
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        userRepository.createUser(user);
    }

    // IMP : Get User By Id -> cindUserById
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // IMP : Revise User -> updateUser
    public void reviseUser(Long id, String nickname, String profileImageUrl) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new IllegalStateException("존재하지 않는 사용자입니다. 🚨");

        User user = optionalUser.get();
        user.setNickname(nickname);
        user.setProfileImageUrl(profileImageUrl);
        userRepository.updateUser(user);
    }

    // IMP : Change Password -> updateUserPassword
    public void changeUserPassword(Long id, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new IllegalStateException("존재하지 않는 사용자입니다. 🚨");

        User user = optionalUser.get();
        if (!PasswordUtil.match(oldPassword, user.getPassword()))
            throw new IllegalStateException("비밀번호가 일치하지 않습니다. 🚨");

        userRepository.updateUserPassword(id, PasswordUtil.encode(newPassword));
    }
}
