package com.falcon.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an ad banner (creative) that can be served to users.
 *
 * Banners belong to advertisers and are matched to placements by the ad server.
 */
public class Banner {
    private String id;

    @JsonProperty("advertiser_id")
    private String advertiserId;

    @JsonProperty("campaign_id")
    private String campaignId;

    private String name;

    @JsonProperty("creative_url")
    private String creativeUrl;

    @JsonProperty("click_url")
    private String clickUrl;

    private int width;
    private int height;
    private int weight;  // Used for weighted random selection

    // Constructors
    public Banner() {
    }

    public Banner(String id, String advertiserId, String campaignId, String name,
                  String creativeUrl, String clickUrl, int width, int height, int weight) {
        this.id = id;
        this.advertiserId = advertiserId;
        this.campaignId = campaignId;
        this.name = name;
        this.creativeUrl = creativeUrl;
        this.clickUrl = clickUrl;
        this.width = width;
        this.height = height;
        this.weight = weight;
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

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreativeUrl() {
        return creativeUrl;
    }

    public void setCreativeUrl(String creativeUrl) {
        this.creativeUrl = creativeUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", advertiserId='" + advertiserId + '\'' +
                ", name='" + name + '\'' +
                ", size=" + width + "x" + height +
                '}';
    }
}
