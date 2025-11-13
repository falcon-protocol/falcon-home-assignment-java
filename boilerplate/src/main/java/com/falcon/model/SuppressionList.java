package com.falcon.model;

import java.time.Instant;
import java.util.List;

/**
 * Represents a suppression list for a specific advertiser.
 *
 * Each advertiser can have multiple suppression lists containing user identifiers
 * (email hashes) that should not receive ads from that advertiser.
 */
public class SuppressionList {
    private String id;
    private String advertiserId;
    private String name;
    private List<String> identifiers;
    private Instant createdAt;

    // Constructors
    public SuppressionList() {
    }

    public SuppressionList(String id, String advertiserId, String name, List<String> identifiers, Instant createdAt) {
        this.id = id;
        this.advertiserId = advertiserId;
        this.name = name;
        this.identifiers = identifiers;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SuppressionList{" +
                "id='" + id + '\'' +
                ", advertiserId='" + advertiserId + '\'' +
                ", name='" + name + '\'' +
                ", identifiersCount=" + (identifiers != null ? identifiers.size() : 0) +
                ", createdAt=" + createdAt +
                '}';
    }
}
