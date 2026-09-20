package com.focusforge.controller;

import com.focusforge.dto.request.CompleteSessionRequest;
import com.focusforge.dto.request.StartSessionRequest;
import com.focusforge.dto.request.UpdateSessionStateRequest;
import com.focusforge.dto.response.SessionResponse;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.FocusSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@Tag(name = "Sessions", description = "Focus session tracking, live state updates, and session history")
public class FocusSessionController {
    public FocusSessionController(FocusSessionService sessionService) {
        this.sessionService = sessionService;
    }



    private final FocusSessionService sessionService;

    @PostMapping("/start")
    @Operation(summary = "Start a new focus session (Squad Sprint or Deep Focus)")
    public ResponseEntity<SessionResponse> startSession(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody StartSessionRequest request) {
        return new ResponseEntity<>(sessionService.startSession(principal.getId(), request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/state")
    @Operation(summary = "Update session member state (FOCUS, IDLE, BREAK) and activity")
    public ResponseEntity<SessionResponse> updateState(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id,
            @Valid @RequestBody UpdateSessionStateRequest request) {
        return ResponseEntity.ok(sessionService.updateSessionState(principal.getId(), id, request));
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete session, save focus time, award XP, and update streaks")
    public ResponseEntity<SessionResponse> completeSession(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long id,
            @Valid @RequestBody CompleteSessionRequest request) {
        return ResponseEntity.ok(sessionService.completeSession(principal.getId(), id, request));
    }

    @GetMapping("/history")
    @Operation(summary = "Get historical focus sessions")
    public ResponseEntity<List<SessionResponse>> getHistory(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(sessionService.getSessionHistory(principal.getId(), page, size));
    }
}
