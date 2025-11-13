package com.falcon.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from an ad serving request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdResponse {
    private Banner banner;

    @JsonProperty("suppression_info")
    private SuppressionInfo suppressionInfo;

    private String message;

    // Constructors
    public AdResponse() {
    }

    public AdResponse(Banner banner, SuppressionInfo suppressionInfo) {
        this.banner = banner;
        this.suppressionInfo = suppressionInfo;
    }

    public AdResponse(String message) {
        this.message = message;
    }

    // Getters and Setters
    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public SuppressionInfo getSuppressionInfo() {
        return suppressionInfo;
    }

    public void setSuppressionInfo(SuppressionInfo suppressionInfo) {
        this.suppressionInfo = suppressionInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Information about suppression checking for this ad request.
     */
    public static class SuppressionInfo {
        @JsonProperty("suppressed_advertisers")
        private java.util.Set<String> suppressedAdvertisers;

        @JsonProperty("check_time_ms")
        private long checkTimeMs;

        public SuppressionInfo() {
        }

        public SuppressionInfo(java.util.Set<String> suppressedAdvertisers, long checkTimeMs) {
            this.suppressedAdvertisers = suppressedAdvertisers;
            this.checkTimeMs = checkTimeMs;
        }

        public java.util.Set<String> getSuppressedAdvertisers() {
            return suppressedAdvertisers;
        }

        public void setSuppressedAdvertisers(java.util.Set<String> suppressedAdvertisers) {
            this.suppressedAdvertisers = suppressedAdvertisers;
        }

        public long getCheckTimeMs() {
            return checkTimeMs;
        }

        public void setCheckTimeMs(long checkTimeMs) {
            this.checkTimeMs = checkTimeMs;
        }
    }
}
