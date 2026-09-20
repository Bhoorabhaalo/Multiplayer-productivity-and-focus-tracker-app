package com.focusforge.service.impl;

import com.focusforge.dto.request.CompleteSessionRequest;
import com.focusforge.dto.request.StartSessionRequest;
import com.focusforge.dto.request.UpdateSessionStateRequest;
import com.focusforge.dto.response.SessionResponse;
import com.focusforge.entity.*;
import com.focusforge.exception.ResourceNotFoundException;
import com.focusforge.exception.UnauthorizedActionException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.service.AchievementService;
import com.focusforge.service.FocusSessionService;
import com.focusforge.websocket.RoomEventPublisher;
import com.focusforge.websocket.WsEventType;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FocusSessionServiceImpl implements FocusSessionService {
    public FocusSessionServiceImpl(FocusSessionRepository focusSessionRepository, UserRepository userRepository, RoomRepository roomRepository, TaskRepository taskRepository, RoomMemberRepository roomMemberRepository, StreakRepository streakRepository, DailyFocusStatRepository dailyFocusStatRepository, AchievementService achievementService, RoomEventPublisher eventPublisher) {
        this.focusSessionRepository = focusSessionRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.taskRepository = taskRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.streakRepository = streakRepository;
        this.dailyFocusStatRepository = dailyFocusStatRepository;
        this.achievementService = achievementService;
        this.eventPublisher = eventPublisher;
    }



    private final FocusSessionRepository focusSessionRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final TaskRepository taskRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final StreakRepository streakRepository;
    private final DailyFocusStatRepository dailyFocusStatRepository;
    private final AchievementService achievementService;
    private final RoomEventPublisher eventPublisher;

    @Override
    @Transactional
    public SessionResponse startSession(Long userId, StartSessionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        Room room = null;
        if (request.getRoomId() != null) {
            room = roomRepository.findById(request.getRoomId()).orElse(null);
        }

        Task task = null;
        if (request.getTaskId() != null) {
            task = taskRepository.findById(request.getTaskId()).orElse(null);
        }

        FocusSession session = FocusSession.builder()
                .user(user)
                .room(room)
                .task(task)
                .mode(request.getMode() != null ? request.getMode() : FocusSession.Mode.SQUAD_SPRINT)
                .startedAt(LocalDateTime.now())
                .plannedSeconds(request.getPlannedSeconds() != null ? request.getPlannedSeconds() : 1500)
                .actualFocusSeconds(0)
                .cyclesCompleted(0)
                .distractions(0)
                .completed(false)
                .xpEarned(0)
                .build();

        session = focusSessionRepository.save(session);
        return EntityMapper.toSessionResponse(session);
    }

    @Override
    @Transactional
    public SessionResponse updateSessionState(Long userId, Long sessionId, UpdateSessionStateRequest request) {
        FocusSession session = findUserSession(userId, sessionId);

        if (session.getRoom() != null) {
            roomMemberRepository.findByRoomIdAndUserIdAndLeftAtIsNull(session.getRoom().getId(), userId)
                    .ifPresent(member -> {
                        if (member.getState() == RoomMember.MemberState.FOCUS && request.getState() == RoomMember.MemberState.IDLE) {
                            session.setDistractions(session.getDistractions() + 1);
                        }
                        member.setState(request.getState());
                        if (request.getActivity() != null) {
                            member.setCurrentActivity(request.getActivity().trim());
                        }
                        roomMemberRepository.save(member);

                        eventPublisher.publishToRoom(session.getRoom().getCode(), WsEventType.MEMBER_STATE_UPDATED,
                                EntityMapper.toRoomMemberResponse(member, userId));
                    });
        }

        focusSessionRepository.save(session);
        return EntityMapper.toSessionResponse(session);
    }

    @Override
    @Transactional
    public SessionResponse completeSession(Long userId, Long sessionId, CompleteSessionRequest request) {
        FocusSession session = findUserSession(userId, sessionId);

        LocalDateTime now = LocalDateTime.now();
        session.setEndedAt(now);
        session.setActualFocusSeconds(request.getActualFocusSeconds());
        session.setCyclesCompleted(request.getCyclesCompleted());
        session.setCompleted(true);

        int focusMinutes = request.getActualFocusSeconds() / 60;
        int xpEarned = focusMinutes * 2;

        if (session.getTask() != null && session.getTask().getStatus() == Task.TaskStatus.COMPLETED) {
            xpEarned += 25; // task bonus
        }

        session.setXpEarned(xpEarned);
        focusSessionRepository.save(session);

        User user = session.getUser();
        user.setTotalXp(user.getTotalXp() + xpEarned);
        user.setLevel(EntityMapper.calculateLevel(user.getTotalXp()));
        userRepository.save(user);

        LocalDate today = LocalDate.now();

        // Streak check
        if (focusMinutes >= 15) {
            streakRepository.findById(userId).ifPresent(streak -> {
                if (streak.getLastFocusDate() == null) {
                    streak.setCurrentStreak(1);
                    streak.setLongestStreak(Math.max(1, streak.getLongestStreak()));
                    streak.setLastFocusDate(today);
                } else if (!today.equals(streak.getLastFocusDate())) {
                    if (streak.getLastFocusDate().plusDays(1).equals(today)) {
                        streak.setCurrentStreak(streak.getCurrentStreak() + 1);
                        streak.setLongestStreak(Math.max(streak.getCurrentStreak(), streak.getLongestStreak()));
                    } else {
                        streak.setCurrentStreak(1);
                    }
                    streak.setLastFocusDate(today);
                }
                streakRepository.save(streak);
            });
        }

        // Daily focus stats rollup
        DailyFocusStat stat = dailyFocusStatRepository.findByUserIdAndStatDate(userId, today)
                .orElse(DailyFocusStat.builder().user(user).statDate(today).focusSeconds(0).sessionsCompleted(0).build());
        stat.setFocusSeconds(stat.getFocusSeconds() + request.getActualFocusSeconds());
        stat.setSessionsCompleted(stat.getSessionsCompleted() + 1);
        dailyFocusStatRepository.save(stat);

        achievementService.evaluateAchievements(userId);

        return EntityMapper.toSessionResponse(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponse> getSessionHistory(Long userId, int page, int size) {
        return focusSessionRepository.findByUserIdOrderByStartedAtDesc(userId, PageRequest.of(page, size))
                .getContent().stream()
                .map(EntityMapper::toSessionResponse)
                .collect(Collectors.toList());
    }

    private FocusSession findUserSession(Long userId, Long sessionId) {
        FocusSession session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));
        if (!session.getUser().getId().equals(userId)) {
            throw new UnauthorizedActionException("You do not have permission to modify this session");
        }
        return session;
    }
}
