package com.focusforge.dto.response;


public class PodResponse {

    private Long id;
    private String code;
    private String label;
    private String name;

    public PodResponse() {}

    public PodResponse(Long id, String code, String label, String name) {
        this.id = id;
        this.code = code;
        this.label = label;
        this.name = name;
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

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String label;
        private String name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public PodResponse build() {
            PodResponse instance = new PodResponse();
            instance.id = this.id;
            instance.code = this.code;
            instance.label = this.label;
            instance.name = this.name;
            return instance;
        }
    }
}
