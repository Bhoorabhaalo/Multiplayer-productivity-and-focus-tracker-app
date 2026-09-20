package com.focusforge.service.impl;

import com.focusforge.dto.response.AchievementResponse;
import com.focusforge.dto.response.AchievementSummaryResponse;
import com.focusforge.entity.*;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.service.AchievementService;
import com.focusforge.websocket.RoomEventPublisher;
import com.focusforge.websocket.WsEventType;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AchievementServiceImpl implements AchievementService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AchievementServiceImpl.class);

    public AchievementServiceImpl(AchievementRepository achievementRepository, UserAchievementRepository userAchievementRepository, UserRepository userRepository, StreakRepository streakRepository, FocusSessionRepository focusSessionRepository, RoomMemberRepository roomMemberRepository, RoomEventPublisher eventPublisher) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.userRepository = userRepository;
        this.streakRepository = streakRepository;
        this.focusSessionRepository = focusSessionRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.eventPublisher = eventPublisher;
    }



    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final StreakRepository streakRepository;
    private final FocusSessionRepository focusSessionRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final RoomEventPublisher eventPublisher;

    @Override
    @Transactional
    public void evaluateAchievements(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        List<Achievement> allAchievements = achievementRepository.findAll();
        Map<Long, UserAchievement> userAchievementsMap = userAchievementRepository.findByUserId(userId).stream()
                .collect(Collectors.toMap(ua -> ua.getAchievement().getId(), ua -> ua));

        Streak streak = streakRepository.findById(userId).orElse(null);
        int currentStreak = streak != null ? streak.getCurrentStreak() : 0;

        List<FocusSession> sessions = focusSessionRepository.findByUserIdOrderByStartedAtDesc(userId, PageRequest.of(0, 500)).getContent();

        int totalFocusSeconds = sessions.stream().mapToInt(FocusSession::getActualFocusSeconds).sum();
        int totalFocusHours = totalFocusSeconds / 3600;

        int longestSessionMinutes = sessions.stream()
                .mapToInt(s -> s.getActualFocusSeconds() / 60)
                .max().orElse(0);

        long midnightSessions = sessions.stream()
                .filter(s -> s.getStartedAt() != null && (s.getStartedAt().getHour() >= 23 || s.getStartedAt().getHour() < 4))
                .count();

        long roomsHosted = roomMemberRepository.findByUserIdAndLeftAtIsNull(userId).stream()
                .filter(rm -> rm.getRole() == RoomMember.MemberRole.HOST)
                .count();

        boolean userModified = false;

        for (Achievement ach : allAchievements) {
            UserAchievement ua = userAchievementsMap.computeIfAbsent(ach.getId(), k -> {
                UserAchievement newUa = UserAchievement.builder()
                        .user(user)
                        .achievement(ach)
                        .progressValue(0)
                        .build();
                return userAchievementRepository.save(newUa);
            });

            if (ua.isUnlocked()) {
                continue; // Already unlocked
            }

            int computedProgress = switch (ach.getMetric()) {
                case TOTAL_FOCUS_HOURS -> totalFocusHours;
                case STREAK_DAYS -> currentStreak;
                case ROOMS_HOSTED -> (int) roomsHosted;
                case MIDNIGHT_SESSIONS -> (int) midnightSessions;
                case LONGEST_SESSION_MINUTES -> longestSessionMinutes;
                case SESSIONS_COMPLETED -> sessions.size();
            };

            ua.setProgressValue(Math.max(ua.getProgressValue(), computedProgress));

            if (ua.getProgressValue() >= ach.getTargetValue()) {
                ua.setUnlockedAt(LocalDateTime.now());
                user.setTotalXp(user.getTotalXp() + ach.getXpReward());
                user.setLevel(EntityMapper.calculateLevel(user.getTotalXp()));
                userModified = true;

                userAchievementRepository.save(ua);
                log.info("User {} unlocked achievement: {}", user.getUsername(), ach.getName());

                eventPublisher.publishToUser(user.getEmail(), WsEventType.ACHIEVEMENT_UNLOCKED,
                        EntityMapper.toAchievementResponse(ach, ua));
            } else {
                userAchievementRepository.save(ua);
            }
        }

        if (userModified) {
            userRepository.save(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AchievementResponse> getUserAchievements(Long userId) {
        List<Achievement> allAchievements = achievementRepository.findAll();
        Map<Long, UserAchievement> userMap = userAchievementRepository.findByUserId(userId).stream()
                .collect(Collectors.toMap(ua -> ua.getAchievement().getId(), ua -> ua));

        List<AchievementResponse> responses = new ArrayList<>();
        for (Achievement ach : allAchievements) {
            UserAchievement ua = userMap.get(ach.getId());
            responses.add(EntityMapper.toAchievementResponse(ach, ua));
        }
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public AchievementSummaryResponse getAchievementSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        long completedCount = userAchievementRepository.countByUserIdAndUnlockedAtIsNotNull(userId);
        long totalCount = achievementRepository.count();

        int level = EntityMapper.calculateLevel(user.getTotalXp());
        int currentLevelFloorXp = (level - 1) * (level - 1) * 100;
        int nextLevelXp = level * level * 100;
        int diff = nextLevelXp - currentLevelFloorXp;
        int progressWithinLevel = Math.max(0, user.getTotalXp() - currentLevelFloorXp);
        int progressPercent = diff > 0 ? Math.min(100, (int) Math.round((progressWithinLevel * 100.0) / diff)) : 0;

        return AchievementSummaryResponse.builder()
                .completedCount((int) completedCount)
                .totalCount((int) totalCount)
                .level(level)
                .levelLabel(EntityMapper.getLevelLabel(level))
                .xpEarned(user.getTotalXp())
                .progressToNextLevel(progressPercent)
                .tierLabel("Sprint Season Tier II")
                .build();
    }
}
