package com.falcon.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to check which advertisers should be suppressed for a given user.
 */
public class SuppressionCheckRequest {
    @JsonProperty("email_hash")
    private String emailHash;

    // Constructors
    public SuppressionCheckRequest() {
    }

    public SuppressionCheckRequest(String emailHash) {
        this.emailHash = emailHash;
    }

    // Getters and Setters
    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    @Override
    public String toString() {
        return "SuppressionCheckRequest{" +
                "emailHash='" + (emailHash != null ? emailHash.substring(0, 8) + "..." : "null") + '\'' +
                '}';
    }
}
