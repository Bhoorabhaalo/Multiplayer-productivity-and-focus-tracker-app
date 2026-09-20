package com.focusforge.dto.response;

import com.focusforge.entity.RoomMember;

import java.time.LocalDateTime;

public class RoomMemberResponse {

    private Long id;
    private Long userId;
    private String username;
    private String displayName;
    private String avatarUrl;
    private RoomMember.MemberRole role;
    private RoomMember.MemberState state;
    private String currentActivity;
    private Integer focusSeconds;
    private Integer focusMinutes;
    private Integer karmaPoints;
    private Integer progressPercent;
    private LocalDateTime joinedAt;
    private Boolean isSelf;

    public RoomMemberResponse() {}

    public RoomMemberResponse(Long id, Long userId, String username, String displayName, String avatarUrl, RoomMember.MemberRole role, RoomMember.MemberState state, String currentActivity, Integer focusSeconds, Integer focusMinutes, Integer karmaPoints, Integer progressPercent, LocalDateTime joinedAt, Boolean isSelf) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.state = state;
        this.currentActivity = currentActivity;
        this.focusSeconds = focusSeconds;
        this.focusMinutes = focusMinutes;
        this.karmaPoints = karmaPoints;
        this.progressPercent = progressPercent;
        this.joinedAt = joinedAt;
        this.isSelf = isSelf;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RoomMember.MemberRole getRole() {
        return this.role;
    }

    public void setRole(RoomMember.MemberRole role) {
        this.role = role;
    }

    public RoomMember.MemberState getState() {
        return this.state;
    }

    public void setState(RoomMember.MemberState state) {
        this.state = state;
    }

    public String getCurrentActivity() {
        return this.currentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Integer getFocusSeconds() {
        return this.focusSeconds;
    }

    public void setFocusSeconds(Integer focusSeconds) {
        this.focusSeconds = focusSeconds;
    }

    public Integer getFocusMinutes() {
        return this.focusMinutes;
    }

    public void setFocusMinutes(Integer focusMinutes) {
        this.focusMinutes = focusMinutes;
    }

    public Integer getKarmaPoints() {
        return this.karmaPoints;
    }

    public void setKarmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    public Integer getProgressPercent() {
        return this.progressPercent;
    }

    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }

    public LocalDateTime getJoinedAt() {
        return this.joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Boolean isSelf() {
        return this.isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private String username;
        private String displayName;
        private String avatarUrl;
        private RoomMember.MemberRole role;
        private RoomMember.MemberState state;
        private String currentActivity;
        private Integer focusSeconds;
        private Integer focusMinutes;
        private Integer karmaPoints;
        private Integer progressPercent;
        private LocalDateTime joinedAt;
        private Boolean isSelf;

        public Builder id(Long id) {
            this.id = id;
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

        public Builder role(RoomMember.MemberRole role) {
            this.role = role;
            return this;
        }

        public Builder state(RoomMember.MemberState state) {
            this.state = state;
            return this;
        }

        public Builder currentActivity(String currentActivity) {
            this.currentActivity = currentActivity;
            return this;
        }

        public Builder focusSeconds(Integer focusSeconds) {
            this.focusSeconds = focusSeconds;
            return this;
        }

        public Builder focusMinutes(Integer focusMinutes) {
            this.focusMinutes = focusMinutes;
            return this;
        }

        public Builder karmaPoints(Integer karmaPoints) {
            this.karmaPoints = karmaPoints;
            return this;
        }

        public Builder progressPercent(Integer progressPercent) {
            this.progressPercent = progressPercent;
            return this;
        }

        public Builder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public Builder isSelf(Boolean isSelf) {
            this.isSelf = isSelf;
            return this;
        }

        public RoomMemberResponse build() {
            RoomMemberResponse instance = new RoomMemberResponse();
            instance.id = this.id;
            instance.userId = this.userId;
            instance.username = this.username;
            instance.displayName = this.displayName;
            instance.avatarUrl = this.avatarUrl;
            instance.role = this.role;
            instance.state = this.state;
            instance.currentActivity = this.currentActivity;
            instance.focusSeconds = this.focusSeconds;
            instance.focusMinutes = this.focusMinutes;
            instance.karmaPoints = this.karmaPoints;
            instance.progressPercent = this.progressPercent;
            instance.joinedAt = this.joinedAt;
            instance.isSelf = this.isSelf;
            return instance;
        }
    }
}
