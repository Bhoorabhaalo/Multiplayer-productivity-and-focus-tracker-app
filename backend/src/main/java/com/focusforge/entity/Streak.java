package com.focusforge.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Table(name = "streaks")
public class Streak implements Persistable<Long> {


    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Transient
    private boolean isNew = true;

    @Override
    public Long getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return this.isNew || this.userId == null;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

        @Column(name = "current_streak", nullable = false)
    private Integer currentStreak = 0;

        @Column(name = "longest_streak", nullable = false)
    private Integer longestStreak = 0;

    @Column(name = "last_focus_date")
    private LocalDate lastFocusDate;

    public Streak() {}

    public Streak(Long userId, User user, Integer currentStreak, Integer longestStreak, LocalDate lastFocusDate) {
        this.userId = userId;
        this.user = user;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastFocusDate = lastFocusDate;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCurrentStreak() {
        return this.currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Integer getLongestStreak() {
        return this.longestStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public LocalDate getLastFocusDate() {
        return this.lastFocusDate;
    }

    public void setLastFocusDate(LocalDate lastFocusDate) {
        this.lastFocusDate = lastFocusDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private User user;
        private Integer currentStreak = 0;
        private Integer longestStreak = 0;
        private LocalDate lastFocusDate;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder currentStreak(Integer currentStreak) {
            this.currentStreak = currentStreak;
            return this;
        }

        public Builder longestStreak(Integer longestStreak) {
            this.longestStreak = longestStreak;
            return this;
        }

        public Builder lastFocusDate(LocalDate lastFocusDate) {
            this.lastFocusDate = lastFocusDate;
            return this;
        }

        public Streak build() {
            Streak instance = new Streak();
            instance.userId = this.userId;
            instance.user = this.user;
            instance.currentStreak = this.currentStreak;
            instance.longestStreak = this.longestStreak;
            instance.lastFocusDate = this.lastFocusDate;
            return instance;
        }
    }
}
