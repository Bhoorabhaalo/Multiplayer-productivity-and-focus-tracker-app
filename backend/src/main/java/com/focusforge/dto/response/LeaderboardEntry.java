package com.focusforge.dto.response;


public class LeaderboardEntry {

    private Integer rank;
    private Long userId;
    private String username;
    private String displayName;
    private String avatarUrl;
    private Integer focusMinutes;
    private String focusTimeFormatted;
    private Integer xp;
    private Boolean isCurrentUser;

    public LeaderboardEntry() {}

    public LeaderboardEntry(Integer rank, Long userId, String username, String displayName, String avatarUrl, Integer focusMinutes, String focusTimeFormatted, Integer xp, Boolean isCurrentUser) {
        this.rank = rank;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.focusMinutes = focusMinutes;
        this.focusTimeFormatted = focusTimeFormatted;
        this.xp = xp;
        this.isCurrentUser = isCurrentUser;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getFocusMinutes() {
        return this.focusMinutes;
    }

    public void setFocusMinutes(Integer focusMinutes) {
        this.focusMinutes = focusMinutes;
    }

    public String getFocusTimeFormatted() {
        return this.focusTimeFormatted;
    }

    public void setFocusTimeFormatted(String focusTimeFormatted) {
        this.focusTimeFormatted = focusTimeFormatted;
    }

    public Integer getXp() {
        return this.xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Boolean isCurrentUser() {
        return this.isCurrentUser;
    }

    public void setIsCurrentUser(Boolean isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer rank;
        private Long userId;
        private String username;
        private String displayName;
        private String avatarUrl;
        private Integer focusMinutes;
        private String focusTimeFormatted;
        private Integer xp;
        private Boolean isCurrentUser;

        public Builder rank(Integer rank) {
            this.rank = rank;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
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

        public Builder focusMinutes(Integer focusMinutes) {
            this.focusMinutes = focusMinutes;
            return this;
        }

        public Builder focusTimeFormatted(String focusTimeFormatted) {
            this.focusTimeFormatted = focusTimeFormatted;
            return this;
        }

        public Builder xp(Integer xp) {
            this.xp = xp;
            return this;
        }

        public Builder isCurrentUser(Boolean isCurrentUser) {
            this.isCurrentUser = isCurrentUser;
            return this;
        }

        public LeaderboardEntry build() {
            LeaderboardEntry instance = new LeaderboardEntry();
            instance.rank = this.rank;
            instance.userId = this.userId;
            instance.username = this.username;
            instance.displayName = this.displayName;
            instance.avatarUrl = this.avatarUrl;
            instance.focusMinutes = this.focusMinutes;
            instance.focusTimeFormatted = this.focusTimeFormatted;
            instance.xp = this.xp;
            instance.isCurrentUser = this.isCurrentUser;
            return instance;
        }
    }
}
