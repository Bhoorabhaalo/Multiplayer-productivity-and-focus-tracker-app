package com.focusforge.dto.request;


public class TimerActionRequest {

    private Integer minutes = 5;

    public TimerActionRequest() {}

    public TimerActionRequest(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getMinutes() {
        return this.minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer minutes = 5;

        public Builder minutes(Integer minutes) {
            this.minutes = minutes;
            return this;
        }

        public TimerActionRequest build() {
            TimerActionRequest instance = new TimerActionRequest();
            instance.minutes = this.minutes;
            return instance;
        }
    }
}
