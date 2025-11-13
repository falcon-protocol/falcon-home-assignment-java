package com.falcon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/**
 * Result of a suppression check showing which advertisers should be suppressed.
 */
public class SuppressionCheckResult {
    @JsonProperty("suppressed_advertisers")
    private Set<String> suppressedAdvertisers;

    @JsonProperty("check_time_ms")
    private long checkTimeMs;

    // Constructors
    public SuppressionCheckResult() {
    }

    public SuppressionCheckResult(Set<String> suppressedAdvertisers, long checkTimeMs) {
        this.suppressedAdvertisers = suppressedAdvertisers;
        this.checkTimeMs = checkTimeMs;
    }

    // Getters and Setters
    public Set<String> getSuppressedAdvertisers() {
        return suppressedAdvertisers;
    }

    public void setSuppressedAdvertisers(Set<String> suppressedAdvertisers) {
        this.suppressedAdvertisers = suppressedAdvertisers;
    }

    public long getCheckTimeMs() {
        return checkTimeMs;
    }

    public void setCheckTimeMs(long checkTimeMs) {
        this.checkTimeMs = checkTimeMs;
    }

    @Override
    public String toString() {
        return "SuppressionCheckResult{" +
                "suppressedAdvertisers=" + suppressedAdvertisers +
                ", checkTimeMs=" + checkTimeMs +
                '}';
    }
}
