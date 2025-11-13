package com.falcon.service;

import com.falcon.model.SuppressionCheckResult;
import com.falcon.model.SuppressionList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

/**
 * Suppression Service - YOUR IMPLEMENTATION SPACE
 *
 * This service manages suppression lists and performs real-time suppression checking.
 *
 * YOUR TASKS:
 * ===========
 *
 * PART 1: Implement CRUD operations for suppression lists
 * - createSuppressionList()
 * - getAllSuppressionLists()
 * - getSuppressionListById()
 * - updateSuppressionList()
 * - deleteSuppressionList()
 *
 * PART 2: Implement efficient suppression checking
 * - loadSuppressionLists() - Build an efficient index at startup
 * - checkSuppression() - Fast lookup (< 10ms) of suppressed advertisers
 *
 * PART 3: (Optional) Implement one advanced feature
 * - Time-based expiration
 * - Audit logging
 * - Caching layer
 */
@Service
public class SuppressionService {

    // Storage for suppression lists (Part 1)
    // TODO: Choose your storage mechanism:
    //   - In-memory Map (simplest)
    //   - H2 database (good practice)
    //   - JSON file persistence
    //   - SQLite
    private final Map<String, SuppressionList> suppressionListsStorage = new HashMap<>();

    // Index for fast lookups (Part 2)
    // TODO: Design an efficient index structure
    // Recommended: Map<String, Set<String>> where:
    //   Key = email_hash
    //   Value = Set of advertiser_ids that suppress this email
    private Map<String, Set<String>> suppressionIndex;

    private final ObjectMapper objectMapper;

    public SuppressionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * PART 2: Load suppression lists and build index at startup.
     *
     * TODO: Implement this method to:
     * 1. Load suppression lists from mock-data/sample_suppression_lists.json
     * 2. Build an efficient index for fast lookups
     * 3. Store lists in your chosen storage mechanism
     *
     * Performance requirement: Should complete in < 1 second for 1000s of lists
     */
    @PostConstruct
    public void loadSuppressionLists() {
        System.out.println("Loading suppression lists...");

        try {
            // TODO: Load from mock-data/sample_suppression_lists.json
            // Try external path first, then classpath as fallback
            InputStream inputStream;
            try {
                inputStream = new ClassPathResource("../../mock-data/sample_suppression_lists.json").getInputStream();
            } catch (IOException e) {
                inputStream = getClass().getClassLoader().getResourceAsStream("sample_suppression_lists.json");
            }

            if (inputStream != null) {
                List<SuppressionList> lists = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<SuppressionList>>() {}
                );

                // TODO: Store lists and build index
                // Example structure:
                // suppressionIndex = new HashMap<>();
                // for (SuppressionList list : lists) {
                //     String advertiserId = list.getAdvertiserId();
                //     for (String identifier : list.getIdentifiers()) {
                //         suppressionIndex.computeIfAbsent(identifier, k -> new HashSet<>()).add(advertiserId);
                //     }
                // }

                System.out.println("✓ Loaded " + lists.size() + " suppression lists");
                System.out.println("⚠ WARNING: Index building not implemented yet!");
            }
        } catch (IOException e) {
            System.err.println("✗ Failed to load suppression lists: " + e.getMessage());
        }
    }

    // ==================== PART 1: CRUD OPERATIONS ====================

    /**
     * Create a new suppression list.
     *
     * TODO: Implement this method
     * - Validate input (non-null advertiser_id, name, identifiers)
     * - Generate unique ID if not provided
     * - Set created_at timestamp
     * - Store in your chosen storage
     * - Update the suppression index
     *
     * @return The created suppression list with generated ID
     */
    public SuppressionList createSuppressionList(SuppressionList list) {
        // TODO: Implement CRUD - Create
        throw new UnsupportedOperationException("createSuppressionList() not implemented yet");
    }

    /**
     * Get all suppression lists.
     *
     * TODO: Implement this method
     * @return List of all suppression lists
     */
    public List<SuppressionList> getAllSuppressionLists() {
        // TODO: Implement CRUD - Read all
        throw new UnsupportedOperationException("getAllSuppressionLists() not implemented yet");
    }

    /**
     * Get a specific suppression list by ID.
     *
     * TODO: Implement this method
     * @return The suppression list, or null if not found
     */
    public SuppressionList getSuppressionListById(String id) {
        // TODO: Implement CRUD - Read one
        throw new UnsupportedOperationException("getSuppressionListById() not implemented yet");
    }

    /**
     * Update a suppression list (add/remove identifiers).
     *
     * TODO: Implement this method
     * - Update the storage
     * - Rebuild the index for affected identifiers
     *
     * @return The updated suppression list, or null if not found
     */
    public SuppressionList updateSuppressionList(String id, SuppressionList updatedList) {
        // TODO: Implement CRUD - Update
        throw new UnsupportedOperationException("updateSuppressionList() not implemented yet");
    }

    /**
     * Delete a suppression list.
     *
     * TODO: Implement this method
     * - Remove from storage
     * - Remove from index
     *
     * @return true if deleted, false if not found
     */
    public boolean deleteSuppressionList(String id) {
        // TODO: Implement CRUD - Delete
        throw new UnsupportedOperationException("deleteSuppressionList() not implemented yet");
    }

    // ==================== PART 2: SUPPRESSION CHECKING ====================

    /**
     * Check which advertisers should be suppressed for a given email hash.
     *
     * TODO: Implement this method with efficient lookup
     * Performance requirement: < 10ms response time
     *
     * Algorithm:
     * 1. Look up emailHash in your index
     * 2. Return the set of advertiser IDs that suppress this email
     * 3. Track the execution time for performance metrics
     *
     * @param emailHash The user's email hash (SHA-256, 64 hex characters)
     * @return Result containing suppressed advertisers and performance metrics
     */
    public SuppressionCheckResult checkSuppression(String emailHash) {
        long startTime = System.currentTimeMillis();

        // TODO: Implement efficient suppression check
        // Example implementation:
        // Set<String> suppressedAdvertisers = suppressionIndex.getOrDefault(emailHash, Collections.emptySet());
        // long duration = System.currentTimeMillis() - startTime;
        // return new SuppressionCheckResult(suppressedAdvertisers, duration);

        long duration = System.currentTimeMillis() - startTime;

        throw new UnsupportedOperationException("checkSuppression() not implemented yet");
    }

    // ==================== PART 3: ADVANCED FEATURES (OPTIONAL) ====================

    /**
     * OPTIONAL: Implement ONE advanced feature here
     *
     * Option 1: Time-Based Expiration
     * - Add expires_at field to SuppressionList
     * - Filter out expired lists in checkSuppression()
     * - Add cleanup job for expired lists
     *
     * Option 2: Audit Logging
     * - Log every suppression check with timestamp, email_hash, results
     * - Provide method to query audit logs
     *
     * Option 3: Caching Layer
     * - Implement LRU cache for recent checks
     * - Track cache hit/miss rates
     * - Configure TTL (e.g., 5 minutes)
     */

    // TODO: Implement your chosen advanced feature here

    // ==================== HELPER METHODS ====================

    /**
     * Rebuild the entire suppression index from stored lists.
     * Useful after bulk updates or for testing.
     */
    private void rebuildIndex() {
        // TODO: Implement index rebuilding
    }

    /**
     * Get statistics about loaded suppression lists.
     * Useful for monitoring and debugging.
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total_lists", suppressionListsStorage.size());
        stats.put("total_identifiers", suppressionIndex != null ? suppressionIndex.size() : 0);
        // TODO: Add more statistics
        return stats;
    }
}
