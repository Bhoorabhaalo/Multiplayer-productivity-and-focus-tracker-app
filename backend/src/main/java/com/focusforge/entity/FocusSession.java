package com.focusforge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "focus_sessions", indexes = {
    @Index(name = "idx_focus_sessions_user_started", columnList = "user_id, started_at")
})
public class FocusSession {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
        private Mode mode = Mode.SQUAD_SPRINT;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

        @Column(name = "planned_seconds", nullable = false)
    private Integer plannedSeconds = 1500;

        @Column(name = "actual_focus_seconds", nullable = false)
    private Integer actualFocusSeconds = 0;

        @Column(name = "cycles_completed", nullable = false)
    private Integer cyclesCompleted = 0;

        @Column(nullable = false)
    private Integer distractions = 0;

        @Column(nullable = false)
    private Boolean completed = false;

        @Column(name = "xp_earned", nullable = false)
    private Integer xpEarned = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum Mode {
        SQUAD_SPRINT, DEEP_FOCUS
    }

    public FocusSession() {}

    public FocusSession(Long id, User user, Room room, Task task, Mode mode, LocalDateTime startedAt, LocalDateTime endedAt, Integer plannedSeconds, Integer actualFocusSeconds, Integer cyclesCompleted, Integer distractions, Boolean completed, Integer xpEarned, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.task = task;
        this.mode = mode;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.plannedSeconds = plannedSeconds;
        this.actualFocusSeconds = actualFocusSeconds;
        this.cyclesCompleted = cyclesCompleted;
        this.distractions = distractions;
        this.completed = completed;
        this.xpEarned = xpEarned;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private Room room;
        private Task task;
        private Mode mode = Mode.SQUAD_SPRINT;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Integer plannedSeconds = 1500;
        private Integer actualFocusSeconds = 0;
        private Integer cyclesCompleted = 0;
        private Integer distractions = 0;
        private Boolean completed = false;
        private Integer xpEarned = 0;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder task(Task task) {
            this.task = task;
            return this;
        }

        public Builder mode(Mode mode) {
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FocusSession build() {
            FocusSession instance = new FocusSession();
            instance.id = this.id;
            instance.user = this.user;
            instance.room = this.room;
            instance.task = this.task;
            instance.mode = this.mode;
            instance.startedAt = this.startedAt;
            instance.endedAt = this.endedAt;
            instance.plannedSeconds = this.plannedSeconds;
            instance.actualFocusSeconds = this.actualFocusSeconds;
            instance.cyclesCompleted = this.cyclesCompleted;
            instance.distractions = this.distractions;
            instance.completed = this.completed;
            instance.xpEarned = this.xpEarned;
            instance.createdAt = this.createdAt;
            return instance;
        }
    }
}
