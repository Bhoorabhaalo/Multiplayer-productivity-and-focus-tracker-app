package com.focusforge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateSprintTargetRequest {


    @NotBlank(message = "Title is required")
    @Size(max = 200)
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull
    private Integer stepCurrent;

    @NotNull
    private Integer stepTotal;

    @Size(max = 50)
    private String tag;

    public UpdateSprintTargetRequest() {}

    public UpdateSprintTargetRequest(String title, String description, Integer stepCurrent, Integer stepTotal, String tag) {
        this.title = title;
        this.description = description;
        this.stepCurrent = stepCurrent;
        this.stepTotal = stepTotal;
        this.tag = tag;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;
        private Integer stepCurrent;
        private Integer stepTotal;
        private String tag;

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

        public UpdateSprintTargetRequest build() {
            UpdateSprintTargetRequest instance = new UpdateSprintTargetRequest();
            instance.title = this.title;
            instance.description = this.description;
            instance.stepCurrent = this.stepCurrent;
            instance.stepTotal = this.stepTotal;
            instance.tag = this.tag;
            return instance;
        }
    }
}
