package com.focusforge.service;

import com.focusforge.dto.request.LoginRequest;
import com.focusforge.dto.request.RegisterRequest;
import com.focusforge.dto.response.AuthResponse;
import com.focusforge.entity.Streak;
import com.focusforge.entity.User;
import com.focusforge.exception.DuplicateResourceException;
import com.focusforge.repository.RefreshTokenRepository;
import com.focusforge.repository.StreakRepository;
import com.focusforge.repository.UserPodRepository;
import com.focusforge.repository.UserRepository;
import com.focusforge.security.JwtTokenProvider;
import com.focusforge.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private StreakRepository streakRepository;
    @Mock
    private UserPodRepository userPodRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider tokenProvider;
    private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        // Set fields via reflection or test secret
        org.springframework.test.util.ReflectionTestUtils.setField(tokenProvider, "jwtSecret", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        org.springframework.test.util.ReflectionTestUtils.setField(tokenProvider, "accessTokenExpirationMs", 1800000L);
        org.springframework.test.util.ReflectionTestUtils.setField(tokenProvider, "refreshTokenExpirationMs", 604800000L);
        tokenProvider.init();

        authService = new AuthServiceImpl(
                userRepository,
                streakRepository,
                userPodRepository,
                refreshTokenRepository,
                passwordEncoder,
                tokenProvider
        );

        testUser = User.builder()
                .id(1L)
                .email("test@focusforge.app")
                .username("testuser")
                .displayName("Test User")
                .passwordHash("hashedpassword")
                .level(1)
                .totalXp(0)
                .build();
    }

    @Test
    void register_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@focusforge.app");
        request.setUsername("testuser");
        request.setDisplayName("Test User");
        request.setPassword("Password@123");

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashedpassword");
        when(userRepository.save(any())).thenReturn(testUser);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
        assertEquals("Test User", response.getUser().getDisplayName());
    }

    @Test
    void register_DuplicateEmail_ThrowsException() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@focusforge.app");
        request.setUsername("testuser");
        request.setDisplayName("Test User");
        request.setPassword("Password@123");

        when(userRepository.existsByEmail("test@focusforge.app")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
    }

    @Test
    void login_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@focusforge.app");
        request.setPassword("Password@123");

        when(userRepository.findByEmail("test@focusforge.app")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("Password@123", "hashedpassword")).thenReturn(true);
        when(streakRepository.findById(1L)).thenReturn(Optional.of(Streak.builder().userId(1L).currentStreak(5).build()));
        when(userPodRepository.findByUserIdOrderBySubscribedAtDesc(1L)).thenReturn(Collections.emptyList());

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertEquals(5, response.getUser().getCurrentStreak());
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@focusforge.app");
        request.setPassword("WrongPassword");

        when(userRepository.findByEmail("test@focusforge.app")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("WrongPassword", "hashedpassword")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }
}
