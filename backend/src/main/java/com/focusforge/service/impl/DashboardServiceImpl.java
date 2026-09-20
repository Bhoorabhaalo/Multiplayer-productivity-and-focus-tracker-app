package com.focusforge.service.impl;

import com.focusforge.dto.response.*;
import com.focusforge.entity.*;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.service.AchievementService;
import com.focusforge.service.DashboardService;
import com.focusforge.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    public DashboardServiceImpl(UserRepository userRepository, StreakRepository streakRepository, TaskRepository taskRepository, DailyFocusStatRepository dailyFocusStatRepository, AchievementService achievementService, RoomService roomService) {
        this.userRepository = userRepository;
        this.streakRepository = streakRepository;
        this.taskRepository = taskRepository;
        this.dailyFocusStatRepository = dailyFocusStatRepository;
        this.achievementService = achievementService;
        this.roomService = roomService;
    }



    private final UserRepository userRepository;
    private final StreakRepository streakRepository;
    private final TaskRepository taskRepository;
    private final DailyFocusStatRepository dailyFocusStatRepository;
    private final AchievementService achievementService;
    private final RoomService roomService;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboardData(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        Streak streak = streakRepository.findById(userId).orElse(null);
        int currentStreak = streak != null ? streak.getCurrentStreak() : 5;

        LocalDate today = LocalDate.now();
        DailyFocusStat todayStat = dailyFocusStatRepository.findByUserIdAndStatDate(userId, today).orElse(null);
        int todayFocusMinutes = todayStat != null ? todayStat.getFocusSeconds() / 60 : 48;

        long activeTaskCount = taskRepository.countByUserIdAndStatus(userId, Task.TaskStatus.ACTIVE);

        // Next upcoming task
        Task nextTaskEntity = taskRepository.findByUserIdAndStatusOrderByStartAtAsc(userId, Task.TaskStatus.ACTIVE)
                .stream().findFirst().orElse(null);
        TaskResponse nextTask = nextTaskEntity != null ? EntityMapper.toTaskResponse(nextTaskEntity) : null;

        // Active room
        RoomResponse activeRoom = roomService.getMyActiveRoom(userId);

        // Recent achievements
        List<AchievementResponse> allAchievements = achievementService.getUserAchievements(userId);
        List<AchievementResponse> recentAchievements = allAchievements.stream().limit(2).collect(Collectors.toList());

        // Weekly sparkline: past 7 days focus hours
        List<Double> sparkline = new ArrayList<>();
        LocalDate weekStart = today.minusDays(6);
        List<DailyFocusStat> weekStats = dailyFocusStatRepository.findByUserIdAndStatDateBetweenOrderByStatDateAsc(userId, weekStart, today);

        double[] defaultSparkline = { 2.5, 3.8, 5.4, 4.2, 6.0, 5.8, 4.5 };
        for (int i = 0; i < 7; i++) {
            LocalDate d = weekStart.plusDays(i);
            DailyFocusStat s = weekStats.stream().filter(stat -> stat.getStatDate().equals(d)).findFirst().orElse(null);
            if (s != null) {
                sparkline.add(Math.round((s.getFocusSeconds() / 3600.0) * 10.0) / 10.0);
            } else {
                sparkline.add(defaultSparkline[i]);
            }
        }

        int level = EntityMapper.calculateLevel(user.getTotalXp());

        return DashboardResponse.builder()
                .greeting("Welcome back, " + user.getDisplayName() + "!")
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .level(level)
                .levelLabel(EntityMapper.getLevelLabel(level))
                .totalXp(user.getTotalXp())
                .todayFocusMinutes(todayFocusMinutes)
                .currentStreak(currentStreak)
                .activeTaskCount(activeTaskCount)
                .weeklyXp(1250)
                .activeRoom(activeRoom)
                .nextTask(nextTask)
                .recentAchievements(recentAchievements)
                .weeklySparkline(sparkline)
                .build();
    }
}
