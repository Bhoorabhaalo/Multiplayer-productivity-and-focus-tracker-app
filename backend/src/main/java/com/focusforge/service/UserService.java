package com.focusforge.service;

import com.focusforge.dto.request.UpdatePreferencesRequest;
import com.focusforge.dto.request.UpdateProfileRequest;
import com.focusforge.dto.response.PodResponse;
import com.focusforge.dto.response.UserProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
    UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request);
    UserProfileResponse updatePreferences(Long userId, UpdatePreferencesRequest request);
    String uploadAvatar(Long userId, MultipartFile file);
    List<PodResponse> getUserPods(Long userId);
    PodResponse addPodSubscription(Long userId, String podCode);
    void removePodSubscription(Long userId, Long podId);
}
