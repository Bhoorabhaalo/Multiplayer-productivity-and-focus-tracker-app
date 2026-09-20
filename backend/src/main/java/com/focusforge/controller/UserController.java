package com.focusforge.controller;

import com.focusforge.dto.request.UpdatePreferencesRequest;
import com.focusforge.dto.request.UpdateProfileRequest;
import com.focusforge.dto.response.PodResponse;
import com.focusforge.dto.response.UserProfileResponse;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User profile, preferences, avatar, and pod subscriptions")
public class UserController {
    public UserController(UserService userService) {
        this.userService = userService;
    }



    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get detailed profile of current user")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.getProfile(principal.getId()));
    }

    @PutMapping("/me")
    @Operation(summary = "Update personal and academic profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(principal.getId(), request));
    }

    @PutMapping("/me/preferences")
    @Operation(summary = "Update interface density, sound effects, and sidebar preference")
    public ResponseEntity<UserProfileResponse> updatePreferences(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UpdatePreferencesRequest request) {
        return ResponseEntity.ok(userService.updatePreferences(principal.getId(), request));
    }

    @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload profile avatar (JPG, PNG, GIF up to 5MB)")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam("file") MultipartFile file) {
        String avatarUrl = userService.uploadAvatar(principal.getId(), file);
        return ResponseEntity.ok(Map.of("avatarUrl", avatarUrl));
    }

    @GetMapping("/me/pods")
    @Operation(summary = "Get user enrolled pods")
    public ResponseEntity<List<PodResponse>> getUserPods(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.getUserPods(principal.getId()));
    }

    @PostMapping("/me/pods")
    @Operation(summary = "Subscribe to a course/subject pod by code")
    public ResponseEntity<PodResponse> addPod(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody Map<String, String> body) {
        String podCode = body.get("podCode");
        return ResponseEntity.ok(userService.addPodSubscription(principal.getId(), podCode));
    }

    @DeleteMapping("/me/pods/{podId}")
    @Operation(summary = "Unsubscribe from a pod")
    public ResponseEntity<Void> removePod(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long podId) {
        userService.removePodSubscription(principal.getId(), podId);
        return ResponseEntity.noContent().build();
    }
}
