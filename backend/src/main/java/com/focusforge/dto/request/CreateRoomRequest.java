package com.focusforge.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRoomRequest {


    @NotBlank(message = "Room name is required")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private Long podId;

    @Min(value = 5, message = "Focus minutes must be between 5 and 120")
    @Max(value = 120, message = "Focus minutes must be between 5 and 120")
    private Integer focusMinutes = 25;

    @Min(value = 1, message = "Break minutes must be between 1 and 30")
    @Max(value = 30, message = "Break minutes must be between 1 and 30")
    private Integer breakMinutes = 5;

    @Min(value = 1, message = "Target cycles must be between 1 and 12")
    @Max(value = 12, message = "Target cycles must be between 1 and 12")
    private Integer targetCycles = 4;

    @Min(value = 2, message = "Max members must be at least 2")
    @Max(value = 50, message = "Max members cannot exceed 50")
    private Integer maxMembers = 10;

    public CreateRoomRequest() {}

    public CreateRoomRequest(String name, String description, Long podId, Integer focusMinutes, Integer breakMinutes, Integer targetCycles, Integer maxMembers) {
        this.name = name;
        this.description = description;
        this.podId = podId;
        this.focusMinutes = focusMinutes;
        this.breakMinutes = breakMinutes;
        this.targetCycles = targetCycles;
        this.maxMembers = maxMembers;
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

    public Long getPodId() {
        return this.podId;
    }

    public void setPodId(Long podId) {
        this.podId = podId;
    }

    public Integer getFocusMinutes() {
        return this.focusMinutes;
    }

    public void setFocusMinutes(Integer focusMinutes) {
        this.focusMinutes = focusMinutes;
    }

    public Integer getBreakMinutes() {
        return this.breakMinutes;
    }

    public void setBreakMinutes(Integer breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    public Integer getTargetCycles() {
        return this.targetCycles;
    }

    public void setTargetCycles(Integer targetCycles) {
        this.targetCycles = targetCycles;
    }

    public Integer getMaxMembers() {
        return this.maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private Long podId;
        private Integer focusMinutes = 25;
        private Integer breakMinutes = 5;
        private Integer targetCycles = 4;
        private Integer maxMembers = 10;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder podId(Long podId) {
            this.podId = podId;
            return this;
        }

        public Builder focusMinutes(Integer focusMinutes) {
            this.focusMinutes = focusMinutes;
            return this;
        }

        public Builder breakMinutes(Integer breakMinutes) {
            this.breakMinutes = breakMinutes;
            return this;
        }

        public Builder targetCycles(Integer targetCycles) {
            this.targetCycles = targetCycles;
            return this;
        }

        public Builder maxMembers(Integer maxMembers) {
            this.maxMembers = maxMembers;
            return this;
        }

        public CreateRoomRequest build() {
            CreateRoomRequest instance = new CreateRoomRequest();
            instance.name = this.name;
            instance.description = this.description;
            instance.podId = this.podId;
            instance.focusMinutes = this.focusMinutes;
            instance.breakMinutes = this.breakMinutes;
            instance.targetCycles = this.targetCycles;
            instance.maxMembers = this.maxMembers;
            return instance;
        }
    }
}
