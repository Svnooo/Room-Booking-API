package com.mayora.meetingroom.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mayora.meetingroom.config.JwtService;
import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.domain.enums.Role;
import com.mayora.meetingroom.dto.response.AuthResponse;
import com.mayora.meetingroom.exception.BadRequestException;
import com.mayora.meetingroom.exception.NotFoundException;
import com.mayora.meetingroom.repository.UserRepository;
import com.mayora.meetingroom.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(String name, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException("Email already used");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new AuthResponse(
                jwtService.generateToken(user.getEmail()),
                user.getEmail(),
                user.getRole().name());
    }

    @Override
    public AuthResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new BadRequestException("User is inactive");
        }

        return new AuthResponse(
                jwtService.generateToken(user.getEmail()),
                user.getEmail(),
                user.getRole().name());
    }
}
