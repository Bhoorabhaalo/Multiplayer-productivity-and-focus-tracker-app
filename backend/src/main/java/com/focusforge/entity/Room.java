package com.focusforge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms", indexes = {
    @Index(name = "idx_rooms_code", columnList = "code"),
    @Index(name = "idx_rooms_status", columnList = "status")
})
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pod_id")
    private Pod pod;

        @Column(name = "focus_minutes", nullable = false)
    private Integer focusMinutes = 25;

        @Column(name = "break_minutes", nullable = false)
    private Integer breakMinutes = 5;

        @Column(name = "target_cycles", nullable = false)
    private Integer targetCycles = 4;

        @Column(name = "current_cycle", nullable = false)
    private Integer currentCycle = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private Phase phase = Phase.IDLE;

    @Column(name = "phase_started_at")
    private LocalDateTime phaseStartedAt;

    @Column(name = "phase_ends_at")
    private LocalDateTime phaseEndsAt;

        @Column(name = "max_members", nullable = false)
    private Integer maxMembers = 10;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private Status status = Status.ACTIVE;

        @Column(nullable = false)
    private Boolean paused = false;

    @Column(name = "remaining_seconds_when_paused")
    private Integer remainingSecondsWhenPaused;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Phase {
        IDLE, FOCUS, BREAK, COMPLETED
    }

    public enum Status {
        ACTIVE, ENDED
    }

    public Room() {}

    public Room(Long id, String code, String name, String description, User host, Pod pod, Integer focusMinutes, Integer breakMinutes, Integer targetCycles, Integer currentCycle, Phase phase, LocalDateTime phaseStartedAt, LocalDateTime phaseEndsAt, Integer maxMembers, Status status, Boolean paused, Integer remainingSecondsWhenPaused, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.host = host;
        this.pod = pod;
        this.focusMinutes = focusMinutes;
        this.breakMinutes = breakMinutes;
        this.targetCycles = targetCycles;
        this.currentCycle = currentCycle;
        this.phase = phase;
        this.phaseStartedAt = phaseStartedAt;
        this.phaseEndsAt = phaseEndsAt;
        this.maxMembers = maxMembers;
        this.status = status;
        this.paused = paused;
        this.remainingSecondsWhenPaused = remainingSecondsWhenPaused;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public User getHost() {
        return this.host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Pod getPod() {
        return this.pod;
    }

    public void setPod(Pod pod) {
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

    public Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Phase phase) {
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

    public Integer getMaxMembers() {
        return this.maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
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
        private String code;
        private String name;
        private String description;
        private User host;
        private Pod pod;
        private Integer focusMinutes = 25;
        private Integer breakMinutes = 5;
        private Integer targetCycles = 4;
        private Integer currentCycle = 0;
        private Phase phase = Phase.IDLE;
        private LocalDateTime phaseStartedAt;
        private LocalDateTime phaseEndsAt;
        private Integer maxMembers = 10;
        private Status status = Status.ACTIVE;
        private Boolean paused = false;
        private Integer remainingSecondsWhenPaused;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

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

        public Builder host(User host) {
            this.host = host;
            return this;
        }

        public Builder pod(Pod pod) {
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

        public Builder phase(Phase phase) {
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

        public Builder maxMembers(Integer maxMembers) {
            this.maxMembers = maxMembers;
            return this;
        }

        public Builder status(Status status) {
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Room build() {
            Room instance = new Room();
            instance.id = this.id;
            instance.code = this.code;
            instance.name = this.name;
            instance.description = this.description;
            instance.host = this.host;
            instance.pod = this.pod;
            instance.focusMinutes = this.focusMinutes;
            instance.breakMinutes = this.breakMinutes;
            instance.targetCycles = this.targetCycles;
            instance.currentCycle = this.currentCycle;
            instance.phase = this.phase;
            instance.phaseStartedAt = this.phaseStartedAt;
            instance.phaseEndsAt = this.phaseEndsAt;
            instance.maxMembers = this.maxMembers;
            instance.status = this.status;
            instance.paused = this.paused;
            instance.remainingSecondsWhenPaused = this.remainingSecondsWhenPaused;
            instance.createdAt = this.createdAt;
            instance.updatedAt = this.updatedAt;
            return instance;
        }
    }
}
