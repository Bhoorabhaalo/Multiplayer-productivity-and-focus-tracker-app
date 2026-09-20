package com.focusforge.service;

import com.focusforge.dto.response.RoomResponse;

public interface TimerService {
    RoomResponse startTimer(Long userId, String code);
    RoomResponse pauseTimer(Long userId, String code);
    RoomResponse resumeTimer(Long userId, String code);
    RoomResponse skipTimer(Long userId, String code);
    RoomResponse extendTimer(Long userId, String code, int minutes);
    RoomResponse endRoom(Long userId, String code);
    void tickActiveRooms();
}
