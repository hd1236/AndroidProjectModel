package com.hand.android.common.network.frame;

public class NetworkResponse {
    private int code;
    private String content;

    public NetworkResponse(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public boolean isSuccessful() {
        return this.code >= 200 && this.code < 300;
    }

    public boolean isServiceError() {
        return this.code >= 400 && this.code < 499;
    }

    public int getCode() {
        return this.code;
    }

    public String getContent() {
        return this.content;
    }

    public static class Builder {
        private int code;
        private String content;

        public Builder() {
        }

        public NetworkResponse.Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public NetworkResponse.Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public NetworkResponse build() {
            return new NetworkResponse(this.code, this.content);
        }
    }
}
