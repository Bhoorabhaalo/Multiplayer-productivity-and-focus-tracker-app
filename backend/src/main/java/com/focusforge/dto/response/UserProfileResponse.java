package com.focusforge.dto.response;

import com.focusforge.entity.User;

import java.util.List;

public class UserProfileResponse {

    private Long id;
    private String email;
    private String username;
    private String displayName;
    private String avatarUrl;
    private String university;
    private String major;
    private String focusStatement;
    private String sprintStatus;
    private Integer level;
    private String levelLabel;
    private Integer totalXp;
    private Integer xpToNextLevel;
    private Integer progressPercent;
    private Integer currentStreak;
    private Integer longestStreak;
    private User.InterfaceDensity interfaceDensity;
    private Boolean soundEffectsEnabled;
    private Boolean compactSidebar;
    private User.Role role;
    private List<PodResponse> enrolledPods;

    public UserProfileResponse() {}

    public UserProfileResponse(Long id, String email, String username, String displayName, String avatarUrl, String university, String major, String focusStatement, String sprintStatus, Integer level, String levelLabel, Integer totalXp, Integer xpToNextLevel, Integer progressPercent, Integer currentStreak, Integer longestStreak, User.InterfaceDensity interfaceDensity, Boolean soundEffectsEnabled, Boolean compactSidebar, User.Role role, List<PodResponse> enrolledPods) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.university = university;
        this.major = major;
        this.focusStatement = focusStatement;
        this.sprintStatus = sprintStatus;
        this.level = level;
        this.levelLabel = levelLabel;
        this.totalXp = totalXp;
        this.xpToNextLevel = xpToNextLevel;
        this.progressPercent = progressPercent;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.interfaceDensity = interfaceDensity;
        this.soundEffectsEnabled = soundEffectsEnabled;
        this.compactSidebar = compactSidebar;
        this.role = role;
        this.enrolledPods = enrolledPods;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUniversity() {
        return this.university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getFocusStatement() {
        return this.focusStatement;
    }

    public void setFocusStatement(String focusStatement) {
        this.focusStatement = focusStatement;
    }

    public String getSprintStatus() {
        return this.sprintStatus;
    }

    public void setSprintStatus(String sprintStatus) {
        this.sprintStatus = sprintStatus;
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

    public Integer getXpToNextLevel() {
        return this.xpToNextLevel;
    }

    public void setXpToNextLevel(Integer xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public Integer getProgressPercent() {
        return this.progressPercent;
    }

    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
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

    public User.InterfaceDensity getInterfaceDensity() {
        return this.interfaceDensity;
    }

    public void setInterfaceDensity(User.InterfaceDensity interfaceDensity) {
        this.interfaceDensity = interfaceDensity;
    }

    public Boolean getSoundEffectsEnabled() {
        return this.soundEffectsEnabled;
    }

    public void setSoundEffectsEnabled(Boolean soundEffectsEnabled) {
        this.soundEffectsEnabled = soundEffectsEnabled;
    }

    public Boolean getCompactSidebar() {
        return this.compactSidebar;
    }

    public void setCompactSidebar(Boolean compactSidebar) {
        this.compactSidebar = compactSidebar;
    }

    public User.Role getRole() {
        return this.role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public List<PodResponse> getEnrolledPods() {
        return this.enrolledPods;
    }

    public void setEnrolledPods(List<PodResponse> enrolledPods) {
        this.enrolledPods = enrolledPods;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email;
        private String username;
        private String displayName;
        private String avatarUrl;
        private String university;
        private String major;
        private String focusStatement;
        private String sprintStatus;
        private Integer level;
        private String levelLabel;
        private Integer totalXp;
        private Integer xpToNextLevel;
        private Integer progressPercent;
        private Integer currentStreak;
        private Integer longestStreak;
        private User.InterfaceDensity interfaceDensity;
        private Boolean soundEffectsEnabled;
        private Boolean compactSidebar;
        private User.Role role;
        private List<PodResponse> enrolledPods;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
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

        public Builder university(String university) {
            this.university = university;
            return this;
        }

        public Builder major(String major) {
            this.major = major;
            return this;
        }

        public Builder focusStatement(String focusStatement) {
            this.focusStatement = focusStatement;
            return this;
        }

        public Builder sprintStatus(String sprintStatus) {
            this.sprintStatus = sprintStatus;
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

        public Builder xpToNextLevel(Integer xpToNextLevel) {
            this.xpToNextLevel = xpToNextLevel;
            return this;
        }

        public Builder progressPercent(Integer progressPercent) {
            this.progressPercent = progressPercent;
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

        public Builder interfaceDensity(User.InterfaceDensity interfaceDensity) {
            this.interfaceDensity = interfaceDensity;
            return this;
        }

        public Builder soundEffectsEnabled(Boolean soundEffectsEnabled) {
            this.soundEffectsEnabled = soundEffectsEnabled;
            return this;
        }

        public Builder compactSidebar(Boolean compactSidebar) {
            this.compactSidebar = compactSidebar;
            return this;
        }

        public Builder role(User.Role role) {
            this.role = role;
            return this;
        }

        public Builder enrolledPods(List<PodResponse> enrolledPods) {
            this.enrolledPods = enrolledPods;
            return this;
        }

        public UserProfileResponse build() {
            UserProfileResponse instance = new UserProfileResponse();
            instance.id = this.id;
            instance.email = this.email;
            instance.username = this.username;
            instance.displayName = this.displayName;
            instance.avatarUrl = this.avatarUrl;
            instance.university = this.university;
            instance.major = this.major;
            instance.focusStatement = this.focusStatement;
            instance.sprintStatus = this.sprintStatus;
            instance.level = this.level;
            instance.levelLabel = this.levelLabel;
            instance.totalXp = this.totalXp;
            instance.xpToNextLevel = this.xpToNextLevel;
            instance.progressPercent = this.progressPercent;
            instance.currentStreak = this.currentStreak;
            instance.longestStreak = this.longestStreak;
            instance.interfaceDensity = this.interfaceDensity;
            instance.soundEffectsEnabled = this.soundEffectsEnabled;
            instance.compactSidebar = this.compactSidebar;
            instance.role = this.role;
            instance.enrolledPods = this.enrolledPods;
            return instance;
        }
    }
}
