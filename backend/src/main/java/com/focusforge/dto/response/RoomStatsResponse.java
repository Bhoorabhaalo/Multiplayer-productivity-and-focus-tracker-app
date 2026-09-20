package com.focusforge.dto.response;


public class RoomStatsResponse {

    private Double focusEfficiency;
    private Integer distractionsLogged;
    private Integer currentCycle;
    private Integer targetCycles;
    private Integer minutesRemaining;
    private Integer karmaEarned;
    private Integer weeklyRank;

    public RoomStatsResponse() {}

    public RoomStatsResponse(Double focusEfficiency, Integer distractionsLogged, Integer currentCycle, Integer targetCycles, Integer minutesRemaining, Integer karmaEarned, Integer weeklyRank) {
        this.focusEfficiency = focusEfficiency;
        this.distractionsLogged = distractionsLogged;
        this.currentCycle = currentCycle;
        this.targetCycles = targetCycles;
        this.minutesRemaining = minutesRemaining;
        this.karmaEarned = karmaEarned;
        this.weeklyRank = weeklyRank;
    }

    public Double getFocusEfficiency() {
        return this.focusEfficiency;
    }

    public void setFocusEfficiency(Double focusEfficiency) {
        this.focusEfficiency = focusEfficiency;
    }

    public Integer getDistractionsLogged() {
        return this.distractionsLogged;
    }

    public void setDistractionsLogged(Integer distractionsLogged) {
        this.distractionsLogged = distractionsLogged;
    }

    public Integer getCurrentCycle() {
        return this.currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
    }

    public Integer getTargetCycles() {
        return this.targetCycles;
    }

    public void setTargetCycles(Integer targetCycles) {
        this.targetCycles = targetCycles;
    }

    public Integer getMinutesRemaining() {
        return this.minutesRemaining;
    }

    public void setMinutesRemaining(Integer minutesRemaining) {
        this.minutesRemaining = minutesRemaining;
    }

    public Integer getKarmaEarned() {
        return this.karmaEarned;
    }

    public void setKarmaEarned(Integer karmaEarned) {
        this.karmaEarned = karmaEarned;
    }

    public Integer getWeeklyRank() {
        return this.weeklyRank;
    }

    public void setWeeklyRank(Integer weeklyRank) {
        this.weeklyRank = weeklyRank;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double focusEfficiency;
        private Integer distractionsLogged;
        private Integer currentCycle;
        private Integer targetCycles;
        private Integer minutesRemaining;
        private Integer karmaEarned;
        private Integer weeklyRank;

        public Builder focusEfficiency(Double focusEfficiency) {
            this.focusEfficiency = focusEfficiency;
            return this;
        }

        public Builder distractionsLogged(Integer distractionsLogged) {
            this.distractionsLogged = distractionsLogged;
            return this;
        }

        public Builder currentCycle(Integer currentCycle) {
            this.currentCycle = currentCycle;
            return this;
        }

        public Builder targetCycles(Integer targetCycles) {
            this.targetCycles = targetCycles;
            return this;
        }

        public Builder minutesRemaining(Integer minutesRemaining) {
            this.minutesRemaining = minutesRemaining;
            return this;
        }

        public Builder karmaEarned(Integer karmaEarned) {
            this.karmaEarned = karmaEarned;
            return this;
        }

        public Builder weeklyRank(Integer weeklyRank) {
            this.weeklyRank = weeklyRank;
            return this;
        }

        public RoomStatsResponse build() {
            RoomStatsResponse instance = new RoomStatsResponse();
            instance.focusEfficiency = this.focusEfficiency;
            instance.distractionsLogged = this.distractionsLogged;
            instance.currentCycle = this.currentCycle;
            instance.targetCycles = this.targetCycles;
            instance.minutesRemaining = this.minutesRemaining;
            instance.karmaEarned = this.karmaEarned;
            instance.weeklyRank = this.weeklyRank;
            return instance;
        }
    }
}
