package com.focusforge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_achievements", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_achievement", columnNames = {"user_id", "achievement_id"})
})
public class UserAchievement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

        @Column(name = "progress_value", nullable = false)
    private Integer progressValue = 0;

    @Column(name = "unlocked_at")
    private LocalDateTime unlockedAt;

    @Transient
    public boolean isUnlocked() {
        return unlockedAt != null;
    }

    public UserAchievement() {}

    public UserAchievement(Long id, User user, Achievement achievement, Integer progressValue, LocalDateTime unlockedAt) {
        this.id = id;
        this.user = user;
        this.achievement = achievement;
        this.progressValue = progressValue;
        this.unlockedAt = unlockedAt;
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

    public Achievement getAchievement() {
        return this.achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Integer getProgressValue() {
        return this.progressValue;
    }

    public void setProgressValue(Integer progressValue) {
        this.progressValue = progressValue;
    }

    public LocalDateTime getUnlockedAt() {
        return this.unlockedAt;
    }

    public void setUnlockedAt(LocalDateTime unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private Achievement achievement;
        private Integer progressValue = 0;
        private LocalDateTime unlockedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder achievement(Achievement achievement) {
            this.achievement = achievement;
            return this;
        }

        public Builder progressValue(Integer progressValue) {
            this.progressValue = progressValue;
            return this;
        }

        public Builder unlockedAt(LocalDateTime unlockedAt) {
            this.unlockedAt = unlockedAt;
            return this;
        }

        public UserAchievement build() {
            UserAchievement instance = new UserAchievement();
            instance.id = this.id;
            instance.user = this.user;
            instance.achievement = this.achievement;
            instance.progressValue = this.progressValue;
            instance.unlockedAt = this.unlockedAt;
            return instance;
        }
    }
}
