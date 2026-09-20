package com.focusforge.dto.request;

import com.focusforge.entity.FocusSession;
import jakarta.validation.constraints.NotNull;

public class StartSessionRequest {

    private Long roomId;
    private Long taskId;
    private FocusSession.Mode mode = FocusSession.Mode.SQUAD_SPRINT;

    @NotNull(message = "Planned seconds is required")
    private Integer plannedSeconds = 1500;

    public StartSessionRequest() {}

    public StartSessionRequest(Long roomId, Long taskId, FocusSession.Mode mode, Integer plannedSeconds) {
        this.roomId = roomId;
        this.taskId = taskId;
        this.mode = mode;
        this.plannedSeconds = plannedSeconds;
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public FocusSession.Mode getMode() {
        return this.mode;
    }

    public void setMode(FocusSession.Mode mode) {
        this.mode = mode;
    }

    public Integer getPlannedSeconds() {
        return this.plannedSeconds;
    }

    public void setPlannedSeconds(Integer plannedSeconds) {
        this.plannedSeconds = plannedSeconds;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long roomId;
        private Long taskId;
        private FocusSession.Mode mode = FocusSession.Mode.SQUAD_SPRINT;
        private Integer plannedSeconds = 1500;

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder taskId(Long taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder mode(FocusSession.Mode mode) {
            this.mode = mode;
            return this;
        }

        public Builder plannedSeconds(Integer plannedSeconds) {
            this.plannedSeconds = plannedSeconds;
            return this;
        }

        public StartSessionRequest build() {
            StartSessionRequest instance = new StartSessionRequest();
            instance.roomId = this.roomId;
            instance.taskId = this.taskId;
            instance.mode = this.mode;
            instance.plannedSeconds = this.plannedSeconds;
            return instance;
        }
    }
}
