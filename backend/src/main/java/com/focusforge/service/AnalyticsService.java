package com.focusforge.service;

import com.focusforge.dto.response.AnalyticsResponse;

public interface AnalyticsService {
    AnalyticsResponse getUserAnalytics(Long userId, String period);
    AnalyticsResponse getRoomAnalytics(String roomCode, String period);
}
