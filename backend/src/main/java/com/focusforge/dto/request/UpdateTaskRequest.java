package com.focusforge.dto.request;

import com.focusforge.entity.Task;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateTaskRequest {


    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 300, message = "Note cannot exceed 300 characters")
    private String note;

    private Long podId;

    private Task.Priority priority;

    private LocalDateTime startAt;

    private LocalDateTime deadlineAt;

    private Integer pomodoroCycles;

    private Task.TaskStatus status;

    public UpdateTaskRequest() {}

    public UpdateTaskRequest(String title, String note, Long podId, Task.Priority priority, LocalDateTime startAt, LocalDateTime deadlineAt, Integer pomodoroCycles, Task.TaskStatus status) {
        this.title = title;
        this.note = note;
        this.podId = podId;
        this.priority = priority;
        this.startAt = startAt;
        this.deadlineAt = deadlineAt;
        this.pomodoroCycles = pomodoroCycles;
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPodId() {
        return this.podId;
    }

    public void setPodId(Long podId) {
        this.podId = podId;
    }

    public Task.Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Task.Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartAt() {
        return this.startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getDeadlineAt() {
        return this.deadlineAt;
    }

    public void setDeadlineAt(LocalDateTime deadlineAt) {
        this.deadlineAt = deadlineAt;
    }

    public Integer getPomodoroCycles() {
        return this.pomodoroCycles;
    }

    public void setPomodoroCycles(Integer pomodoroCycles) {
        this.pomodoroCycles = pomodoroCycles;
    }

    public Task.TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(Task.TaskStatus status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String note;
        private Long podId;
        private Task.Priority priority;
        private LocalDateTime startAt;
        private LocalDateTime deadlineAt;
        private Integer pomodoroCycles;
        private Task.TaskStatus status;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder podId(Long podId) {
            this.podId = podId;
            return this;
        }

        public Builder priority(Task.Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder startAt(LocalDateTime startAt) {
            this.startAt = startAt;
            return this;
        }

        public Builder deadlineAt(LocalDateTime deadlineAt) {
            this.deadlineAt = deadlineAt;
            return this;
        }

        public Builder pomodoroCycles(Integer pomodoroCycles) {
            this.pomodoroCycles = pomodoroCycles;
            return this;
        }

        public Builder status(Task.TaskStatus status) {
            this.status = status;
            return this;
        }

        public UpdateTaskRequest build() {
            UpdateTaskRequest instance = new UpdateTaskRequest();
            instance.title = this.title;
            instance.note = this.note;
            instance.podId = this.podId;
            instance.priority = this.priority;
            instance.startAt = this.startAt;
            instance.deadlineAt = this.deadlineAt;
            instance.pomodoroCycles = this.pomodoroCycles;
            instance.status = this.status;
            return instance;
        }
    }
}
