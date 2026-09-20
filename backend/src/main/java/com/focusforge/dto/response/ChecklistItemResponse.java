package com.focusforge.dto.response;


import java.time.LocalDateTime;

public class ChecklistItemResponse {

    private Long id;
    private Long roomId;
    private String text;
    private String tag;
    private Boolean done;
    private Long createdById;
    private String createdByName;
    private Long completedById;
    private String completedByName;
    private LocalDateTime createdAt;

    public ChecklistItemResponse() {}

    public ChecklistItemResponse(Long id, Long roomId, String text, String tag, Boolean done, Long createdById, String createdByName, Long completedById, String completedByName, LocalDateTime createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.text = text;
        this.tag = tag;
        this.done = done;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.completedById = completedById;
        this.completedByName = completedByName;
        this.createdAt = createdAt;
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getDone() {
        return this.done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Long getCreatedById() {
        return this.createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return this.createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Long getCompletedById() {
        return this.completedById;
    }

    public void setCompletedById(Long completedById) {
        this.completedById = completedById;
    }

    public String getCompletedByName() {
        return this.completedByName;
    }

    public void setCompletedByName(String completedByName) {
        this.completedByName = completedByName;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long roomId;
        private String text;
        private String tag;
        private Boolean done;
        private Long createdById;
        private String createdByName;
        private Long completedById;
        private String completedByName;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder done(Boolean done) {
            this.done = done;
            return this;
        }

        public Builder createdById(Long createdById) {
            this.createdById = createdById;
            return this;
        }

        public Builder createdByName(String createdByName) {
            this.createdByName = createdByName;
            return this;
        }

        public Builder completedById(Long completedById) {
            this.completedById = completedById;
            return this;
        }

        public Builder completedByName(String completedByName) {
            this.completedByName = completedByName;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ChecklistItemResponse build() {
            ChecklistItemResponse instance = new ChecklistItemResponse();
            instance.id = this.id;
            instance.roomId = this.roomId;
            instance.text = this.text;
            instance.tag = this.tag;
            instance.done = this.done;
            instance.createdById = this.createdById;
            instance.createdByName = this.createdByName;
            instance.completedById = this.completedById;
            instance.completedByName = this.completedByName;
            instance.createdAt = this.createdAt;
            return instance;
        }
    }
}
