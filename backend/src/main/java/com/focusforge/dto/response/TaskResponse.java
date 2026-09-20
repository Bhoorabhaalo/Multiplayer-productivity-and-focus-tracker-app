package com.focusforge.dto.response;

import com.focusforge.entity.Task;

import java.time.LocalDateTime;

public class TaskResponse {

    private Long id;
    private String title;
    private String note;
    private PodResponse pod;
    private Task.Priority priority;
    private LocalDateTime startAt;
    private LocalDateTime deadlineAt;
    private Integer pomodoroCycles;
    private Task.TaskStatus status;
    private LocalDateTime completedAt;
    private Integer xpAwarded;
    private String formattedStartTime;
    private String formattedDeadline;
    private String formattedCompletedTime;

    public TaskResponse() {}

    public TaskResponse(Long id, String title, String note, PodResponse pod, Task.Priority priority, LocalDateTime startAt, LocalDateTime deadlineAt, Integer pomodoroCycles, Task.TaskStatus status, LocalDateTime completedAt, Integer xpAwarded, String formattedStartTime, String formattedDeadline, String formattedCompletedTime) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.pod = pod;
        this.priority = priority;
        this.startAt = startAt;
        this.deadlineAt = deadlineAt;
        this.pomodoroCycles = pomodoroCycles;
        this.status = status;
        this.completedAt = completedAt;
        this.xpAwarded = xpAwarded;
        this.formattedStartTime = formattedStartTime;
        this.formattedDeadline = formattedDeadline;
        this.formattedCompletedTime = formattedCompletedTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PodResponse getPod() {
        return this.pod;
    }

    public void setPod(PodResponse pod) {
        this.pod = pod;
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

    public LocalDateTime getCompletedAt() {
        return this.completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Integer getXpAwarded() {
        return this.xpAwarded;
    }

    public void setXpAwarded(Integer xpAwarded) {
        this.xpAwarded = xpAwarded;
    }

    public String getFormattedStartTime() {
        return this.formattedStartTime;
    }

    public void setFormattedStartTime(String formattedStartTime) {
        this.formattedStartTime = formattedStartTime;
    }

    public String getFormattedDeadline() {
        return this.formattedDeadline;
    }

    public void setFormattedDeadline(String formattedDeadline) {
        this.formattedDeadline = formattedDeadline;
    }

    public String getFormattedCompletedTime() {
        return this.formattedCompletedTime;
    }

    public void setFormattedCompletedTime(String formattedCompletedTime) {
        this.formattedCompletedTime = formattedCompletedTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String note;
        private PodResponse pod;
        private Task.Priority priority;
        private LocalDateTime startAt;
        private LocalDateTime deadlineAt;
        private Integer pomodoroCycles;
        private Task.TaskStatus status;
        private LocalDateTime completedAt;
        private Integer xpAwarded;
        private String formattedStartTime;
        private String formattedDeadline;
        private String formattedCompletedTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder pod(PodResponse pod) {
            this.pod = pod;
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

        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder xpAwarded(Integer xpAwarded) {
            this.xpAwarded = xpAwarded;
            return this;
        }

        public Builder formattedStartTime(String formattedStartTime) {
            this.formattedStartTime = formattedStartTime;
            return this;
        }

        public Builder formattedDeadline(String formattedDeadline) {
            this.formattedDeadline = formattedDeadline;
            return this;
        }

        public Builder formattedCompletedTime(String formattedCompletedTime) {
            this.formattedCompletedTime = formattedCompletedTime;
            return this;
        }

        public TaskResponse build() {
            TaskResponse instance = new TaskResponse();
            instance.id = this.id;
            instance.title = this.title;
            instance.note = this.note;
            instance.pod = this.pod;
            instance.priority = this.priority;
            instance.startAt = this.startAt;
            instance.deadlineAt = this.deadlineAt;
            instance.pomodoroCycles = this.pomodoroCycles;
            instance.status = this.status;
            instance.completedAt = this.completedAt;
            instance.xpAwarded = this.xpAwarded;
            instance.formattedStartTime = this.formattedStartTime;
            instance.formattedDeadline = this.formattedDeadline;
            instance.formattedCompletedTime = this.formattedCompletedTime;
            return instance;
        }
    }
}
