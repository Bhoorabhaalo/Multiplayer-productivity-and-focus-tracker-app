package com.focusforge.dto.response;


import java.time.LocalDateTime;

public class SprintTargetResponse {

    private Long id;
    private Long roomId;
    private String title;
    private String description;
    private Integer stepCurrent;
    private Integer stepTotal;
    private String tag;
    private Long createdById;
    private String createdByName;
    private Integer minutesOnTask;
    private LocalDateTime createdAt;

    public SprintTargetResponse() {}

    public SprintTargetResponse(Long id, Long roomId, String title, String description, Integer stepCurrent, Integer stepTotal, String tag, Long createdById, String createdByName, Integer minutesOnTask, LocalDateTime createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.title = title;
        this.description = description;
        this.stepCurrent = stepCurrent;
        this.stepTotal = stepTotal;
        this.tag = tag;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.minutesOnTask = minutesOnTask;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStepCurrent() {
        return this.stepCurrent;
    }

    public void setStepCurrent(Integer stepCurrent) {
        this.stepCurrent = stepCurrent;
    }

    public Integer getStepTotal() {
        return this.stepTotal;
    }

    public void setStepTotal(Integer stepTotal) {
        this.stepTotal = stepTotal;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Integer getMinutesOnTask() {
        return this.minutesOnTask;
    }

    public void setMinutesOnTask(Integer minutesOnTask) {
        this.minutesOnTask = minutesOnTask;
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
        private String title;
        private String description;
        private Integer stepCurrent;
        private Integer stepTotal;
        private String tag;
        private Long createdById;
        private String createdByName;
        private Integer minutesOnTask;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder stepCurrent(Integer stepCurrent) {
            this.stepCurrent = stepCurrent;
            return this;
        }

        public Builder stepTotal(Integer stepTotal) {
            this.stepTotal = stepTotal;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
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

        public Builder minutesOnTask(Integer minutesOnTask) {
            this.minutesOnTask = minutesOnTask;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SprintTargetResponse build() {
            SprintTargetResponse instance = new SprintTargetResponse();
            instance.id = this.id;
            instance.roomId = this.roomId;
            instance.title = this.title;
            instance.description = this.description;
            instance.stepCurrent = this.stepCurrent;
            instance.stepTotal = this.stepTotal;
            instance.tag = this.tag;
            instance.createdById = this.createdById;
            instance.createdByName = this.createdByName;
            instance.minutesOnTask = this.minutesOnTask;
            instance.createdAt = this.createdAt;
            return instance;
        }
    }
}
