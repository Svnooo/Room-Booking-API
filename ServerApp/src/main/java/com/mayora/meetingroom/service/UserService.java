package com.mayora.meetingroom.service;

import java.util.List;

import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.domain.enums.Role;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, String name, Role role, Boolean isActive);

    void deleteUser(Long id);

    User getProfile(Long userId);

    User updateProfile(Long userId, String name);
}
