package com.focusforge.dto.response;


import java.util.List;

public class AnalyticsResponse {

    private String totalFocusTime; // e.g. "38h 45m"
    private Integer totalFocusMinutes;
    private Integer completedSessions;
    private Double completionRate; // e.g. 97.8
    private Double percentChangeVsLastPeriod; // e.g. +18.5
    private List<DailyBreakdownItem> dailyBreakdown;
    private Double dailyAverage; // e.g. 5.5
    private String insight;
    private String synergyLabel; // "High Synergy", "Moderate Synergy", "Low Synergy"
    private List<SessionResponse> pastSessions;

    public AnalyticsResponse() {}

    public AnalyticsResponse(String totalFocusTime, Integer totalFocusMinutes, Integer completedSessions, Double completionRate, Double percentChangeVsLastPeriod, List<DailyBreakdownItem> dailyBreakdown, Double dailyAverage, String insight, String synergyLabel, List<SessionResponse> pastSessions) {
        this.totalFocusTime = totalFocusTime;
        this.totalFocusMinutes = totalFocusMinutes;
        this.completedSessions = completedSessions;
        this.completionRate = completionRate;
        this.percentChangeVsLastPeriod = percentChangeVsLastPeriod;
        this.dailyBreakdown = dailyBreakdown;
        this.dailyAverage = dailyAverage;
        this.insight = insight;
        this.synergyLabel = synergyLabel;
        this.pastSessions = pastSessions;
    }

    public String getTotalFocusTime() {
        return this.totalFocusTime;
    }

    public void setTotalFocusTime(String totalFocusTime) {
        this.totalFocusTime = totalFocusTime;
    }

    public Integer getTotalFocusMinutes() {
        return this.totalFocusMinutes;
    }

    public void setTotalFocusMinutes(Integer totalFocusMinutes) {
        this.totalFocusMinutes = totalFocusMinutes;
    }

    public Integer getCompletedSessions() {
        return this.completedSessions;
    }

    public void setCompletedSessions(Integer completedSessions) {
        this.completedSessions = completedSessions;
    }

    public Double getCompletionRate() {
        return this.completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getPercentChangeVsLastPeriod() {
        return this.percentChangeVsLastPeriod;
    }

    public void setPercentChangeVsLastPeriod(Double percentChangeVsLastPeriod) {
        this.percentChangeVsLastPeriod = percentChangeVsLastPeriod;
    }

    public List<DailyBreakdownItem> getDailyBreakdown() {
        return this.dailyBreakdown;
    }

    public void setDailyBreakdown(List<DailyBreakdownItem> dailyBreakdown) {
        this.dailyBreakdown = dailyBreakdown;
    }

    public Double getDailyAverage() {
        return this.dailyAverage;
    }

    public void setDailyAverage(Double dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public String getInsight() {
        return this.insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public String getSynergyLabel() {
        return this.synergyLabel;
    }

    public void setSynergyLabel(String synergyLabel) {
        this.synergyLabel = synergyLabel;
    }

    public List<SessionResponse> getPastSessions() {
        return this.pastSessions;
    }

    public void setPastSessions(List<SessionResponse> pastSessions) {
        this.pastSessions = pastSessions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String totalFocusTime;
        private Integer totalFocusMinutes;
        private Integer completedSessions;
        private Double completionRate;
        private Double percentChangeVsLastPeriod;
        private List<DailyBreakdownItem> dailyBreakdown;
        private Double dailyAverage;
        private String insight;
        private String synergyLabel;
        private List<SessionResponse> pastSessions;

        public Builder totalFocusTime(String totalFocusTime) {
            this.totalFocusTime = totalFocusTime;
            return this;
        }

        public Builder totalFocusMinutes(Integer totalFocusMinutes) {
            this.totalFocusMinutes = totalFocusMinutes;
            return this;
        }

        public Builder completedSessions(Integer completedSessions) {
            this.completedSessions = completedSessions;
            return this;
        }

        public Builder completionRate(Double completionRate) {
            this.completionRate = completionRate;
            return this;
        }

        public Builder percentChangeVsLastPeriod(Double percentChangeVsLastPeriod) {
            this.percentChangeVsLastPeriod = percentChangeVsLastPeriod;
            return this;
        }

        public Builder dailyBreakdown(List<DailyBreakdownItem> dailyBreakdown) {
            this.dailyBreakdown = dailyBreakdown;
            return this;
        }

        public Builder dailyAverage(Double dailyAverage) {
            this.dailyAverage = dailyAverage;
            return this;
        }

        public Builder insight(String insight) {
            this.insight = insight;
            return this;
        }

        public Builder synergyLabel(String synergyLabel) {
            this.synergyLabel = synergyLabel;
            return this;
        }

        public Builder pastSessions(List<SessionResponse> pastSessions) {
            this.pastSessions = pastSessions;
            return this;
        }

        public AnalyticsResponse build() {
            AnalyticsResponse instance = new AnalyticsResponse();
            instance.totalFocusTime = this.totalFocusTime;
            instance.totalFocusMinutes = this.totalFocusMinutes;
            instance.completedSessions = this.completedSessions;
            instance.completionRate = this.completionRate;
            instance.percentChangeVsLastPeriod = this.percentChangeVsLastPeriod;
            instance.dailyBreakdown = this.dailyBreakdown;
            instance.dailyAverage = this.dailyAverage;
            instance.insight = this.insight;
            instance.synergyLabel = this.synergyLabel;
            instance.pastSessions = this.pastSessions;
            return instance;
        }
    }
}
