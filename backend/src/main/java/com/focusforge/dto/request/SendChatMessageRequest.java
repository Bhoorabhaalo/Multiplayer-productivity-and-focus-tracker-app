package com.focusforge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendChatMessageRequest {


    @NotBlank(message = "Message content is required")
    @Size(min = 1, max = 500, message = "Message must be between 1 and 500 characters")
    private String content;

    public SendChatMessageRequest() {}

    public SendChatMessageRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String content;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public SendChatMessageRequest build() {
            SendChatMessageRequest instance = new SendChatMessageRequest();
            instance.content = this.content;
            return instance;
        }
    }
}
