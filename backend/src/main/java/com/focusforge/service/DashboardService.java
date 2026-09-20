package com.focusforge.service;

import com.focusforge.dto.response.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboardData(Long userId);
}
