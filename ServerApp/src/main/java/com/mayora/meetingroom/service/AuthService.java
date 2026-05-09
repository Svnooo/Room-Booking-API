package com.mayora.meetingroom.service;

import com.mayora.meetingroom.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(String name, String email, String password);

    AuthResponse login(String email, String password);
}
