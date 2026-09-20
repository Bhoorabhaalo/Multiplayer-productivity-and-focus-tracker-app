package com.focusforge.controller;

import com.focusforge.dto.request.*;
import com.focusforge.dto.response.*;
import com.focusforge.security.UserPrincipal;
import com.focusforge.service.RoomService;
import com.focusforge.service.TimerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "Rooms", description = "Focus room collaboration, synchronized timer, sprint targets, checklist, and chat")
public class RoomController {
    public RoomController(RoomService roomService, TimerService timerService) {
        this.roomService = roomService;
        this.timerService = timerService;
    }



    private final RoomService roomService;
    private final TimerService timerService;

    @PostMapping
    @Operation(summary = "Create a new Focus Room with generated 6-character invite code")
    public ResponseEntity<RoomResponse> createRoom(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody CreateRoomRequest request) {
        return new ResponseEntity<>(roomService.createRoom(principal.getId(), request), HttpStatus.CREATED);
    }

    @PostMapping("/join")
    @Operation(summary = "Join an active Focus Room using its 6-character code")
    public ResponseEntity<RoomResponse> joinRoom(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody JoinRoomRequest request) {
        return ResponseEntity.ok(roomService.joinRoom(principal.getId(), request.getCode()));
    }

    @PostMapping("/{code}/leave")
    @Operation(summary = "Leave a Focus Room")
    public ResponseEntity<Void> leaveRoom(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        roomService.leaveRoom(principal.getId(), code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{code}")
    @Operation(summary = "Get full room state (details, timer, members, target, checklist)")
    public ResponseEntity<RoomResponse> getRoom(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(roomService.getRoomByCode(code, principal.getId()));
    }

    @GetMapping("/my-active")
    @Operation(summary = "Get current active room for caller, or 204 if not in any room")
    public ResponseEntity<RoomResponse> getMyActiveRoom(@AuthenticationPrincipal UserPrincipal principal) {
        RoomResponse room = roomService.getMyActiveRoom(principal.getId());
        if (room == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{code}/members")
    @Operation(summary = "Get list of room members and their live focus states")
    public ResponseEntity<List<RoomMemberResponse>> getMembers(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(roomService.getRoomMembers(code, principal.getId()));
    }

    @GetMapping("/{code}/stats")
    @Operation(summary = "Get room session statistics and efficiency metrics")
    public ResponseEntity<RoomStatsResponse> getStats(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(roomService.getRoomStats(code, principal.getId()));
    }

    // Host-only Timer Controls
    @PostMapping("/{code}/timer/start")
    @Operation(summary = "Host only: Start focus timer")
    public ResponseEntity<RoomResponse> startTimer(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(timerService.startTimer(principal.getId(), code));
    }

    @PostMapping("/{code}/timer/pause")
    @Operation(summary = "Host only: Pause timer")
    public ResponseEntity<RoomResponse> pauseTimer(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(timerService.pauseTimer(principal.getId(), code));
    }

    @PostMapping("/{code}/timer/resume")
    @Operation(summary = "Host only: Resume paused timer")
    public ResponseEntity<RoomResponse> resumeTimer(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(timerService.resumeTimer(principal.getId(), code));
    }

    @PostMapping("/{code}/timer/skip")
    @Operation(summary = "Host only: Skip current phase (Focus -> Break or Break -> Focus)")
    public ResponseEntity<RoomResponse> skipTimer(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(timerService.skipTimer(principal.getId(), code));
    }

    @PostMapping("/{code}/timer/extend")
    @Operation(summary = "Host only: Extend timer by minutes (default 5)")
    public ResponseEntity<RoomResponse> extendTimer(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @RequestBody(required = false) TimerActionRequest request) {
        int mins = (request != null && request.getMinutes() != null) ? request.getMinutes() : 5;
        return ResponseEntity.ok(timerService.extendTimer(principal.getId(), code, mins));
    }

    @PostMapping("/{code}/end")
    @Operation(summary = "Host only: End the study room session")
    public ResponseEntity<RoomResponse> endRoom(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code) {
        return ResponseEntity.ok(timerService.endRoom(principal.getId(), code));
    }

    // Sprint Target & Checklist
    @PutMapping("/{code}/sprint-target")
    @Operation(summary = "Update room sprint target and progress")
    public ResponseEntity<SprintTargetResponse> updateSprintTarget(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @Valid @RequestBody UpdateSprintTargetRequest request) {
        return ResponseEntity.ok(roomService.updateSprintTarget(principal.getId(), code, request));
    }

    @PostMapping("/{code}/checklist")
    @Operation(summary = "Add an item to room session checklist")
    public ResponseEntity<List<ChecklistItemResponse>> addChecklistItem(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @Valid @RequestBody CreateChecklistItemRequest request) {
        return ResponseEntity.ok(roomService.addChecklistItem(principal.getId(), code, request));
    }

    @PatchMapping("/{code}/checklist/{itemId}/toggle")
    @Operation(summary = "Toggle a checklist item (awards +20 KP on completion)")
    public ResponseEntity<List<ChecklistItemResponse>> toggleChecklistItem(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(roomService.toggleChecklistItem(principal.getId(), code, itemId));
    }

    @DeleteMapping("/{code}/checklist/{itemId}")
    @Operation(summary = "Delete a checklist item")
    public ResponseEntity<List<ChecklistItemResponse>> deleteChecklistItem(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(roomService.deleteChecklistItem(principal.getId(), code, itemId));
    }

    // Room Chat
    @GetMapping("/{code}/chat")
    @Operation(summary = "Get room chat history")
    public ResponseEntity<List<ChatMessageResponse>> getChat(
            @PathVariable String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(roomService.getChatMessages(code, page, size));
    }

    @PostMapping("/{code}/chat")
    @Operation(summary = "Send a chat message to room members")
    public ResponseEntity<ChatMessageResponse> sendChatMessage(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @Valid @RequestBody SendChatMessageRequest request) {
        return ResponseEntity.ok(roomService.sendChatMessage(principal.getId(), code, request.getContent()));
    }

    // Leaderboard
    @GetMapping("/{code}/leaderboard")
    @Operation(summary = "Get squad leaderboard (THIS_WEEK or ALL_TIME)")
    public ResponseEntity<LeaderboardResponse> getLeaderboard(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String code,
            @RequestParam(defaultValue = "THIS_WEEK") String period) {
        return ResponseEntity.ok(roomService.getLeaderboard(code, period, principal.getId()));
    }
}
