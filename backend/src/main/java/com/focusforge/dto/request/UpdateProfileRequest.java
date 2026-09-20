package com.focusforge.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {


    @Size(max = 80)
    private String displayName;

    @Size(max = 150)
    private String university;

    @Size(max = 150)
    private String major;

    @Size(max = 500)
    private String focusStatement;

    @Size(max = 120)
    private String sprintStatus;

    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String displayName, String university, String major, String focusStatement, String sprintStatus) {
        this.displayName = displayName;
        this.university = university;
        this.major = major;
        this.focusStatement = focusStatement;
        this.sprintStatus = sprintStatus;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String displayName;
        private String university;
        private String major;
        private String focusStatement;
        private String sprintStatus;

        public Builder displayName(String displayName) {
            this.displayName = displayName;
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

        public UpdateProfileRequest build() {
            UpdateProfileRequest instance = new UpdateProfileRequest();
            instance.displayName = this.displayName;
            instance.university = this.university;
            instance.major = this.major;
            instance.focusStatement = this.focusStatement;
            instance.sprintStatus = this.sprintStatus;
            return instance;
        }
    }
}
