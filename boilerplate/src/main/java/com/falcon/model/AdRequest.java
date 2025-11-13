package com.falcon.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an ad request from a publisher's placement.
 */
public class AdRequest {
    @JsonProperty("placement_id")
    private String placementId;

    @JsonProperty("user_email_hash")
    private String userEmailHash;

    // Constructors
    public AdRequest() {
    }

    public AdRequest(String placementId, String userEmailHash) {
        this.placementId = placementId;
        this.userEmailHash = userEmailHash;
    }

    // Getters and Setters
    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }

    public String getUserEmailHash() {
        return userEmailHash;
    }

    public void setUserEmailHash(String userEmailHash) {
        this.userEmailHash = userEmailHash;
    }

    @Override
    public String toString() {
        return "AdRequest{" +
                "placementId='" + placementId + '\'' +
                ", userEmailHash='" + (userEmailHash != null ? userEmailHash.substring(0, 8) + "..." : "null") + '\'' +
                '}';
    }
}
