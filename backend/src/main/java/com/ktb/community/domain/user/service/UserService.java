package com.ktb.community.domain.user.service;

import org.springframework.stereotype.Service;

import com.ktb.community.domain.user.model.entity.User;
import com.ktb.community.domain.user.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
}
