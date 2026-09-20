package com.focusforge.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_focus_stats", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_stat_date", columnNames = {"user_id", "stat_date"})
}, indexes = {
    @Index(name = "idx_daily_stats_user_date", columnList = "user_id, stat_date")
})
public class DailyFocusStat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;

        @Column(name = "focus_seconds", nullable = false)
    private Integer focusSeconds = 0;

        @Column(name = "sessions_completed", nullable = false)
    private Integer sessionsCompleted = 0;

    public DailyFocusStat() {}

    public DailyFocusStat(Long id, User user, LocalDate statDate, Integer focusSeconds, Integer sessionsCompleted) {
        this.id = id;
        this.user = user;
        this.statDate = statDate;
        this.focusSeconds = focusSeconds;
        this.sessionsCompleted = sessionsCompleted;
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

    public LocalDate getStatDate() {
        return this.statDate;
    }

    public void setStatDate(LocalDate statDate) {
        this.statDate = statDate;
    }

    public Integer getFocusSeconds() {
        return this.focusSeconds;
    }

    public void setFocusSeconds(Integer focusSeconds) {
        this.focusSeconds = focusSeconds;
    }

    public Integer getSessionsCompleted() {
        return this.sessionsCompleted;
    }

    public void setSessionsCompleted(Integer sessionsCompleted) {
        this.sessionsCompleted = sessionsCompleted;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private LocalDate statDate;
        private Integer focusSeconds = 0;
        private Integer sessionsCompleted = 0;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder statDate(LocalDate statDate) {
            this.statDate = statDate;
            return this;
        }

        public Builder focusSeconds(Integer focusSeconds) {
            this.focusSeconds = focusSeconds;
            return this;
        }

        public Builder sessionsCompleted(Integer sessionsCompleted) {
            this.sessionsCompleted = sessionsCompleted;
            return this;
        }

        public DailyFocusStat build() {
            DailyFocusStat instance = new DailyFocusStat();
            instance.id = this.id;
            instance.user = this.user;
            instance.statDate = this.statDate;
            instance.focusSeconds = this.focusSeconds;
            instance.sessionsCompleted = this.sessionsCompleted;
            return instance;
        }
    }
}
