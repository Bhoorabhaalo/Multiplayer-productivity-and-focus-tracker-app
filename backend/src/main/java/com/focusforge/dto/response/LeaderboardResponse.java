package com.focusforge.dto.response;


import java.util.List;

public class LeaderboardResponse {

    private String period; // THIS_WEEK or ALL_TIME
    private List<LeaderboardEntry> podium; // Top 3
    private List<LeaderboardEntry> standings; // All ranked
    private LeaderboardEntry userStanding;

    public LeaderboardResponse() {}

    public LeaderboardResponse(String period, List<LeaderboardEntry> podium, List<LeaderboardEntry> standings, LeaderboardEntry userStanding) {
        this.period = period;
        this.podium = podium;
        this.standings = standings;
        this.userStanding = userStanding;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<LeaderboardEntry> getPodium() {
        return this.podium;
    }

    public void setPodium(List<LeaderboardEntry> podium) {
        this.podium = podium;
    }

    public List<LeaderboardEntry> getStandings() {
        return this.standings;
    }

    public void setStandings(List<LeaderboardEntry> standings) {
        this.standings = standings;
    }

    public LeaderboardEntry getUserStanding() {
        return this.userStanding;
    }

    public void setUserStanding(LeaderboardEntry userStanding) {
        this.userStanding = userStanding;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String period;
        private List<LeaderboardEntry> podium;
        private List<LeaderboardEntry> standings;
        private LeaderboardEntry userStanding;

        public Builder period(String period) {
            this.period = period;
            return this;
        }

        public Builder podium(List<LeaderboardEntry> podium) {
            this.podium = podium;
            return this;
        }

        public Builder standings(List<LeaderboardEntry> standings) {
            this.standings = standings;
            return this;
        }

        public Builder userStanding(LeaderboardEntry userStanding) {
            this.userStanding = userStanding;
            return this;
        }

        public LeaderboardResponse build() {
            LeaderboardResponse instance = new LeaderboardResponse();
            instance.period = this.period;
            instance.podium = this.podium;
            instance.standings = this.standings;
            instance.userStanding = this.userStanding;
            return instance;
        }
    }
}
