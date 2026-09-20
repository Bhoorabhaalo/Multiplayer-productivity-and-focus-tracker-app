package com.focusforge.dto.response;


import java.time.LocalDateTime;

public class ChatMessageResponse {

    private Long id;
    private Long roomId;
    private String roomCode;
    private Long userId;
    private String username;
    private String displayName;
    private String avatarUrl;
    private String content;
    private LocalDateTime sentAt;

    public ChatMessageResponse() {}

    public ChatMessageResponse(Long id, Long roomId, String roomCode, Long userId, String username, String displayName, String avatarUrl, String content, LocalDateTime sentAt) {
        this.id = id;
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return this.sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long roomId;
        private String roomCode;
        private Long userId;
        private String username;
        private String displayName;
        private String avatarUrl;
        private String content;
        private LocalDateTime sentAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder roomCode(String roomCode) {
            this.roomCode = roomCode;
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

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder sentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public ChatMessageResponse build() {
            ChatMessageResponse instance = new ChatMessageResponse();
            instance.id = this.id;
            instance.roomId = this.roomId;
            instance.roomCode = this.roomCode;
            instance.userId = this.userId;
            instance.username = this.username;
            instance.displayName = this.displayName;
            instance.avatarUrl = this.avatarUrl;
            instance.content = this.content;
            instance.sentAt = this.sentAt;
            return instance;
        }
    }
}
