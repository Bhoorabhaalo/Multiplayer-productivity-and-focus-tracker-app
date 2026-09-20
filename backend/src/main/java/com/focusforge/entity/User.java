package com.focusforge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_users_email", columnList = "email"),
    @Index(name = "idx_users_username", columnList = "username")
})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "display_name", nullable = false, length = 80)
    private String displayName;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(length = 150)
    private String university;

    @Column(length = 150)
    private String major;

    @Column(name = "focus_statement", length = 500)
    private String focusStatement;

    @Column(name = "sprint_status", length = 120)
    private String sprintStatus;

        @Column(nullable = false)
    private Integer level = 1;

        @Column(name = "total_xp", nullable = false)
    private Integer totalXp = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "interface_density", nullable = false, length = 20)
        private InterfaceDensity interfaceDensity = InterfaceDensity.BALANCED;

        @Column(name = "sound_effects_enabled", nullable = false)
    private Boolean soundEffectsEnabled = true;

        @Column(name = "compact_sidebar", nullable = false)
    private Boolean compactSidebar = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private Role role = Role.USER;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum InterfaceDensity {
        COMPACT, BALANCED, SPACIOUS
    }

    public enum Role {
        USER, ADMIN
    }

    public User() {}

    public User(Long id, String email, String username, String displayName, String passwordHash, String avatarUrl, String university, String major, String focusStatement, String sprintStatus, Integer level, Integer totalXp, InterfaceDensity interfaceDensity, Boolean soundEffectsEnabled, Boolean compactSidebar, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.avatarUrl = avatarUrl;
        this.university = university;
        this.major = major;
        this.focusStatement = focusStatement;
        this.sprintStatus = sprintStatus;
        this.level = level;
        this.totalXp = totalXp;
        this.interfaceDensity = interfaceDensity;
        this.soundEffectsEnabled = soundEffectsEnabled;
        this.compactSidebar = compactSidebar;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public Integer getTotalXp() {
        return this.totalXp;
    }

    public void setTotalXp(Integer totalXp) {
        this.totalXp = totalXp;
    }

    public InterfaceDensity getInterfaceDensity() {
        return this.interfaceDensity;
    }

    public void setInterfaceDensity(InterfaceDensity interfaceDensity) {
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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email;
        private String username;
        private String displayName;
        private String passwordHash;
        private String avatarUrl;
        private String university;
        private String major;
        private String focusStatement;
        private String sprintStatus;
        private Integer level = 1;
        private Integer totalXp = 0;
        private InterfaceDensity interfaceDensity = InterfaceDensity.BALANCED;
        private Boolean soundEffectsEnabled = true;
        private Boolean compactSidebar = false;
        private Role role = Role.USER;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

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

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
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

        public Builder totalXp(Integer totalXp) {
            this.totalXp = totalXp;
            return this;
        }

        public Builder interfaceDensity(InterfaceDensity interfaceDensity) {
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

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            User instance = new User();
            instance.id = this.id;
            instance.email = this.email;
            instance.username = this.username;
            instance.displayName = this.displayName;
            instance.passwordHash = this.passwordHash;
            instance.avatarUrl = this.avatarUrl;
            instance.university = this.university;
            instance.major = this.major;
            instance.focusStatement = this.focusStatement;
            instance.sprintStatus = this.sprintStatus;
            instance.level = this.level;
            instance.totalXp = this.totalXp;
            instance.interfaceDensity = this.interfaceDensity;
            instance.soundEffectsEnabled = this.soundEffectsEnabled;
            instance.compactSidebar = this.compactSidebar;
            instance.role = this.role;
            instance.createdAt = this.createdAt;
            instance.updatedAt = this.updatedAt;
            return instance;
        }
    }
}
