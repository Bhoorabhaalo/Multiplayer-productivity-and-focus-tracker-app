package com.focusforge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks", indexes = {
    @Index(name = "idx_tasks_user_status", columnList = "user_id, status"),
    @Index(name = "idx_tasks_start_at", columnList = "start_at")
})
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 300)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pod_id")
    private Pod pod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private Priority priority = Priority.MEDIUM;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "deadline_at")
    private LocalDateTime deadlineAt;

    @Column(name = "pomodoro_cycles")
    private Integer pomodoroCycles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private TaskStatus status = TaskStatus.ACTIVE;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

        @Column(name = "xp_awarded", nullable = false)
    private Integer xpAwarded = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public enum TaskStatus {
        ACTIVE, COMPLETED
    }

    public Task() {}

    public Task(Long id, User user, String title, String note, Pod pod, Priority priority, LocalDateTime startAt, LocalDateTime deadlineAt, Integer pomodoroCycles, TaskStatus status, LocalDateTime completedAt, Integer xpAwarded, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Pod getPod() {
        return this.pod;
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
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

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private String title;
        private String note;
        private Pod pod;
        private Priority priority = Priority.MEDIUM;
        private LocalDateTime startAt;
        private LocalDateTime deadlineAt;
        private Integer pomodoroCycles;
        private TaskStatus status = TaskStatus.ACTIVE;
        private LocalDateTime completedAt;
        private Integer xpAwarded = 0;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
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

        public Builder pod(Pod pod) {
            this.pod = pod;
            return this;
        }

        public Builder priority(Priority priority) {
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

        public Builder status(TaskStatus status) {
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Task build() {
            Task instance = new Task();
            instance.id = this.id;
            instance.user = this.user;
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
            instance.createdAt = this.createdAt;
            instance.updatedAt = this.updatedAt;
            return instance;
        }
    }
}
