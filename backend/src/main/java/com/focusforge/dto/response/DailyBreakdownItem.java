package com.focusforge.dto.response;


public class DailyBreakdownItem {

    private String day; // MON, TUE, WED, THU, FRI, SAT, SUN
    private Double hours;
    private Boolean isCurrentDay;

    public DailyBreakdownItem() {}

    public DailyBreakdownItem(String day, Double hours, Boolean isCurrentDay) {
        this.day = day;
        this.hours = hours;
        this.isCurrentDay = isCurrentDay;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getHours() {
        return this.hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Boolean isCurrentDay() {
        return this.isCurrentDay;
    }

    public void setIsCurrentDay(Boolean isCurrentDay) {
        this.isCurrentDay = isCurrentDay;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String day;
        private Double hours;
        private Boolean isCurrentDay;

        public Builder day(String day) {
            this.day = day;
            return this;
        }

        public Builder hours(Double hours) {
            this.hours = hours;
            return this;
        }

        public Builder isCurrentDay(Boolean isCurrentDay) {
            this.isCurrentDay = isCurrentDay;
            return this;
        }

        public DailyBreakdownItem build() {
            DailyBreakdownItem instance = new DailyBreakdownItem();
            instance.day = this.day;
            instance.hours = this.hours;
            instance.isCurrentDay = this.isCurrentDay;
            return instance;
        }
    }
}
