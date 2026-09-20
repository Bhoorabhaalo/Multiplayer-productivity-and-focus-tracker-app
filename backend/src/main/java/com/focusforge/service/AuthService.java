package com.focusforge.service;

import com.focusforge.dto.request.LoginRequest;
import com.focusforge.dto.request.RefreshTokenRequest;
import com.focusforge.dto.request.RegisterRequest;
import com.focusforge.dto.response.AuthResponse;
import com.focusforge.dto.response.UserProfileResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    void logout(Long userId);
    UserProfileResponse getCurrentUser(Long userId);
}
