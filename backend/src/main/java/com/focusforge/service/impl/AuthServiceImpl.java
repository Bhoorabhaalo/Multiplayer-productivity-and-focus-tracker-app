package com.focusforge.service.impl;

import com.focusforge.dto.request.LoginRequest;
import com.focusforge.dto.request.RefreshTokenRequest;
import com.focusforge.dto.request.RegisterRequest;
import com.focusforge.dto.response.AuthResponse;
import com.focusforge.dto.response.UserProfileResponse;
import com.focusforge.entity.*;
import com.focusforge.exception.DuplicateResourceException;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.exception.UnauthorizedActionException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.security.JwtTokenProvider;
import com.focusforge.service.AuthService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    public AuthServiceImpl(UserRepository userRepository, StreakRepository streakRepository, UserPodRepository userPodRepository, RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.streakRepository = streakRepository;
        this.userPodRepository = userPodRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }



    private final UserRepository userRepository;
    private final StreakRepository streakRepository;
    private final UserPodRepository userPodRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use: " + request.getEmail());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username is already taken: " + request.getUsername());
        }

        User user = User.builder()
                .email(request.getEmail().toLowerCase().trim())
                .username(request.getUsername().trim())
                .displayName(request.getDisplayName().trim())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .level(1)
                .totalXp(0)
                .interfaceDensity(User.InterfaceDensity.BALANCED)
                .soundEffectsEnabled(true)
                .compactSidebar(false)
                .role(User.Role.USER)
                .build();

        user = userRepository.save(user);

        Streak streak = Streak.builder()
                .userId(user.getId())
                .user(user)
                .currentStreak(0)
                .longestStreak(0)
                .build();
        streakRepository.save(streak);

        return generateAuthResponse(user, streak, List.of());
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Streak streak = streakRepository.findById(user.getId()).orElse(null);
        List<UserPod> userPods = userPodRepository.findByUserIdOrderBySubscribedAtDesc(user.getId());

        return generateAuthResponse(user, streak, userPods);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(request.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedActionException("Invalid refresh token"));

        if (refreshToken.getRevoked() || refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedActionException("Refresh token is expired or revoked");
        }

        User user = refreshToken.getUser();
        Streak streak = streakRepository.findById(user.getId()).orElse(null);
        List<UserPod> userPods = userPodRepository.findByUserIdOrderBySubscribedAtDesc(user.getId());

        String newAccessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getTokenHash())
                .tokenType("Bearer")
                .expiresIn(1800L)
                .user(EntityMapper.toUserProfileResponse(user, streak, userPods))
                .build();
    }

    @Override
    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Streak streak = streakRepository.findById(userId).orElse(null);
        List<UserPod> userPods = userPodRepository.findByUserIdOrderBySubscribedAtDesc(userId);

        return EntityMapper.toUserProfileResponse(user, streak, userPods);
    }

    private AuthResponse generateAuthResponse(User user, Streak streak, List<UserPod> userPods) {
        String accessToken = tokenProvider.generateAccessToken(user.getId(), user.getEmail());
        String refreshTokenString = tokenProvider.generateRefreshTokenString();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(refreshTokenString)
                .expiryDate(Instant.now().plusMillis(tokenProvider.getRefreshTokenExpirationMs()))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenString)
                .tokenType("Bearer")
                .expiresIn(1800L)
                .user(EntityMapper.toUserProfileResponse(user, streak, userPods))
                .build();
    }
}
