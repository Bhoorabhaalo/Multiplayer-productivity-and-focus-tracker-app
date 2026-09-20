package com.focusforge.controller;

import com.focusforge.dto.response.DashboardResponse;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Home dashboard aggregated statistics and activity")
public class DashboardController {
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }



    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Get aggregated dashboard homepage data")
    public ResponseEntity<DashboardResponse> getDashboard(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(dashboardService.getDashboardData(principal.getId()));
    }
}
