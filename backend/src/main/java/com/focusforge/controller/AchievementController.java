package com.focusforge.controller;

import com.focusforge.dto.response.AchievementResponse;
import com.focusforge.dto.response.AchievementSummaryResponse;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievements")
@Tag(name = "Achievements", description = "Focus milestones, tiers, and progression badges")
public class AchievementController {
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }



    private final AchievementService achievementService;

    @GetMapping("/me")
    @Operation(summary = "Get all achievements with user's current progress and unlock status")
    public ResponseEntity<List<AchievementResponse>> getUserAchievements(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(achievementService.getUserAchievements(principal.getId()));
    }

    @GetMapping("/me/summary")
    @Operation(summary = "Get achievement milestone summary (completed count, season tier, level)")
    public ResponseEntity<AchievementSummaryResponse> getSummary(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(achievementService.getAchievementSummary(principal.getId()));
    }
}
