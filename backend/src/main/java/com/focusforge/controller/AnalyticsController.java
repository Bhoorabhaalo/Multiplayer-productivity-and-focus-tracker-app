package com.focusforge.controller;

import com.focusforge.dto.response.AnalyticsResponse;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@Tag(name = "Analytics", description = "Personal and study squad focus activity, weekly cadence, and insights")
public class AnalyticsController {
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }



    private final AnalyticsService analyticsService;

    @GetMapping("/me")
    @Operation(summary = "Get user focus analytics (THIS_WEEK or LAST_WEEK)")
    public ResponseEntity<AnalyticsResponse> getUserAnalytics(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "THIS_WEEK") String period) {
        return ResponseEntity.ok(analyticsService.getUserAnalytics(principal.getId(), period));
    }

    @GetMapping("/room/{code}")
    @Operation(summary = "Get squad focus analytics for a room (THIS_WEEK or LAST_WEEK)")
    public ResponseEntity<AnalyticsResponse> getRoomAnalytics(
            @PathVariable String code,
            @RequestParam(defaultValue = "THIS_WEEK") String period) {
        return ResponseEntity.ok(analyticsService.getRoomAnalytics(code, period));
    }
}
