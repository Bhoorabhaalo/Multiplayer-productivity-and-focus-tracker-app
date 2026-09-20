package com.focusforge.dto.response;

import com.focusforge.entity.Achievement;

import java.time.LocalDateTime;

public class AchievementResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Achievement.Tier tier;
    private Achievement.Metric metric;
    private Integer targetValue;
    private Integer xpReward;
    private String icon;
    private Integer progressValue;
    private Integer progressPercent;
    private Boolean unlocked;
    private LocalDateTime unlockedAt;
    private String progressText;

    public AchievementResponse() {}

    public AchievementResponse(Long id, String code, String name, String description, Achievement.Tier tier, Achievement.Metric metric, Integer targetValue, Integer xpReward, String icon, Integer progressValue, Integer progressPercent, Boolean unlocked, LocalDateTime unlockedAt, String progressText) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.tier = tier;
        this.metric = metric;
        this.targetValue = targetValue;
        this.xpReward = xpReward;
        this.icon = icon;
        this.progressValue = progressValue;
        this.progressPercent = progressPercent;
        this.unlocked = unlocked;
        this.unlockedAt = unlockedAt;
        this.progressText = progressText;
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

    public Achievement.Tier getTier() {
        return this.tier;
    }

    public void setTier(Achievement.Tier tier) {
        this.tier = tier;
    }

    public Achievement.Metric getMetric() {
        return this.metric;
    }

    public void setMetric(Achievement.Metric metric) {
        this.metric = metric;
    }

    public Integer getTargetValue() {
        return this.targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getXpReward() {
        return this.xpReward;
    }

    public void setXpReward(Integer xpReward) {
        this.xpReward = xpReward;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getProgressValue() {
        return this.progressValue;
    }

    public void setProgressValue(Integer progressValue) {
        this.progressValue = progressValue;
    }

    public Integer getProgressPercent() {
        return this.progressPercent;
    }

    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }

    public Boolean getUnlocked() {
        return this.unlocked;
    }

    public void setUnlocked(Boolean unlocked) {
        this.unlocked = unlocked;
    }

    public LocalDateTime getUnlockedAt() {
        return this.unlockedAt;
    }

    public void setUnlockedAt(LocalDateTime unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public String getProgressText() {
        return this.progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String name;
        private String description;
        private Achievement.Tier tier;
        private Achievement.Metric metric;
        private Integer targetValue;
        private Integer xpReward;
        private String icon;
        private Integer progressValue;
        private Integer progressPercent;
        private Boolean unlocked;
        private LocalDateTime unlockedAt;
        private String progressText;

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

        public Builder tier(Achievement.Tier tier) {
            this.tier = tier;
            return this;
        }

        public Builder metric(Achievement.Metric metric) {
            this.metric = metric;
            return this;
        }

        public Builder targetValue(Integer targetValue) {
            this.targetValue = targetValue;
            return this;
        }

        public Builder xpReward(Integer xpReward) {
            this.xpReward = xpReward;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder progressValue(Integer progressValue) {
            this.progressValue = progressValue;
            return this;
        }

        public Builder progressPercent(Integer progressPercent) {
            this.progressPercent = progressPercent;
            return this;
        }

        public Builder unlocked(Boolean unlocked) {
            this.unlocked = unlocked;
            return this;
        }

        public Builder unlockedAt(LocalDateTime unlockedAt) {
            this.unlockedAt = unlockedAt;
            return this;
        }

        public Builder progressText(String progressText) {
            this.progressText = progressText;
            return this;
        }

        public AchievementResponse build() {
            AchievementResponse instance = new AchievementResponse();
            instance.id = this.id;
            instance.code = this.code;
            instance.name = this.name;
            instance.description = this.description;
            instance.tier = this.tier;
            instance.metric = this.metric;
            instance.targetValue = this.targetValue;
            instance.xpReward = this.xpReward;
            instance.icon = this.icon;
            instance.progressValue = this.progressValue;
            instance.progressPercent = this.progressPercent;
            instance.unlocked = this.unlocked;
            instance.unlockedAt = this.unlockedAt;
            instance.progressText = this.progressText;
            return instance;
        }
    }
}
