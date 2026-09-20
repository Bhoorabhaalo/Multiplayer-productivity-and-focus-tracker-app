package com.focusforge.dto.response;

import com.focusforge.entity.FocusSession;

import java.time.LocalDateTime;

public class SessionResponse {

    private Long id;
    private Long roomId;
    private String roomCode;
    private String roomName;
    private Long taskId;
    private String taskTitle;
    private FocusSession.Mode mode;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer plannedSeconds;
    private Integer actualFocusSeconds;
    private Integer durationMinutes;
    private Integer cyclesCompleted;
    private Integer distractions;
    private Boolean completed;
    private Integer xpEarned;
    private String podCode;
    private String podLabel;
    private String formattedCompletionTime;

    public SessionResponse() {}

    public SessionResponse(Long id, Long roomId, String roomCode, String roomName, Long taskId, String taskTitle, FocusSession.Mode mode, LocalDateTime startedAt, LocalDateTime endedAt, Integer plannedSeconds, Integer actualFocusSeconds, Integer durationMinutes, Integer cyclesCompleted, Integer distractions, Boolean completed, Integer xpEarned, String podCode, String podLabel, String formattedCompletionTime) {
        this.id = id;
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.mode = mode;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.plannedSeconds = plannedSeconds;
        this.actualFocusSeconds = actualFocusSeconds;
        this.durationMinutes = durationMinutes;
        this.cyclesCompleted = cyclesCompleted;
        this.distractions = distractions;
        this.completed = completed;
        this.xpEarned = xpEarned;
        this.podCode = podCode;
        this.podLabel = podLabel;
        this.formattedCompletionTime = formattedCompletionTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public FocusSession.Mode getMode() {
        return this.mode;
    }

    public void setMode(FocusSession.Mode mode) {
        this.mode = mode;
    }

    public LocalDateTime getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return this.endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Integer getPlannedSeconds() {
        return this.plannedSeconds;
    }

    public void setPlannedSeconds(Integer plannedSeconds) {
        this.plannedSeconds = plannedSeconds;
    }

    public Integer getActualFocusSeconds() {
        return this.actualFocusSeconds;
    }

    public void setActualFocusSeconds(Integer actualFocusSeconds) {
        this.actualFocusSeconds = actualFocusSeconds;
    }

    public Integer getDurationMinutes() {
        return this.durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getCyclesCompleted() {
        return this.cyclesCompleted;
    }

    public void setCyclesCompleted(Integer cyclesCompleted) {
        this.cyclesCompleted = cyclesCompleted;
    }

    public Integer getDistractions() {
        return this.distractions;
    }

    public void setDistractions(Integer distractions) {
        this.distractions = distractions;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getXpEarned() {
        return this.xpEarned;
    }

    public void setXpEarned(Integer xpEarned) {
        this.xpEarned = xpEarned;
    }

    public String getPodCode() {
        return this.podCode;
    }

    public void setPodCode(String podCode) {
        this.podCode = podCode;
    }

    public String getPodLabel() {
        return this.podLabel;
    }

    public void setPodLabel(String podLabel) {
        this.podLabel = podLabel;
    }

    public String getFormattedCompletionTime() {
        return this.formattedCompletionTime;
    }

    public void setFormattedCompletionTime(String formattedCompletionTime) {
        this.formattedCompletionTime = formattedCompletionTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long roomId;
        private String roomCode;
        private String roomName;
        private Long taskId;
        private String taskTitle;
        private FocusSession.Mode mode;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Integer plannedSeconds;
        private Integer actualFocusSeconds;
        private Integer durationMinutes;
        private Integer cyclesCompleted;
        private Integer distractions;
        private Boolean completed;
        private Integer xpEarned;
        private String podCode;
        private String podLabel;
        private String formattedCompletionTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder roomCode(String roomCode) {
            this.roomCode = roomCode;
            return this;
        }

        public Builder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder taskId(Long taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder taskTitle(String taskTitle) {
            this.taskTitle = taskTitle;
            return this;
        }

        public Builder mode(FocusSession.Mode mode) {
            this.mode = mode;
            return this;
        }

        public Builder startedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public Builder endedAt(LocalDateTime endedAt) {
            this.endedAt = endedAt;
            return this;
        }

        public Builder plannedSeconds(Integer plannedSeconds) {
            this.plannedSeconds = plannedSeconds;
            return this;
        }

        public Builder actualFocusSeconds(Integer actualFocusSeconds) {
            this.actualFocusSeconds = actualFocusSeconds;
            return this;
        }

        public Builder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public Builder cyclesCompleted(Integer cyclesCompleted) {
            this.cyclesCompleted = cyclesCompleted;
            return this;
        }

        public Builder distractions(Integer distractions) {
            this.distractions = distractions;
            return this;
        }

        public Builder completed(Boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder xpEarned(Integer xpEarned) {
            this.xpEarned = xpEarned;
            return this;
        }

        public Builder podCode(String podCode) {
            this.podCode = podCode;
            return this;
        }

        public Builder podLabel(String podLabel) {
            this.podLabel = podLabel;
            return this;
        }

        public Builder formattedCompletionTime(String formattedCompletionTime) {
            this.formattedCompletionTime = formattedCompletionTime;
            return this;
        }

        public SessionResponse build() {
            SessionResponse instance = new SessionResponse();
            instance.id = this.id;
            instance.roomId = this.roomId;
            instance.roomCode = this.roomCode;
            instance.roomName = this.roomName;
            instance.taskId = this.taskId;
            instance.taskTitle = this.taskTitle;
            instance.mode = this.mode;
            instance.startedAt = this.startedAt;
            instance.endedAt = this.endedAt;
            instance.plannedSeconds = this.plannedSeconds;
            instance.actualFocusSeconds = this.actualFocusSeconds;
            instance.durationMinutes = this.durationMinutes;
            instance.cyclesCompleted = this.cyclesCompleted;
            instance.distractions = this.distractions;
            instance.completed = this.completed;
            instance.xpEarned = this.xpEarned;
            instance.podCode = this.podCode;
            instance.podLabel = this.podLabel;
            instance.formattedCompletionTime = this.formattedCompletionTime;
            return instance;
        }
    }
}
