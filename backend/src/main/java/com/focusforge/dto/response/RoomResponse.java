package com.focusforge.dto.response;

import com.focusforge.entity.Room;

import java.time.LocalDateTime;
import java.util.List;

public class RoomResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Long hostId;
    private String hostDisplayName;
    private PodResponse pod;
    private Integer focusMinutes;
    private Integer breakMinutes;
    private Integer targetCycles;
    private Integer currentCycle;
    private Room.Phase phase;
    private LocalDateTime phaseStartedAt;
    private LocalDateTime phaseEndsAt;
    private LocalDateTime serverTime;
    private Long remainingSeconds;
    private Integer maxMembers;
    private Room.Status status;
    private Boolean paused;
    private Integer remainingSecondsWhenPaused;
    private Integer onlineCount;
    private List<RoomMemberResponse> members;
    private SprintTargetResponse sprintTarget;
    private List<ChecklistItemResponse> checklist;

    public RoomResponse() {}

    public RoomResponse(Long id, String code, String name, String description, Long hostId, String hostDisplayName, PodResponse pod, Integer focusMinutes, Integer breakMinutes, Integer targetCycles, Integer currentCycle, Room.Phase phase, LocalDateTime phaseStartedAt, LocalDateTime phaseEndsAt, LocalDateTime serverTime, Long remainingSeconds, Integer maxMembers, Room.Status status, Boolean paused, Integer remainingSecondsWhenPaused, Integer onlineCount, List<RoomMemberResponse> members, SprintTargetResponse sprintTarget, List<ChecklistItemResponse> checklist) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.hostId = hostId;
        this.hostDisplayName = hostDisplayName;
        this.pod = pod;
        this.focusMinutes = focusMinutes;
        this.breakMinutes = breakMinutes;
        this.targetCycles = targetCycles;
        this.currentCycle = currentCycle;
        this.phase = phase;
        this.phaseStartedAt = phaseStartedAt;
        this.phaseEndsAt = phaseEndsAt;
        this.serverTime = serverTime;
        this.remainingSeconds = remainingSeconds;
        this.maxMembers = maxMembers;
        this.status = status;
        this.paused = paused;
        this.remainingSecondsWhenPaused = remainingSecondsWhenPaused;
        this.onlineCount = onlineCount;
        this.members = members;
        this.sprintTarget = sprintTarget;
        this.checklist = checklist;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getHostId() {
        return this.hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getHostDisplayName() {
        return this.hostDisplayName;
    }

    public void setHostDisplayName(String hostDisplayName) {
        this.hostDisplayName = hostDisplayName;
    }

    public PodResponse getPod() {
        return this.pod;
    }

    public void setPod(PodResponse pod) {
        this.pod = pod;
    }

    public Integer getFocusMinutes() {
        return this.focusMinutes;
    }

    public void setFocusMinutes(Integer focusMinutes) {
        this.focusMinutes = focusMinutes;
    }

    public Integer getBreakMinutes() {
        return this.breakMinutes;
    }

    public void setBreakMinutes(Integer breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    public Integer getTargetCycles() {
        return this.targetCycles;
    }

    public void setTargetCycles(Integer targetCycles) {
        this.targetCycles = targetCycles;
    }

    public Integer getCurrentCycle() {
        return this.currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
    }

    public Room.Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Room.Phase phase) {
        this.phase = phase;
    }

    public LocalDateTime getPhaseStartedAt() {
        return this.phaseStartedAt;
    }

    public void setPhaseStartedAt(LocalDateTime phaseStartedAt) {
        this.phaseStartedAt = phaseStartedAt;
    }

    public LocalDateTime getPhaseEndsAt() {
        return this.phaseEndsAt;
    }

    public void setPhaseEndsAt(LocalDateTime phaseEndsAt) {
        this.phaseEndsAt = phaseEndsAt;
    }

    public LocalDateTime getServerTime() {
        return this.serverTime;
    }

    public void setServerTime(LocalDateTime serverTime) {
        this.serverTime = serverTime;
    }

    public Long getRemainingSeconds() {
        return this.remainingSeconds;
    }

    public void setRemainingSeconds(Long remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public Integer getMaxMembers() {
        return this.maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public Room.Status getStatus() {
        return this.status;
    }

    public void setStatus(Room.Status status) {
        this.status = status;
    }

    public Boolean getPaused() {
        return this.paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Integer getRemainingSecondsWhenPaused() {
        return this.remainingSecondsWhenPaused;
    }

    public void setRemainingSecondsWhenPaused(Integer remainingSecondsWhenPaused) {
        this.remainingSecondsWhenPaused = remainingSecondsWhenPaused;
    }

    public Integer getOnlineCount() {
        return this.onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public List<RoomMemberResponse> getMembers() {
        return this.members;
    }

    public void setMembers(List<RoomMemberResponse> members) {
        this.members = members;
    }

    public SprintTargetResponse getSprintTarget() {
        return this.sprintTarget;
    }

    public void setSprintTarget(SprintTargetResponse sprintTarget) {
        this.sprintTarget = sprintTarget;
    }

    public List<ChecklistItemResponse> getChecklist() {
        return this.checklist;
    }

    public void setChecklist(List<ChecklistItemResponse> checklist) {
        this.checklist = checklist;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String name;
        private String description;
        private Long hostId;
        private String hostDisplayName;
        private PodResponse pod;
        private Integer focusMinutes;
        private Integer breakMinutes;
        private Integer targetCycles;
        private Integer currentCycle;
        private Room.Phase phase;
        private LocalDateTime phaseStartedAt;
        private LocalDateTime phaseEndsAt;
        private LocalDateTime serverTime;
        private Long remainingSeconds;
        private Integer maxMembers;
        private Room.Status status;
        private Boolean paused;
        private Integer remainingSecondsWhenPaused;
        private Integer onlineCount;
        private List<RoomMemberResponse> members;
        private SprintTargetResponse sprintTarget;
        private List<ChecklistItemResponse> checklist;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder hostId(Long hostId) {
            this.hostId = hostId;
            return this;
        }

        public Builder hostDisplayName(String hostDisplayName) {
            this.hostDisplayName = hostDisplayName;
            return this;
        }

        public Builder pod(PodResponse pod) {
            this.pod = pod;
            return this;
        }

        public Builder focusMinutes(Integer focusMinutes) {
            this.focusMinutes = focusMinutes;
            return this;
        }

        public Builder breakMinutes(Integer breakMinutes) {
            this.breakMinutes = breakMinutes;
            return this;
        }

        public Builder targetCycles(Integer targetCycles) {
            this.targetCycles = targetCycles;
            return this;
        }

        public Builder currentCycle(Integer currentCycle) {
            this.currentCycle = currentCycle;
            return this;
        }

        public Builder phase(Room.Phase phase) {
            this.phase = phase;
            return this;
        }

        public Builder phaseStartedAt(LocalDateTime phaseStartedAt) {
            this.phaseStartedAt = phaseStartedAt;
            return this;
        }

        public Builder phaseEndsAt(LocalDateTime phaseEndsAt) {
            this.phaseEndsAt = phaseEndsAt;
            return this;
        }

        public Builder serverTime(LocalDateTime serverTime) {
            this.serverTime = serverTime;
            return this;
        }

        public Builder remainingSeconds(Long remainingSeconds) {
            this.remainingSeconds = remainingSeconds;
            return this;
        }

        public Builder maxMembers(Integer maxMembers) {
            this.maxMembers = maxMembers;
            return this;
        }

        public Builder status(Room.Status status) {
            this.status = status;
            return this;
        }

        public Builder paused(Boolean paused) {
            this.paused = paused;
            return this;
        }

        public Builder remainingSecondsWhenPaused(Integer remainingSecondsWhenPaused) {
            this.remainingSecondsWhenPaused = remainingSecondsWhenPaused;
            return this;
        }

        public Builder onlineCount(Integer onlineCount) {
            this.onlineCount = onlineCount;
            return this;
        }

        public Builder members(List<RoomMemberResponse> members) {
            this.members = members;
            return this;
        }

        public Builder sprintTarget(SprintTargetResponse sprintTarget) {
            this.sprintTarget = sprintTarget;
            return this;
        }

        public Builder checklist(List<ChecklistItemResponse> checklist) {
            this.checklist = checklist;
            return this;
        }

        public RoomResponse build() {
            RoomResponse instance = new RoomResponse();
            instance.id = this.id;
            instance.code = this.code;
            instance.name = this.name;
            instance.description = this.description;
            instance.hostId = this.hostId;
            instance.hostDisplayName = this.hostDisplayName;
            instance.pod = this.pod;
            instance.focusMinutes = this.focusMinutes;
            instance.breakMinutes = this.breakMinutes;
            instance.targetCycles = this.targetCycles;
            instance.currentCycle = this.currentCycle;
            instance.phase = this.phase;
            instance.phaseStartedAt = this.phaseStartedAt;
            instance.phaseEndsAt = this.phaseEndsAt;
            instance.serverTime = this.serverTime;
            instance.remainingSeconds = this.remainingSeconds;
            instance.maxMembers = this.maxMembers;
            instance.status = this.status;
            instance.paused = this.paused;
            instance.remainingSecondsWhenPaused = this.remainingSecondsWhenPaused;
            instance.onlineCount = this.onlineCount;
            instance.members = this.members;
            instance.sprintTarget = this.sprintTarget;
            instance.checklist = this.checklist;
            return instance;
        }
    }
}
