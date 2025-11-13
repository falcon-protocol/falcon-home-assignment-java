package com.falcon.controller;

import com.falcon.model.AdRequest;
import com.falcon.model.AdResponse;
import com.falcon.model.Banner;
import com.falcon.model.SuppressionCheckResult;
import com.falcon.service.AdServer;
import com.falcon.service.SuppressionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Ad Serving with Suppression Integration (Part 2).
 *
 * This controller demonstrates how the suppression system integrates with
 * the ad server. You may need to modify the serveAd() method implementation
 * as you complete Part 2.
 */
@RestController
@RequestMapping("/api")
public class AdController {

    private final AdServer adServer;
    private final SuppressionService suppressionService;

    public AdController(AdServer adServer, SuppressionService suppressionService) {
        this.adServer = adServer;
        this.suppressionService = suppressionService;
    }

    /**
     * Serve an ad for a placement with suppression logic.
     * POST /api/serve-ad
     *
     * Flow:
     * 1. Receive ad request with placement_id and user_email_hash
     * 2. Check suppression: which advertisers should be excluded for this user?
     * 3. Pass suppressed advertisers to ad server via custom params
     * 4. Ad server filters out banners from suppressed advertisers
     * 5. Return selected banner (or no ad if all eligible banners suppressed)
     */
    @PostMapping("/serve-ad")
    public ResponseEntity<?> serveAd(@RequestBody AdRequest request) {
        try {
            // Validate request
            if (request.getPlacementId() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "placement_id is required"));
            }

            // PART 2: Check suppression for this user
            SuppressionCheckResult suppressionResult = null;
            if (request.getUserEmailHash() != null && !request.getUserEmailHash().isEmpty()) {
                try {
                    suppressionResult = suppressionService.checkSuppression(request.getUserEmailHash());
                } catch (UnsupportedOperationException e) {
                    // Suppression check not implemented yet - serve ad without suppression
                    System.out.println("⚠ Warning: Suppression check not implemented, serving ad without suppression");
                }
            }

            // Prepare custom parameters for ad server
            Map<String, Object> customParams = new HashMap<>();
            if (suppressionResult != null && !suppressionResult.getSuppressedAdvertisers().isEmpty()) {
                customParams.put("suppress_advertisers", suppressionResult.getSuppressedAdvertisers());
            }

            // Call ad server to select a banner
            Banner selectedBanner = adServer.serveAd(request.getPlacementId(), customParams);

            // Build response
            if (selectedBanner != null) {
                AdResponse response = new AdResponse();
                response.setBanner(selectedBanner);

                // Include suppression info for transparency
                if (suppressionResult != null) {
                    AdResponse.SuppressionInfo info = new AdResponse.SuppressionInfo(
                            suppressionResult.getSuppressedAdvertisers(),
                            suppressionResult.getCheckTimeMs()
                    );
                    response.setSuppressionInfo(info);
                }

                return ResponseEntity.ok(response);
            } else {
                // No eligible banner found (all suppressed or no matching banners)
                AdResponse response = new AdResponse("No eligible ad found for this placement and user");
                if (suppressionResult != null) {
                    AdResponse.SuppressionInfo info = new AdResponse.SuppressionInfo(
                            suppressionResult.getSuppressedAdvertisers(),
                            suppressionResult.getCheckTimeMs()
                    );
                    response.setSuppressionInfo(info);
                }
                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to serve ad: " + e.getMessage()));
        }
    }

    /**
     * Get information about loaded banners (for debugging/testing).
     * GET /api/banners
     */
    @GetMapping("/banners")
    public ResponseEntity<?> getBanners() {
        return ResponseEntity.ok(Map.of(
                "banners", adServer.getAllBanners(),
                "count_by_advertiser", adServer.getBannerCountByAdvertiser()
        ));
    }
}
