package com.focusforge.dto.response;


public class TaskSummaryResponse {

    private long activeCount;
    private long completedCount;
    private int xpEarnedToday;

    public TaskSummaryResponse() {}

    public TaskSummaryResponse(long activeCount, long completedCount, int xpEarnedToday) {
        this.activeCount = activeCount;
        this.completedCount = completedCount;
        this.xpEarnedToday = xpEarnedToday;
    }

    public long getActiveCount() {
        return this.activeCount;
    }

    public void setActiveCount(long activeCount) {
        this.activeCount = activeCount;
    }

    public long getCompletedCount() {
        return this.completedCount;
    }

    public void setCompletedCount(long completedCount) {
        this.completedCount = completedCount;
    }

    public int getXpEarnedToday() {
        return this.xpEarnedToday;
    }

    public void setXpEarnedToday(int xpEarnedToday) {
        this.xpEarnedToday = xpEarnedToday;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long activeCount;
        private long completedCount;
        private int xpEarnedToday;

        public Builder activeCount(long activeCount) {
            this.activeCount = activeCount;
            return this;
        }

        public Builder completedCount(long completedCount) {
            this.completedCount = completedCount;
            return this;
        }

        public Builder xpEarnedToday(int xpEarnedToday) {
            this.xpEarnedToday = xpEarnedToday;
            return this;
        }

        public TaskSummaryResponse build() {
            TaskSummaryResponse instance = new TaskSummaryResponse();
            instance.activeCount = this.activeCount;
            instance.completedCount = this.completedCount;
            instance.xpEarnedToday = this.xpEarnedToday;
            return instance;
        }
    }
}
