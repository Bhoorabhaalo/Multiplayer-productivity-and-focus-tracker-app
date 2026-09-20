package com.focusforge.dto.request;

import jakarta.validation.constraints.NotNull;

public class CompleteSessionRequest {


    @NotNull(message = "Actual focus seconds is required")
    private Integer actualFocusSeconds;

    @NotNull(message = "Cycles completed is required")
    private Integer cyclesCompleted;

    public CompleteSessionRequest() {}

    public CompleteSessionRequest(Integer actualFocusSeconds, Integer cyclesCompleted) {
        this.actualFocusSeconds = actualFocusSeconds;
        this.cyclesCompleted = cyclesCompleted;
    }

    public Integer getActualFocusSeconds() {
        return this.actualFocusSeconds;
    }

    public void setActualFocusSeconds(Integer actualFocusSeconds) {
        this.actualFocusSeconds = actualFocusSeconds;
    }

    public Integer getCyclesCompleted() {
        return this.cyclesCompleted;
    }

    public void setCyclesCompleted(Integer cyclesCompleted) {
        this.cyclesCompleted = cyclesCompleted;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer actualFocusSeconds;
        private Integer cyclesCompleted;

        public Builder actualFocusSeconds(Integer actualFocusSeconds) {
            this.actualFocusSeconds = actualFocusSeconds;
            return this;
        }

        public Builder cyclesCompleted(Integer cyclesCompleted) {
            this.cyclesCompleted = cyclesCompleted;
            return this;
        }

        public CompleteSessionRequest build() {
            CompleteSessionRequest instance = new CompleteSessionRequest();
            instance.actualFocusSeconds = this.actualFocusSeconds;
            instance.cyclesCompleted = this.cyclesCompleted;
            return instance;
        }
    }
}
