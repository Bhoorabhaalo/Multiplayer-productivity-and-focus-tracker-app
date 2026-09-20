package com.focusforge.service;

import com.focusforge.dto.request.CompleteSessionRequest;
import com.focusforge.dto.request.StartSessionRequest;
import com.focusforge.dto.request.UpdateSessionStateRequest;
import com.focusforge.dto.response.SessionResponse;

import java.util.List;

public interface FocusSessionService {
    SessionResponse startSession(Long userId, StartSessionRequest request);
    SessionResponse updateSessionState(Long userId, Long sessionId, UpdateSessionStateRequest request);
    SessionResponse completeSession(Long userId, Long sessionId, CompleteSessionRequest request);
    List<SessionResponse> getSessionHistory(Long userId, int page, int size);
}
