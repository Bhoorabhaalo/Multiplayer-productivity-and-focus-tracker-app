package com.focusforge.dto.response;


import java.util.List;

public class DashboardResponse {

    private String greeting;
    private String displayName;
    private String avatarUrl;
    private Integer level;
    private String levelLabel;
    private Integer totalXp;
    private Integer todayFocusMinutes;
    private Integer currentStreak;
    private Long activeTaskCount;
    private Integer weeklyXp;
    private RoomResponse activeRoom;
    private TaskResponse nextTask;
    private List<AchievementResponse> recentAchievements;
    private List<Double> weeklySparkline;

    public DashboardResponse() {}

    public DashboardResponse(String greeting, String displayName, String avatarUrl, Integer level, String levelLabel, Integer totalXp, Integer todayFocusMinutes, Integer currentStreak, Long activeTaskCount, Integer weeklyXp, RoomResponse activeRoom, TaskResponse nextTask, List<AchievementResponse> recentAchievements, List<Double> weeklySparkline) {
        this.greeting = greeting;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.level = level;
        this.levelLabel = levelLabel;
        this.totalXp = totalXp;
        this.todayFocusMinutes = todayFocusMinutes;
        this.currentStreak = currentStreak;
        this.activeTaskCount = activeTaskCount;
        this.weeklyXp = weeklyXp;
        this.activeRoom = activeRoom;
        this.nextTask = nextTask;
        this.recentAchievements = recentAchievements;
        this.weeklySparkline = weeklySparkline;
    }

    public String getGreeting() {
        return this.greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Integer getTotalXp() {
        return this.totalXp;
    }

    public void setTotalXp(Integer totalXp) {
        this.totalXp = totalXp;
    }

    public Integer getTodayFocusMinutes() {
        return this.todayFocusMinutes;
    }

    public void setTodayFocusMinutes(Integer todayFocusMinutes) {
        this.todayFocusMinutes = todayFocusMinutes;
    }

    public Integer getCurrentStreak() {
        return this.currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Long getActiveTaskCount() {
        return this.activeTaskCount;
    }

    public void setActiveTaskCount(Long activeTaskCount) {
        this.activeTaskCount = activeTaskCount;
    }

    public Integer getWeeklyXp() {
        return this.weeklyXp;
    }

    public void setWeeklyXp(Integer weeklyXp) {
        this.weeklyXp = weeklyXp;
    }

    public RoomResponse getActiveRoom() {
        return this.activeRoom;
    }

    public void setActiveRoom(RoomResponse activeRoom) {
        this.activeRoom = activeRoom;
    }

    public TaskResponse getNextTask() {
        return this.nextTask;
    }

    public void setNextTask(TaskResponse nextTask) {
        this.nextTask = nextTask;
    }

    public List<AchievementResponse> getRecentAchievements() {
        return this.recentAchievements;
    }

    public void setRecentAchievements(List<AchievementResponse> recentAchievements) {
        this.recentAchievements = recentAchievements;
    }

    public List<Double> getWeeklySparkline() {
        return this.weeklySparkline;
    }

    public void setWeeklySparkline(List<Double> weeklySparkline) {
        this.weeklySparkline = weeklySparkline;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String greeting;
        private String displayName;
        private String avatarUrl;
        private Integer level;
        private String levelLabel;
        private Integer totalXp;
        private Integer todayFocusMinutes;
        private Integer currentStreak;
        private Long activeTaskCount;
        private Integer weeklyXp;
        private RoomResponse activeRoom;
        private TaskResponse nextTask;
        private List<AchievementResponse> recentAchievements;
        private List<Double> weeklySparkline;

        public Builder greeting(String greeting) {
            this.greeting = greeting;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
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

        public Builder totalXp(Integer totalXp) {
            this.totalXp = totalXp;
            return this;
        }

        public Builder todayFocusMinutes(Integer todayFocusMinutes) {
            this.todayFocusMinutes = todayFocusMinutes;
            return this;
        }

        public Builder currentStreak(Integer currentStreak) {
            this.currentStreak = currentStreak;
            return this;
        }

        public Builder activeTaskCount(Long activeTaskCount) {
            this.activeTaskCount = activeTaskCount;
            return this;
        }

        public Builder weeklyXp(Integer weeklyXp) {
            this.weeklyXp = weeklyXp;
            return this;
        }

        public Builder activeRoom(RoomResponse activeRoom) {
            this.activeRoom = activeRoom;
            return this;
        }

        public Builder nextTask(TaskResponse nextTask) {
            this.nextTask = nextTask;
            return this;
        }

        public Builder recentAchievements(List<AchievementResponse> recentAchievements) {
            this.recentAchievements = recentAchievements;
            return this;
        }

        public Builder weeklySparkline(List<Double> weeklySparkline) {
            this.weeklySparkline = weeklySparkline;
            return this;
        }

        public DashboardResponse build() {
            DashboardResponse instance = new DashboardResponse();
            instance.greeting = this.greeting;
            instance.displayName = this.displayName;
            instance.avatarUrl = this.avatarUrl;
            instance.level = this.level;
            instance.levelLabel = this.levelLabel;
            instance.totalXp = this.totalXp;
            instance.todayFocusMinutes = this.todayFocusMinutes;
            instance.currentStreak = this.currentStreak;
            instance.activeTaskCount = this.activeTaskCount;
            instance.weeklyXp = this.weeklyXp;
            instance.activeRoom = this.activeRoom;
            instance.nextTask = this.nextTask;
            instance.recentAchievements = this.recentAchievements;
            instance.weeklySparkline = this.weeklySparkline;
            return instance;
        }
    }
}
