package com.falcon.controller;

import com.falcon.model.SuppressionCheckRequest;
import com.falcon.model.SuppressionCheckResult;
import com.falcon.model.SuppressionList;
import com.falcon.service.SuppressionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Suppression List Management (Part 1) and
 * Suppression Checking (Part 2).
 *
 * This controller provides endpoints for the assignment tasks.
 * Some methods may need modification as you implement the service layer.
 */
@RestController
@RequestMapping("/api")
public class SuppressionController {

    private final SuppressionService suppressionService;

    public SuppressionController(SuppressionService suppressionService) {
        this.suppressionService = suppressionService;
    }

    // ==================== PART 1: CRUD ENDPOINTS ====================

    /**
     * Create a new suppression list.
     * POST /api/suppression-lists
     */
    @PostMapping("/suppression-lists")
    public ResponseEntity<?> createSuppressionList(@RequestBody SuppressionList list) {
        try {
            SuppressionList created = suppressionService.createSuppressionList(list);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Map.of("error", "This endpoint is not implemented yet. See SuppressionService.createSuppressionList()"));
        }
    }

    /**
     * Get all suppression lists.
     * GET /api/suppression-lists
     */
    @GetMapping("/suppression-lists")
    public ResponseEntity<?> getAllSuppressionLists() {
        try {
            List<SuppressionList> lists = suppressionService.getAllSuppressionLists();
            return ResponseEntity.ok(lists);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Map.of("error", "This endpoint is not implemented yet. See SuppressionService.getAllSuppressionLists()"));
        }
    }

    /**
     * Get a specific suppression list by ID.
     * GET /api/suppression-lists/{id}
     */
    @GetMapping("/suppression-lists/{id}")
    public ResponseEntity<?> getSuppressionListById(@PathVariable String id) {
        try {
            SuppressionList list = suppressionService.getSuppressionListById(id);
            if (list != null) {
                return ResponseEntity.ok(list);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Map.of("error", "This endpoint is not implemented yet. See SuppressionService.getSuppressionListById()"));
        }
    }

    /**
     * Update a suppression list.
     * PUT /api/suppression-lists/{id}
     */
    @PutMapping("/suppression-lists/{id}")
    public ResponseEntity<?> updateSuppressionList(
            @PathVariable String id,
            @RequestBody SuppressionList updatedList) {
        try {
            SuppressionList updated = suppressionService.updateSuppressionList(id, updatedList);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Map.of("error", "This endpoint is not implemented yet. See SuppressionService.updateSuppressionList()"));
        }
    }

    /**
     * Delete a suppression list.
     * DELETE /api/suppression-lists/{id}
     */
    @DeleteMapping("/suppression-lists/{id}")
    public ResponseEntity<?> deleteSuppressionList(@PathVariable String id) {
        try {
            boolean deleted = suppressionService.deleteSuppressionList(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Map.of("error", "This endpoint is not implemented yet. See SuppressionService.deleteSuppressionList()"));
        }
    }

    // ==================== PART 2: SUPPRESSION CHECKING ====================

    /**
     * Check which advertisers should be suppressed for a given email hash.
     * POST /api/check-suppression
     *
     * Request body: { "email_hash": "a1b2c3..." }
     * Response: { "suppressed_advertisers": ["adv1", "adv2"], "check_time_ms": 2 }
     */
    @PostMapping("/check-suppression")
    public ResponseEntity<?> checkSuppression(@RequestBody SuppressionCheckRequest request) {
        try {
            if (request.getEmailHash() == null || request.getEmailHash().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "email_hash is required"));
            }

            SuppressionCheckResult result = suppressionService.checkSuppression(request.getEmailHash());
            return ResponseEntity.ok(result);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Map.of("error", "This endpoint is not implemented yet. See SuppressionService.checkSuppression()"));
        }
    }

    // ==================== MONITORING / DEBUG ENDPOINTS ====================

    /**
     * Get statistics about the suppression system.
     * GET /api/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = suppressionService.getStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Health check endpoint.
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Falcon Suppression System"
        ));
    }
}
