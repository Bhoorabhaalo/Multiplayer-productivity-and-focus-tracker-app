package com.focusforge.service.impl;

import com.focusforge.dto.request.UpdatePreferencesRequest;
import com.focusforge.dto.request.UpdateProfileRequest;
import com.focusforge.dto.response.PodResponse;
import com.focusforge.dto.response.UserProfileResponse;
import com.focusforge.entity.*;
import com.focusforge.exception.DuplicateResourceException;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, StreakRepository streakRepository, PodRepository podRepository, UserPodRepository userPodRepository) {
        this.userRepository = userRepository;
        this.streakRepository = streakRepository;
        this.podRepository = podRepository;
        this.userPodRepository = userPodRepository;
    }



    private final UserRepository userRepository;
    private final StreakRepository streakRepository;
    private final PodRepository podRepository;
    private final UserPodRepository userPodRepository;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long userId) {
        User user = findUser(userId);
        Streak streak = streakRepository.findById(userId).orElse(null);
        List<UserPod> pods = userPodRepository.findByUserIdOrderBySubscribedAtDesc(userId);
        return EntityMapper.toUserProfileResponse(user, streak, pods);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = findUser(userId);

        if (request.getDisplayName() != null) user.setDisplayName(request.getDisplayName().trim());
        if (request.getUniversity() != null) user.setUniversity(request.getUniversity().trim());
        if (request.getMajor() != null) user.setMajor(request.getMajor().trim());
        if (request.getFocusStatement() != null) user.setFocusStatement(request.getFocusStatement().trim());
        if (request.getSprintStatus() != null) user.setSprintStatus(request.getSprintStatus().trim());

        user = userRepository.save(user);

        Streak streak = streakRepository.findById(userId).orElse(null);
        List<UserPod> pods = userPodRepository.findByUserIdOrderBySubscribedAtDesc(userId);
        return EntityMapper.toUserProfileResponse(user, streak, pods);
    }

    @Override
    @Transactional
    public UserProfileResponse updatePreferences(Long userId, UpdatePreferencesRequest request) {
        User user = findUser(userId);

        if (request.getInterfaceDensity() != null) {
            user.setInterfaceDensity(request.getInterfaceDensity());
        }
        if (request.getSoundEffectsEnabled() != null) {
            user.setSoundEffectsEnabled(request.getSoundEffectsEnabled());
        }
        if (request.getCompactSidebar() != null) {
            user.setCompactSidebar(request.getCompactSidebar());
        }

        user = userRepository.save(user);

        Streak streak = streakRepository.findById(userId).orElse(null);
        List<UserPod> pods = userPodRepository.findByUserIdOrderBySubscribedAtDesc(userId);
        return EntityMapper.toUserProfileResponse(user, streak, pods);
    }

    @Override
    @Transactional
    public String uploadAvatar(Long userId, MultipartFile file) {
        User user = findUser(userId);

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "avatar.png");
            String extension = "";
            int i = originalFilename.lastIndexOf('.');
            if (i >= 0) {
                extension = originalFilename.substring(i);
            }

            String filename = "avatar_" + userId + "_" + UUID.randomUUID() + extension;
            Path targetLocation = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String avatarUrl = "/uploads/" + filename;
            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);

            return avatarUrl;
        } catch (IOException e) {
            log.error("Could not store avatar file", e);
            throw new RuntimeException("Could not store avatar file", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PodResponse> getUserPods(Long userId) {
        return userPodRepository.findByUserIdOrderBySubscribedAtDesc(userId).stream()
                .map(up -> EntityMapper.toPodResponse(up.getPod()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PodResponse addPodSubscription(Long userId, String podCode) {
        User user = findUser(userId);
        Pod pod = podRepository.findByCode(podCode.trim().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Pod not found with code: " + podCode));

        if (userPodRepository.findByUserIdAndPodId(userId, pod.getId()).isPresent()) {
            throw new DuplicateResourceException("User is already enrolled in pod: " + pod.getCode());
        }

        UserPod userPod = UserPod.builder()
                .user(user)
                .pod(pod)
                .build();
        userPodRepository.save(userPod);

        return EntityMapper.toPodResponse(pod);
    }

    @Override
    @Transactional
    public void removePodSubscription(Long userId, Long podId) {
        userPodRepository.deleteByUserIdAndPodId(userId, podId);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
