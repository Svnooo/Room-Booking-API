package com.mayora.meetingroom.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayora.meetingroom.dto.request.LoginRequest;
import com.mayora.meetingroom.dto.request.RegisterRequest;
import com.mayora.meetingroom.dto.response.AuthResponse;
import com.mayora.meetingroom.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register( @Valid @RequestBody RegisterRequest request) {
        return authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(
                request.getEmail(),
                request.getPassword());
    }
}
