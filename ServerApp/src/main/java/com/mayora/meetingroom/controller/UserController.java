package com.mayora.meetingroom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.domain.enums.Role;
import com.mayora.meetingroom.dto.request.UpdateProfileRequest;
import com.mayora.meetingroom.dto.request.UpdateUserRequest;
import com.mayora.meetingroom.dto.response.UserResponse;
import com.mayora.meetingroom.mapper.UserMapper;
import com.mayora.meetingroom.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // =============================
    // ADMIN: GET ALL USERS
    // =============================
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(
                userMapper.toResponseList(userService.getAllUsers())
        );
    }

    // =============================
    // ADMIN: GET USER BY ID
    // =============================
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.toResponse(userService.getUserById(id))
        );
    }

    // =============================
    // ADMIN: UPDATE USER
    // =============================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        User user = userService.updateUser(
                id,
                request.getName(),
                request.getRole(),
                request.getIsActive()
        );

        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    // =============================
    // ADMIN: DELETE USER
    // =============================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    // =============================
    // USER: GET OWN PROFILE
    // =============================
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                userMapper.toResponse(userService.getProfile(user.getId()))
        );
    }

    // =============================
    // USER: UPDATE PROFILE
    // =============================
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateProfileRequest request) {

        User updated = userService.updateProfile(
                user.getId(),
                request.getName()
        );

        
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }
}
