package com.focusforge.mapper;

import com.focusforge.dto.response.*;
import com.focusforge.entity.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class EntityMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, hh:mm a");

    private EntityMapper() {}

    public static UserProfileResponse toUserProfileResponse(User user, Streak streak, List<UserPod> userPods) {
        int level = calculateLevel(user.getTotalXp());
        int currentLevelFloorXp = (level - 1) * (level - 1) * 100;
        int nextLevelXp = level * level * 100;
        int diff = nextLevelXp - currentLevelFloorXp;
        int progressWithinLevel = Math.max(0, user.getTotalXp() - currentLevelFloorXp);
        int progressPercent = diff > 0 ? Math.min(100, (int) Math.round((progressWithinLevel * 100.0) / diff)) : 0;
        int xpToNextLevel = Math.max(0, nextLevelXp - user.getTotalXp());

        List<PodResponse> enrolledPods = (userPods != null)
                ? userPods.stream().map(up -> toPodResponse(up.getPod())).collect(Collectors.toList())
                : Collections.emptyList();

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .university(user.getUniversity())
                .major(user.getMajor())
                .focusStatement(user.getFocusStatement())
                .sprintStatus(user.getSprintStatus())
                .level(level)
                .levelLabel(getLevelLabel(level))
                .totalXp(user.getTotalXp())
                .xpToNextLevel(xpToNextLevel)
                .progressPercent(progressPercent)
                .currentStreak(streak != null ? streak.getCurrentStreak() : 0)
                .longestStreak(streak != null ? streak.getLongestStreak() : 0)
                .interfaceDensity(user.getInterfaceDensity())
                .soundEffectsEnabled(user.getSoundEffectsEnabled())
                .compactSidebar(user.getCompactSidebar())
                .role(user.getRole())
                .enrolledPods(enrolledPods)
                .build();
    }

    public static int calculateLevel(int totalXp) {
        return (int) Math.floor(Math.sqrt(Math.max(0, totalXp) / 100.0)) + 1;
    }

    public static String getLevelLabel(int level) {
        if (level >= 20) return "Level " + level + " Grandmaster";
        if (level >= 15) return "Level " + level + " Master";
        if (level >= 10) return "Level " + level + " Scholar";
        if (level >= 5) return "Level " + level + " Practitioner";
        return "Level " + level + " Novice";
    }

    public static PodResponse toPodResponse(Pod pod) {
        if (pod == null) return null;
        return PodResponse.builder()
                .id(pod.getId())
                .code(pod.getCode())
                .label(pod.getLabel())
                .name(pod.getName())
                .build();
    }

    public static TaskResponse toTaskResponse(Task task) {
        if (task == null) return null;
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .note(task.getNote())
                .pod(toPodResponse(task.getPod()))
                .priority(task.getPriority())
                .startAt(task.getStartAt())
                .deadlineAt(task.getDeadlineAt())
                .pomodoroCycles(task.getPomodoroCycles())
                .status(task.getStatus())
                .completedAt(task.getCompletedAt())
                .xpAwarded(task.getXpAwarded())
                .formattedStartTime(task.getStartAt() != null ? task.getStartAt().format(TIME_FORMATTER) : null)
                .formattedDeadline(task.getDeadlineAt() != null ? task.getDeadlineAt().format(TIME_FORMATTER) : null)
                .formattedCompletedTime(task.getCompletedAt() != null ? task.getCompletedAt().format(TIME_FORMATTER) : null)
                .build();
    }

    public static RoomMemberResponse toRoomMemberResponse(RoomMember member, Long currentUserId) {
        if (member == null) return null;
        User u = member.getUser();
        int focusMins = member.getFocusSeconds() / 60;
        int plannedMins = 60; // standard display baseline
        int progress = Math.min(100, (int) Math.round((focusMins * 100.0) / plannedMins));

        return RoomMemberResponse.builder()
                .id(member.getId())
                .userId(u.getId())
                .username(u.getUsername())
                .displayName(u.getDisplayName())
                .avatarUrl(u.getAvatarUrl())
                .role(member.getRole())
                .state(member.getState())
                .currentActivity(member.getCurrentActivity())
                .focusSeconds(member.getFocusSeconds())
                .focusMinutes(focusMins)
                .karmaPoints(member.getKarmaPoints())
                .progressPercent(progress)
                .joinedAt(member.getJoinedAt())
                .isSelf(currentUserId != null && currentUserId.equals(u.getId()))
                .build();
    }

    public static SprintTargetResponse toSprintTargetResponse(SprintTarget target) {
        if (target == null) return null;
        int minsOnTask = 14; // Default initial display or derived from duration
        if (target.getCreatedAt() != null) {
            minsOnTask = Math.max(1, (int) Duration.between(target.getCreatedAt(), LocalDateTime.now()).toMinutes());
        }
        return SprintTargetResponse.builder()
                .id(target.getId())
                .roomId(target.getRoom().getId())
                .title(target.getTitle())
                .description(target.getDescription())
                .stepCurrent(target.getStepCurrent())
                .stepTotal(target.getStepTotal())
                .tag(target.getTag())
                .createdById(target.getCreatedBy().getId())
                .createdByName(target.getCreatedBy().getDisplayName())
                .minutesOnTask(minsOnTask)
                .createdAt(target.getCreatedAt())
                .build();
    }

    public static ChecklistItemResponse toChecklistItemResponse(ChecklistItem item) {
        if (item == null) return null;
        return ChecklistItemResponse.builder()
                .id(item.getId())
                .roomId(item.getRoom().getId())
                .text(item.getText())
                .tag(item.getTag())
                .done(item.getDone())
                .createdById(item.getCreatedBy().getId())
                .createdByName(item.getCreatedBy().getDisplayName())
                .completedById(item.getCompletedBy() != null ? item.getCompletedBy().getId() : null)
                .completedByName(item.getCompletedBy() != null ? item.getCompletedBy().getDisplayName() : null)
                .createdAt(item.getCreatedAt())
                .build();
    }

    public static RoomResponse toRoomResponse(Room room,
                                             List<RoomMemberResponse> members,
                                             SprintTargetResponse sprintTarget,
                                             List<ChecklistItemResponse> checklist) {
        if (room == null) return null;

        LocalDateTime now = LocalDateTime.now();
        long remainingSeconds = 0;
        if (room.getPhaseEndsAt() != null && room.getPhase() != Room.Phase.IDLE && room.getPhase() != Room.Phase.COMPLETED) {
            if (Boolean.TRUE.equals(room.getPaused()) && room.getRemainingSecondsWhenPaused() != null) {
                remainingSeconds = room.getRemainingSecondsWhenPaused();
            } else {
                remainingSeconds = Math.max(0, Duration.between(now, room.getPhaseEndsAt()).getSeconds());
            }
        }

        return RoomResponse.builder()
                .id(room.getId())
                .code(room.getCode())
                .name(room.getName())
                .description(room.getDescription())
                .hostId(room.getHost().getId())
                .hostDisplayName(room.getHost().getDisplayName())
                .pod(toPodResponse(room.getPod()))
                .focusMinutes(room.getFocusMinutes())
                .breakMinutes(room.getBreakMinutes())
                .targetCycles(room.getTargetCycles())
                .currentCycle(room.getCurrentCycle())
                .phase(room.getPhase())
                .phaseStartedAt(room.getPhaseStartedAt())
                .phaseEndsAt(room.getPhaseEndsAt())
                .serverTime(now)
                .remainingSeconds(remainingSeconds)
                .maxMembers(room.getMaxMembers())
                .status(room.getStatus())
                .paused(room.getPaused())
                .remainingSecondsWhenPaused(room.getRemainingSecondsWhenPaused())
                .onlineCount(members != null ? members.size() : 0)
                .members(members)
                .sprintTarget(sprintTarget)
                .checklist(checklist)
                .build();
    }

    public static ChatMessageResponse toChatMessageResponse(ChatMessage msg) {
        if (msg == null) return null;
        User u = msg.getUser();
        return ChatMessageResponse.builder()
                .id(msg.getId())
                .roomId(msg.getRoom().getId())
                .roomCode(msg.getRoom().getCode())
                .userId(u.getId())
                .username(u.getUsername())
                .displayName(u.getDisplayName())
                .avatarUrl(u.getAvatarUrl())
                .content(msg.getContent())
                .sentAt(msg.getSentAt())
                .build();
    }

    public static SessionResponse toSessionResponse(FocusSession session) {
        if (session == null) return null;
        int durationMinutes = session.getActualFocusSeconds() > 0
                ? session.getActualFocusSeconds() / 60
                : session.getPlannedSeconds() / 60;

        String formattedTime = null;
        if (session.getEndedAt() != null) {
            formattedTime = session.getEndedAt().format(DATE_TIME_FORMATTER);
        } else if (session.getStartedAt() != null) {
            formattedTime = session.getStartedAt().format(DATE_TIME_FORMATTER);
        }

        String podCode = null;
        String podLabel = null;
        if (session.getTask() != null && session.getTask().getPod() != null) {
            podCode = session.getTask().getPod().getCode();
            podLabel = session.getTask().getPod().getLabel();
        } else if (session.getRoom() != null && session.getRoom().getPod() != null) {
            podCode = session.getRoom().getPod().getCode();
            podLabel = session.getRoom().getPod().getLabel();
        }

        return SessionResponse.builder()
                .id(session.getId())
                .roomId(session.getRoom() != null ? session.getRoom().getId() : null)
                .roomCode(session.getRoom() != null ? session.getRoom().getCode() : null)
                .roomName(session.getRoom() != null ? session.getRoom().getName() : null)
                .taskId(session.getTask() != null ? session.getTask().getId() : null)
                .taskTitle(session.getTask() != null ? session.getTask().getTitle() : null)
                .mode(session.getMode())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .plannedSeconds(session.getPlannedSeconds())
                .actualFocusSeconds(session.getActualFocusSeconds())
                .durationMinutes(durationMinutes)
                .cyclesCompleted(session.getCyclesCompleted())
                .distractions(session.getDistractions())
                .completed(session.getCompleted())
                .xpEarned(session.getXpEarned())
                .podCode(podCode)
                .podLabel(podLabel)
                .formattedCompletionTime(formattedTime)
                .build();
    }

    public static AchievementResponse toAchievementResponse(Achievement a, UserAchievement ua) {
        int progress = ua != null ? ua.getProgressValue() : 0;
        int target = a.getTargetValue();
        int progressPercent = target > 0 ? Math.min(100, (int) Math.round((progress * 100.0) / target)) : 0;
        boolean unlocked = ua != null && ua.isUnlocked();

        String progressText;
        switch (a.getMetric()) {
            case TOTAL_FOCUS_HOURS -> progressText = progress + " / " + target + " hrs (" + progressPercent + "%)";
            case STREAK_DAYS -> progressText = progress + " / " + target + " days (" + progressPercent + "%)";
            case ROOMS_HOSTED -> progressText = progress + " / " + target + " hosted (" + progressPercent + "%)";
            case MIDNIGHT_SESSIONS -> progressText = progress + " / " + target + " completed";
            case LONGEST_SESSION_MINUTES -> progressText = progress + " / " + target + " min";
            default -> progressText = progress + " / " + target;
        }

        return AchievementResponse.builder()
                .id(a.getId())
                .code(a.getCode())
                .name(a.getName())
                .description(a.getDescription())
                .tier(a.getTier())
                .metric(a.getMetric())
                .targetValue(target)
                .xpReward(a.getXpReward())
                .icon(a.getIcon())
                .progressValue(progress)
                .progressPercent(progressPercent)
                .unlocked(unlocked)
                .unlockedAt(ua != null ? ua.getUnlockedAt() : null)
                .progressText(progressText)
                .build();
    }
}
