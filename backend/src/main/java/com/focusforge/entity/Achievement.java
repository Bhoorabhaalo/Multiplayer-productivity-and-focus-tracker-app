package com.focusforge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "achievements", indexes = {
    @Index(name = "idx_achievements_code", columnList = "code")
})
public class Achievement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
        private Tier tier = Tier.NONE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 35)
    private Metric metric;

    @Column(name = "target_value", nullable = false)
    private Integer targetValue;

    @Column(name = "xp_reward", nullable = false)
    private Integer xpReward;

    @Column(length = 40)
    private String icon;

    public enum Tier {
        BRONZE, SILVER, GOLD, NONE
    }

    public enum Metric {
        TOTAL_FOCUS_HOURS,
        STREAK_DAYS,
        ROOMS_HOSTED,
        MIDNIGHT_SESSIONS,
        LONGEST_SESSION_MINUTES,
        SESSIONS_COMPLETED
    }

    public Achievement() {}

    public Achievement(Long id, String code, String name, String description, Tier tier, Metric metric, Integer targetValue, Integer xpReward, String icon) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.tier = tier;
        this.metric = metric;
        this.targetValue = targetValue;
        this.xpReward = xpReward;
        this.icon = icon;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tier getTier() {
        return this.tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Metric getMetric() {
        return this.metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Integer getTargetValue() {
        return this.targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getXpReward() {
        return this.xpReward;
    }

    public void setXpReward(Integer xpReward) {
        this.xpReward = xpReward;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String name;
        private String description;
        private Tier tier = Tier.NONE;
        private Metric metric;
        private Integer targetValue;
        private Integer xpReward;
        private String icon;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder tier(Tier tier) {
            this.tier = tier;
            return this;
        }

        public Builder metric(Metric metric) {
            this.metric = metric;
            return this;
        }

        public Builder targetValue(Integer targetValue) {
            this.targetValue = targetValue;
            return this;
        }

        public Builder xpReward(Integer xpReward) {
            this.xpReward = xpReward;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Achievement build() {
            Achievement instance = new Achievement();
            instance.id = this.id;
            instance.code = this.code;
            instance.name = this.name;
            instance.description = this.description;
            instance.tier = this.tier;
            instance.metric = this.metric;
            instance.targetValue = this.targetValue;
            instance.xpReward = this.xpReward;
            instance.icon = this.icon;
            return instance;
        }
    }
}
