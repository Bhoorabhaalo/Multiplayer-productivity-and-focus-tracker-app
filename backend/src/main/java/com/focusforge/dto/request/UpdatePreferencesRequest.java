package com.focusforge.dto.request;

import com.focusforge.entity.User;

public class UpdatePreferencesRequest {

    private User.InterfaceDensity interfaceDensity;
    private Boolean soundEffectsEnabled;
    private Boolean compactSidebar;

    public UpdatePreferencesRequest() {}

    public UpdatePreferencesRequest(User.InterfaceDensity interfaceDensity, Boolean soundEffectsEnabled, Boolean compactSidebar) {
        this.interfaceDensity = interfaceDensity;
        this.soundEffectsEnabled = soundEffectsEnabled;
        this.compactSidebar = compactSidebar;
    }

    public User.InterfaceDensity getInterfaceDensity() {
        return this.interfaceDensity;
    }

    public void setInterfaceDensity(User.InterfaceDensity interfaceDensity) {
        this.interfaceDensity = interfaceDensity;
    }

    public Boolean getSoundEffectsEnabled() {
        return this.soundEffectsEnabled;
    }

    public void setSoundEffectsEnabled(Boolean soundEffectsEnabled) {
        this.soundEffectsEnabled = soundEffectsEnabled;
    }

    public Boolean getCompactSidebar() {
        return this.compactSidebar;
    }

    public void setCompactSidebar(Boolean compactSidebar) {
        this.compactSidebar = compactSidebar;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User.InterfaceDensity interfaceDensity;
        private Boolean soundEffectsEnabled;
        private Boolean compactSidebar;

        public Builder interfaceDensity(User.InterfaceDensity interfaceDensity) {
            this.interfaceDensity = interfaceDensity;
            return this;
        }

        public Builder soundEffectsEnabled(Boolean soundEffectsEnabled) {
            this.soundEffectsEnabled = soundEffectsEnabled;
            return this;
        }

        public Builder compactSidebar(Boolean compactSidebar) {
            this.compactSidebar = compactSidebar;
            return this;
        }

        public UpdatePreferencesRequest build() {
            UpdatePreferencesRequest instance = new UpdatePreferencesRequest();
            instance.interfaceDensity = this.interfaceDensity;
            instance.soundEffectsEnabled = this.soundEffectsEnabled;
            instance.compactSidebar = this.compactSidebar;
            return instance;
        }
    }
}
