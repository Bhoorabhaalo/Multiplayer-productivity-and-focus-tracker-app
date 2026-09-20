package com.focusforge.dto.request;

import com.focusforge.entity.RoomMember;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateSessionStateRequest {


    @NotNull(message = "State is required")
    private RoomMember.MemberState state;

    @Size(max = 150)
    private String activity;

    public UpdateSessionStateRequest() {}

    public UpdateSessionStateRequest(RoomMember.MemberState state, String activity) {
        this.state = state;
        this.activity = activity;
    }

    public RoomMember.MemberState getState() {
        return this.state;
    }

    public void setState(RoomMember.MemberState state) {
        this.state = state;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RoomMember.MemberState state;
        private String activity;

        public Builder state(RoomMember.MemberState state) {
            this.state = state;
            return this;
        }

        public Builder activity(String activity) {
            this.activity = activity;
            return this;
        }

        public UpdateSessionStateRequest build() {
            UpdateSessionStateRequest instance = new UpdateSessionStateRequest();
            instance.state = this.state;
            instance.activity = this.activity;
            return instance;
        }
    }
}
