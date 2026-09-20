package com.focusforge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateChecklistItemRequest {


    @NotBlank(message = "Item text is required")
    @Size(max = 255)
    private String text;

    @Size(max = 30)
    private String tag;

    public CreateChecklistItemRequest() {}

    public CreateChecklistItemRequest(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String text;
        private String tag;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public CreateChecklistItemRequest build() {
            CreateChecklistItemRequest instance = new CreateChecklistItemRequest();
            instance.text = this.text;
            instance.tag = this.tag;
            return instance;
        }
    }
}
