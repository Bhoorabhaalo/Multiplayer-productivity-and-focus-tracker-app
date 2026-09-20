package com.focusforge.service;

import com.focusforge.dto.response.AchievementResponse;
import com.focusforge.dto.response.AchievementSummaryResponse;

import java.util.List;

public interface AchievementService {
    void evaluateAchievements(Long userId);
    List<AchievementResponse> getUserAchievements(Long userId);
    AchievementSummaryResponse getAchievementSummary(Long userId);
}
