package com.focusforge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JoinRoomRequest {


    @NotBlank(message = "Room code is required")
    @Size(min = 6, max = 6, message = "Room code must be exactly 6 characters")
    private String code;

    public JoinRoomRequest() {}

    public JoinRoomRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public JoinRoomRequest build() {
            JoinRoomRequest instance = new JoinRoomRequest();
            instance.code = this.code;
            return instance;
        }
    }
}
