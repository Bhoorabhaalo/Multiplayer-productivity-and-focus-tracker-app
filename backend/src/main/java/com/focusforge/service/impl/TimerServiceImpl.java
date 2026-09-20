package com.focusforge.service.impl;

import com.focusforge.dto.response.RoomResponse;
import com.focusforge.entity.*;
import com.focusforge.exception.InvalidRoomCodeException;
import com.focusforge.exception.UnauthorizedActionException;
import com.focusforge.mapper.EntityMapper;
import com.focusforge.repository.*;
import com.focusforge.service.AchievementService;
import com.focusforge.service.RoomService;
import com.focusforge.service.TimerService;
import com.focusforge.websocket.RoomEventPublisher;
import com.focusforge.websocket.WsEventType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimerServiceImpl implements TimerService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TimerServiceImpl.class);

    public TimerServiceImpl(RoomRepository roomRepository, RoomMemberRepository roomMemberRepository, UserRepository userRepository, StreakRepository streakRepository, DailyFocusStatRepository dailyFocusStatRepository, FocusSessionRepository focusSessionRepository, AchievementService achievementService, RoomService roomService, RoomEventPublisher eventPublisher) {
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.userRepository = userRepository;
        this.streakRepository = streakRepository;
        this.dailyFocusStatRepository = dailyFocusStatRepository;
        this.focusSessionRepository = focusSessionRepository;
        this.achievementService = achievementService;
        this.roomService = roomService;
        this.eventPublisher = eventPublisher;
    }



    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final UserRepository userRepository;
    private final StreakRepository streakRepository;
    private final DailyFocusStatRepository dailyFocusStatRepository;
    private final FocusSessionRepository focusSessionRepository;
    private final AchievementService achievementService;
    private final RoomService roomService;
    private final RoomEventPublisher eventPublisher;

    @Override
    @Transactional
    public RoomResponse startTimer(Long userId, String code) {
        Room room = getRoomAndVerifyHost(userId, code);

        LocalDateTime now = LocalDateTime.now();
        room.setPhase(Room.Phase.FOCUS);
        if (room.getCurrentCycle() == 0) {
            room.setCurrentCycle(1);
        }
        room.setPhaseStartedAt(now);
        room.setPhaseEndsAt(now.plusMinutes(room.getFocusMinutes()));
        room.setPaused(false);
        room.setRemainingSecondsWhenPaused(null);
        room = roomRepository.save(room);

        broadcastTimerState(room, "START");
        return roomService.getRoomByCode(code, userId);
    }

    @Override
    @Transactional
    public RoomResponse pauseTimer(Long userId, String code) {
        Room room = getRoomAndVerifyHost(userId, code);

        if (room.getPhase() != Room.Phase.FOCUS && room.getPhase() != Room.Phase.BREAK) {
            return roomService.getRoomByCode(code, userId);
        }

        LocalDateTime now = LocalDateTime.now();
        if (!Boolean.TRUE.equals(room.getPaused()) && room.getPhaseEndsAt() != null) {
            long remaining = Math.max(0, Duration.between(now, room.getPhaseEndsAt()).getSeconds());
            room.setPaused(true);
            room.setRemainingSecondsWhenPaused((int) remaining);
            room = roomRepository.save(room);
            broadcastTimerState(room, "PAUSE");
        }

        return roomService.getRoomByCode(code, userId);
    }

    @Override
    @Transactional
    public RoomResponse resumeTimer(Long userId, String code) {
        Room room = getRoomAndVerifyHost(userId, code);

        if (Boolean.TRUE.equals(room.getPaused())) {
            LocalDateTime now = LocalDateTime.now();
            int remaining = room.getRemainingSecondsWhenPaused() != null
                    ? room.getRemainingSecondsWhenPaused()
                    : room.getFocusMinutes() * 60;
            room.setPaused(false);
            room.setPhaseEndsAt(now.plusSeconds(remaining));
            room.setRemainingSecondsWhenPaused(null);
            room = roomRepository.save(room);
            broadcastTimerState(room, "RESUME");
        }

        return roomService.getRoomByCode(code, userId);
    }

    @Override
    @Transactional
    public RoomResponse skipTimer(Long userId, String code) {
        Room room = getRoomAndVerifyHost(userId, code);
        transitionRoomPhase(room);
        return roomService.getRoomByCode(code, userId);
    }

    @Override
    @Transactional
    public RoomResponse extendTimer(Long userId, String code, int minutes) {
        Room room = getRoomAndVerifyHost(userId, code);

        if (Boolean.TRUE.equals(room.getPaused())) {
            int currentRemaining = room.getRemainingSecondsWhenPaused() != null ? room.getRemainingSecondsWhenPaused() : 0;
            room.setRemainingSecondsWhenPaused(currentRemaining + (minutes * 60));
        } else if (room.getPhaseEndsAt() != null) {
            room.setPhaseEndsAt(room.getPhaseEndsAt().plusMinutes(minutes));
        }
        room = roomRepository.save(room);

        broadcastTimerState(room, "EXTEND");
        return roomService.getRoomByCode(code, userId);
    }

    @Override
    @Transactional
    public RoomResponse endRoom(Long userId, String code) {
        Room room = getRoomAndVerifyHost(userId, code);
        room.setStatus(Room.Status.ENDED);
        room.setPhase(Room.Phase.COMPLETED);
        room.setPhaseEndsAt(LocalDateTime.now());
        room = roomRepository.save(room);

        broadcastTimerState(room, "END");
        return roomService.getRoomByCode(code, userId);
    }

    @Override
    @Transactional
    public void tickActiveRooms() {
        LocalDateTime now = LocalDateTime.now();
        List<Room> activeRooms = roomRepository.findByStatus(Room.Status.ACTIVE);

        for (Room room : activeRooms) {
            if (Boolean.TRUE.equals(room.getPaused())) {
                continue;
            }
            if (room.getPhase() == Room.Phase.IDLE || room.getPhase() == Room.Phase.COMPLETED) {
                continue;
            }
            if (room.getPhaseEndsAt() != null && !now.isBefore(room.getPhaseEndsAt())) {
                log.info("Room {} timer phase expired, transitioning phase", room.getCode());
                transitionRoomPhase(room);
            }
        }
    }

    private void transitionRoomPhase(Room room) {
        LocalDateTime now = LocalDateTime.now();
        Room.Phase previousPhase = room.getPhase();

        if (previousPhase == Room.Phase.FOCUS) {
            // Completed a focus cycle: record focus time, award XP & KP
            int focusMinutes = room.getFocusMinutes();
            recordCycleCompletion(room, focusMinutes);

            room.setPhase(Room.Phase.BREAK);
            room.setPhaseStartedAt(now);
            room.setPhaseEndsAt(now.plusMinutes(room.getBreakMinutes()));
            room.setPaused(false);
            room.setRemainingSecondsWhenPaused(null);
            roomRepository.save(room);

            broadcastPhaseChanged(room, previousPhase);
            broadcastTimerState(room, "PHASE_CHANGED");
        } else if (previousPhase == Room.Phase.BREAK) {
            int nextCycle = room.getCurrentCycle() + 1;
            if (nextCycle > room.getTargetCycles()) {
                room.setPhase(Room.Phase.COMPLETED);
                room.setPhaseEndsAt(now);
                room.setPaused(false);
                roomRepository.save(room);

                // Full sprint bonus: +50 XP
                awardSprintCompletionBonus(room);

                broadcastPhaseChanged(room, previousPhase);
                broadcastTimerState(room, "COMPLETED");
            } else {
                room.setCurrentCycle(nextCycle);
                room.setPhase(Room.Phase.FOCUS);
                room.setPhaseStartedAt(now);
                room.setPhaseEndsAt(now.plusMinutes(room.getFocusMinutes()));
                room.setPaused(false);
                room.setRemainingSecondsWhenPaused(null);
                roomRepository.save(room);

                broadcastPhaseChanged(room, previousPhase);
                broadcastTimerState(room, "PHASE_CHANGED");
            }
        }
    }

    private void recordCycleCompletion(Room room, int focusMinutes) {
        List<RoomMember> activeMembers = roomMemberRepository.findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(room.getId());
        LocalDate today = LocalDate.now();

        for (RoomMember member : activeMembers) {
            if (member.getState() == RoomMember.MemberState.FOCUS) {
                int addSeconds = focusMinutes * 60;
                member.setFocusSeconds(member.getFocusSeconds() + addSeconds);
                member.setKarmaPoints(member.getKarmaPoints() + (focusMinutes * 3));
                roomMemberRepository.save(member);

                User user = member.getUser();
                int xpEarned = focusMinutes * 2;
                user.setTotalXp(user.getTotalXp() + xpEarned);
                user.setLevel(EntityMapper.calculateLevel(user.getTotalXp()));
                userRepository.save(user);

                // Streak update (day counts if >= 15 min focus)
                streakRepository.findById(user.getId()).ifPresent(streak -> {
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

                // DailyFocusStat
                DailyFocusStat stat = dailyFocusStatRepository.findByUserIdAndStatDate(user.getId(), today)
                        .orElse(DailyFocusStat.builder().user(user).statDate(today).focusSeconds(0).sessionsCompleted(0).build());
                stat.setFocusSeconds(stat.getFocusSeconds() + addSeconds);
                stat.setSessionsCompleted(stat.getSessionsCompleted() + 1);
                dailyFocusStatRepository.save(stat);

                // Session record
                FocusSession session = FocusSession.builder()
                        .user(user)
                        .room(room)
                        .mode(FocusSession.Mode.SQUAD_SPRINT)
                        .startedAt(LocalDateTime.now().minusMinutes(focusMinutes))
                        .endedAt(LocalDateTime.now())
                        .plannedSeconds(addSeconds)
                        .actualFocusSeconds(addSeconds)
                        .cyclesCompleted(1)
                        .distractions(0)
                        .completed(true)
                        .xpEarned(xpEarned)
                        .build();
                focusSessionRepository.save(session);

                achievementService.evaluateAchievements(user.getId());

                eventPublisher.publishToRoom(room.getCode(), WsEventType.MEMBER_STATE_UPDATED,
                        EntityMapper.toRoomMemberResponse(member, null));
            }
        }
    }

    private void awardSprintCompletionBonus(Room room) {
        List<RoomMember> activeMembers = roomMemberRepository.findByRoomIdAndLeftAtIsNullOrderByJoinedAtAsc(room.getId());
        for (RoomMember member : activeMembers) {
            User user = member.getUser();
            user.setTotalXp(user.getTotalXp() + 50); // +50 XP bonus for completing sprint
            user.setLevel(EntityMapper.calculateLevel(user.getTotalXp()));
            userRepository.save(user);
            achievementService.evaluateAchievements(user.getId());
        }
    }

    private Room getRoomAndVerifyHost(Long userId, String code) {
        Room room = roomRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new InvalidRoomCodeException("Room not found with code: " + code));

        if (!room.getHost().getId().equals(userId)) {
            throw new UnauthorizedActionException("Only the host can control the room timer");
        }
        return room;
    }

    private void broadcastTimerState(Room room, String action) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("action", action);
        payload.put("phase", room.getPhase());
        payload.put("phaseStartedAt", room.getPhaseStartedAt());
        payload.put("phaseEndsAt", room.getPhaseEndsAt());
        payload.put("currentCycle", room.getCurrentCycle());
        payload.put("targetCycles", room.getTargetCycles());
        payload.put("paused", room.getPaused());
        payload.put("remainingSecondsWhenPaused", room.getRemainingSecondsWhenPaused());
        payload.put("serverTime", LocalDateTime.now());

        eventPublisher.publishToRoom(room.getCode(), WsEventType.TIMER_UPDATED, payload);
    }

    private void broadcastPhaseChanged(Room room, Room.Phase previousPhase) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("previousPhase", previousPhase);
        payload.put("phase", room.getPhase());
        payload.put("phaseEndsAt", room.getPhaseEndsAt());
        payload.put("currentCycle", room.getCurrentCycle());
        payload.put("serverTime", LocalDateTime.now());

        eventPublisher.publishToRoom(room.getCode(), WsEventType.PHASE_CHANGED, payload);
    }
}
