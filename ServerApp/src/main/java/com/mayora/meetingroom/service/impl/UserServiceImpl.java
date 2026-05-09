package com.mayora.meetingroom.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.domain.enums.Role;
import com.mayora.meetingroom.exception.NotFoundException;
import com.mayora.meetingroom.repository.UserRepository;
import com.mayora.meetingroom.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return findUserOrThrow(id);
    }

    @Override
    public User updateUser(Long id, String name, Role role, Boolean isActive) {

        User user = findUserOrThrow(id);

        user.setName(name);
        user.setRole(role);
        user.setIsActive(isActive);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = findUserOrThrow(id);

        userRepository.delete(user);
    }

    @Override
    public User getProfile(Long userId) {
        return findUserOrThrow(userId);
    }

    @Override
    public User updateProfile(Long userId, String name) {

        User user = findUserOrThrow(userId);

        user.setName(name);

        return userRepository.save(user);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
