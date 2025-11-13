package com.falcon.service;

import com.falcon.model.Banner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ad Server Service - DO NOT MODIFY THIS FILE
 *
 * This class simulates an external ad server that you cannot control.
 * It handles:
 * - Loading banner configurations
 * - Weighted random banner selection
 * - Filtering banners based on custom parameters (including suppressed advertisers)
 *
 * Your task is to integrate with this ad server by passing the correct
 * custom parameters containing suppressed advertiser IDs.
 */
@Service
public class AdServer {
    private List<Banner> allBanners;
    private final ObjectMapper objectMapper;
    private final Random random;

    public AdServer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.random = new Random();
    }

    /**
     * Load banner configurations at startup from mock data.
     */
    @PostConstruct
    public void loadBanners() {
        try {
            // Try to load from external mock-data directory first
            InputStream inputStream;
            try {
                inputStream = new ClassPathResource("../../mock-data/ad_server_config.json").getInputStream();
            } catch (IOException e) {
                // Fallback to classpath
                inputStream = getClass().getClassLoader().getResourceAsStream("ad_server_config.json");
            }

            if (inputStream == null) {
                throw new RuntimeException("Could not find ad_server_config.json");
            }

            JsonNode root = objectMapper.readTree(inputStream);
            JsonNode bannersNode = root.get("banners");

            allBanners = new ArrayList<>();
            if (bannersNode.isArray()) {
                for (JsonNode bannerNode : bannersNode) {
                    Banner banner = objectMapper.treeToValue(bannerNode, Banner.class);
                    allBanners.add(banner);
                }
            }

            System.out.println("✓ AdServer loaded " + allBanners.size() + " banners");
        } catch (IOException e) {
            System.err.println("✗ Failed to load ad server config: " + e.getMessage());
            allBanners = new ArrayList<>();
        }
    }

    /**
     * Serve an ad banner for a placement, filtering out suppressed advertisers.
     *
     * @param placementId The placement requesting an ad
     * @param customParams Custom parameters including "suppress_advertisers"
     * @return Selected banner, or null if no eligible banner found
     */
    public Banner serveAd(String placementId, Map<String, Object> customParams) {
        // Get suppressed advertiser IDs from custom parameters
        Set<String> suppressedAdvertisers = extractSuppressedAdvertisers(customParams);

        // Filter banners: exclude those from suppressed advertisers
        List<Banner> eligibleBanners = allBanners.stream()
                .filter(banner -> !suppressedAdvertisers.contains(banner.getAdvertiserId()))
                .collect(Collectors.toList());

        if (eligibleBanners.isEmpty()) {
            return null;  // No eligible banners after suppression
        }

        // Weighted random selection
        return selectBannerWeighted(eligibleBanners);
    }

    /**
     * Extract suppressed advertiser IDs from custom parameters.
     */
    @SuppressWarnings("unchecked")
    private Set<String> extractSuppressedAdvertisers(Map<String, Object> customParams) {
        if (customParams == null || !customParams.containsKey("suppress_advertisers")) {
            return Collections.emptySet();
        }

        Object suppressObj = customParams.get("suppress_advertisers");
        if (suppressObj instanceof Collection) {
            return new HashSet<>((Collection<String>) suppressObj);
        } else if (suppressObj instanceof String[]) {
            return new HashSet<>(Arrays.asList((String[]) suppressObj));
        }

        return Collections.emptySet();
    }

    /**
     * Select a banner using weighted random selection.
     * Banners with higher weights have proportionally higher chances of being selected.
     */
    private Banner selectBannerWeighted(List<Banner> banners) {
        if (banners.isEmpty()) {
            return null;
        }

        if (banners.size() == 1) {
            return banners.get(0);
        }

        // Calculate total weight
        int totalWeight = banners.stream()
                .mapToInt(Banner::getWeight)
                .sum();

        // Random selection based on weights
        int randomValue = random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (Banner banner : banners) {
            cumulativeWeight += banner.getWeight();
            if (randomValue < cumulativeWeight) {
                return banner;
            }
        }

        // Fallback (should never reach here)
        return banners.get(0);
    }

    /**
     * Get all loaded banners (for testing/debugging).
     */
    public List<Banner> getAllBanners() {
        return new ArrayList<>(allBanners);
    }

    /**
     * Get count of banners by advertiser ID.
     */
    public Map<String, Long> getBannerCountByAdvertiser() {
        return allBanners.stream()
                .collect(Collectors.groupingBy(
                        Banner::getAdvertiserId,
                        Collectors.counting()
                ));
    }
}
