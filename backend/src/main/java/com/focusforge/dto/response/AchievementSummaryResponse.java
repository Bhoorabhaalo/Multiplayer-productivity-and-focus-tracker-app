package com.focusforge.dto.response;


public class AchievementSummaryResponse {

    private Integer completedCount;
    private Integer totalCount;
    private Integer level;
    private String levelLabel;
    private Integer xpEarned;
    private Integer progressToNextLevel;
    private String tierLabel;

    public AchievementSummaryResponse() {}

    public AchievementSummaryResponse(Integer completedCount, Integer totalCount, Integer level, String levelLabel, Integer xpEarned, Integer progressToNextLevel, String tierLabel) {
        this.completedCount = completedCount;
        this.totalCount = totalCount;
        this.level = level;
        this.levelLabel = levelLabel;
        this.xpEarned = xpEarned;
        this.progressToNextLevel = progressToNextLevel;
        this.tierLabel = tierLabel;
    }

    public Integer getCompletedCount() {
        return this.completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelLabel() {
        return this.levelLabel;
    }

    public void setLevelLabel(String levelLabel) {
        this.levelLabel = levelLabel;
    }

    public Integer getXpEarned() {
        return this.xpEarned;
    }

    public void setXpEarned(Integer xpEarned) {
        this.xpEarned = xpEarned;
    }

    public Integer getProgressToNextLevel() {
        return this.progressToNextLevel;
    }

    public void setProgressToNextLevel(Integer progressToNextLevel) {
        this.progressToNextLevel = progressToNextLevel;
    }

    public String getTierLabel() {
        return this.tierLabel;
    }

    public void setTierLabel(String tierLabel) {
        this.tierLabel = tierLabel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer completedCount;
        private Integer totalCount;
        private Integer level;
        private String levelLabel;
        private Integer xpEarned;
        private Integer progressToNextLevel;
        private String tierLabel;

        public Builder completedCount(Integer completedCount) {
            this.completedCount = completedCount;
            return this;
        }

        public Builder totalCount(Integer totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder level(Integer level) {
            this.level = level;
            return this;
        }

        public Builder levelLabel(String levelLabel) {
            this.levelLabel = levelLabel;
            return this;
        }

        public Builder xpEarned(Integer xpEarned) {
            this.xpEarned = xpEarned;
            return this;
        }

        public Builder progressToNextLevel(Integer progressToNextLevel) {
            this.progressToNextLevel = progressToNextLevel;
            return this;
        }

        public Builder tierLabel(String tierLabel) {
            this.tierLabel = tierLabel;
            return this;
        }

        public AchievementSummaryResponse build() {
            AchievementSummaryResponse instance = new AchievementSummaryResponse();
            instance.completedCount = this.completedCount;
            instance.totalCount = this.totalCount;
            instance.level = this.level;
            instance.levelLabel = this.levelLabel;
            instance.xpEarned = this.xpEarned;
            instance.progressToNextLevel = this.progressToNextLevel;
            instance.tierLabel = this.tierLabel;
            return instance;
        }
    }
}
