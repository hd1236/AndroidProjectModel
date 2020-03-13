package com.hand.android.common.network.frame;

import java.util.Map;

public class NetworkOption {

    private int mMethod;
    private String mHost;
    private Map<String, String> mUrlParameters;
    private Map<String, String> mHeaders;

    public NetworkOption(int mMethod, String mHost, Map<String, String> mUrlParameters, Map<String, String> mHeaders) {
        this.mMethod = mMethod;
        this.mHost = mHost;
        this.mUrlParameters = mUrlParameters;
        this.mHeaders = mHeaders;
    }

    public int getmMethod() {
        return this.mMethod;
    }

    public String getmHost() {
        return this.mHost;
    }

    public Map<String, String> getmUrlParameters() {
        return this.mUrlParameters;
    }

    public Map<String, String> getmHeaders() {
        return this.mHeaders;
    }

    public static final class Builder {
        private int mMethod = 1;
        private String mHost;
        private Map<String, String> mUrlParameters;
        private Map<String, String> mHeaders;

        public Builder() {
        }

        public NetworkOption.Builder setMethod(int mMethod) {
            this.mMethod = mMethod;
            return this;
        }

        public NetworkOption.Builder setHost(String mHost) {
            this.mHost = mHost;
            return this;
        }

        public NetworkOption.Builder setUrlParams(Map<String, String> mUrlParameters) {
            this.mUrlParameters = mUrlParameters;
            return this;
        }

        public NetworkOption.Builder setHeaders(Map<String, String> mHeaders) {
            this.mHeaders = mHeaders;
            return this;
        }

        public NetworkOption build() {
            return new NetworkOption(this.mMethod, this.mHost, this.mUrlParameters, this.mHeaders);
        }
    }

    public interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
}
