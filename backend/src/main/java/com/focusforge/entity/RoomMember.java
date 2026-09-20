package com.focusforge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_members", indexes = {
    @Index(name = "idx_room_members_room_user", columnList = "room_id, user_id"),
    @Index(name = "idx_room_members_left_at", columnList = "left_at")
})
public class RoomMember {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private MemberRole role = MemberRole.MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private MemberState state = MemberState.FOCUS;

    @Column(name = "current_activity", length = 150)
    private String currentActivity;

        @Column(name = "focus_seconds", nullable = false)
    private Integer focusSeconds = 0;

        @Column(name = "karma_points", nullable = false)
    private Integer karmaPoints = 0;

    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    @Column(name = "last_heartbeat_at")
    private LocalDateTime lastHeartbeatAt;

    public enum MemberRole {
        HOST, MEMBER
    }

    public enum MemberState {
        FOCUS, IDLE, BREAK
    }

    public RoomMember() {}

    public RoomMember(Long id, Room room, User user, MemberRole role, MemberState state, String currentActivity, Integer focusSeconds, Integer karmaPoints, LocalDateTime joinedAt, LocalDateTime leftAt, LocalDateTime lastHeartbeatAt) {
        this.id = id;
        this.room = room;
        this.user = user;
        this.role = role;
        this.state = state;
        this.currentActivity = currentActivity;
        this.focusSeconds = focusSeconds;
        this.karmaPoints = karmaPoints;
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
        this.lastHeartbeatAt = lastHeartbeatAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MemberRole getRole() {
        return this.role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public MemberState getState() {
        return this.state;
    }

    public void setState(MemberState state) {
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

    public Integer getKarmaPoints() {
        return this.karmaPoints;
    }

    public void setKarmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    public LocalDateTime getJoinedAt() {
        return this.joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return this.leftAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public LocalDateTime getLastHeartbeatAt() {
        return this.lastHeartbeatAt;
    }

    public void setLastHeartbeatAt(LocalDateTime lastHeartbeatAt) {
        this.lastHeartbeatAt = lastHeartbeatAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Room room;
        private User user;
        private MemberRole role = MemberRole.MEMBER;
        private MemberState state = MemberState.FOCUS;
        private String currentActivity;
        private Integer focusSeconds = 0;
        private Integer karmaPoints = 0;
        private LocalDateTime joinedAt;
        private LocalDateTime leftAt;
        private LocalDateTime lastHeartbeatAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder role(MemberRole role) {
            this.role = role;
            return this;
        }

        public Builder state(MemberState state) {
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

        public Builder karmaPoints(Integer karmaPoints) {
            this.karmaPoints = karmaPoints;
            return this;
        }

        public Builder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public Builder leftAt(LocalDateTime leftAt) {
            this.leftAt = leftAt;
            return this;
        }

        public Builder lastHeartbeatAt(LocalDateTime lastHeartbeatAt) {
            this.lastHeartbeatAt = lastHeartbeatAt;
            return this;
        }

        public RoomMember build() {
            RoomMember instance = new RoomMember();
            instance.id = this.id;
            instance.room = this.room;
            instance.user = this.user;
            instance.role = this.role;
            instance.state = this.state;
            instance.currentActivity = this.currentActivity;
            instance.focusSeconds = this.focusSeconds;
            instance.karmaPoints = this.karmaPoints;
            instance.joinedAt = this.joinedAt;
            instance.leftAt = this.leftAt;
            instance.lastHeartbeatAt = this.lastHeartbeatAt;
            return instance;
        }
    }
}
