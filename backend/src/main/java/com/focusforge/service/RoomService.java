package com.focusforge.service;

import com.focusforge.dto.request.*;
import com.focusforge.dto.response.*;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(Long userId, CreateRoomRequest request);
    RoomResponse joinRoom(Long userId, String code);
    void leaveRoom(Long userId, String code);
    RoomResponse getRoomByCode(String code, Long currentUserId);
    RoomResponse getMyActiveRoom(Long userId);
    List<RoomMemberResponse> getRoomMembers(String code, Long currentUserId);
    RoomStatsResponse getRoomStats(String code, Long currentUserId);
    SprintTargetResponse updateSprintTarget(Long userId, String code, UpdateSprintTargetRequest request);
    List<ChecklistItemResponse> addChecklistItem(Long userId, String code, CreateChecklistItemRequest request);
    List<ChecklistItemResponse> toggleChecklistItem(Long userId, String code, Long itemId);
    List<ChecklistItemResponse> deleteChecklistItem(Long userId, String code, Long itemId);
    List<ChatMessageResponse> getChatMessages(String code, int page, int size);
    ChatMessageResponse sendChatMessage(Long userId, String code, String content);
    LeaderboardResponse getLeaderboard(String code, String period, Long currentUserId);
}
