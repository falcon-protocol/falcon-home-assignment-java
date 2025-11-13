

# Boilerplate Code Documentation

## Overview

This directory contains complete boilerplate code for the Suppression List System assignment. The boilerplate provides:

- ✅ **Complete Spring Boot setup** with Maven
- ✅ **AdServer implementation** (DO NOT MODIFY - represents external system)
- ✅ **Skeleton SuppressionService** (YOUR IMPLEMENTATION SPACE)
- ✅ **REST Controllers** ready to use
- ✅ **Model classes** for all data structures
- ✅ **Mock data loading** configured

## Project Structure

```
boilerplate/
├── pom.xml                          Maven configuration
├── src/main/
│   ├── java/com/falcon/
│   │   ├── SuppressionSystemApplication.java    Main application
│   │   ├── controller/
│   │   │   ├── SuppressionController.java       Part 1 & 2 endpoints
│   │   │   └── AdController.java                Ad serving endpoint
│   │   ├── service/
│   │   │   ├── AdServer.java                    DO NOT MODIFY
│   │   │   └── SuppressionService.java          YOUR IMPLEMENTATION
│   │   └── model/
│   │       ├── SuppressionList.java
│   │       ├── Banner.java
│   │       ├── AdRequest.java
│   │       ├── AdResponse.java
│   │       ├── SuppressionCheckRequest.java
│   │       └── SuppressionCheckResult.java
│   └── resources/
│       └── application.properties               Configuration
└── README.md (this file)
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA recommended)

### Build and Run

```bash
# Navigate to boilerplate directory
cd boilerplate/

# Clean and build
mvn clean install

# Run the application
mvn spring-boot:run

# Alternative: Run from IDE
# Open SuppressionSystemApplication.java and click Run
```

### Verify It's Working

```bash
# Health check
curl http://localhost:8080/api/health

# Should return:
# {"status":"UP","service":"Falcon Suppression System"}
```

## Your Implementation Tasks

### Part 1: CRUD Operations (30%)

**File to modify**: `src/main/java/com/falcon/service/SuppressionService.java`

Implement these methods:
- `createSuppressionList(SuppressionList list)`
- `getAllSuppressionLists()`
- `getSuppressionListById(String id)`
- `updateSuppressionList(String id, SuppressionList updatedList)`
- `deleteSuppressionList(String id)`

**Storage Options:**
1. **In-memory** (simplest): Use `Map<String, SuppressionList>`
2. **H2 database**: Enable in `application.properties`, add JPA entities
3. **JSON file**: Persist to/from JSON file
4. **SQLite**: Lightweight file-based database

**Example: In-Memory Implementation**
```java
private final Map<String, SuppressionList> storage = new ConcurrentHashMap<>();

public SuppressionList createSuppressionList(SuppressionList list) {
    if (list.getId() == null) {
        list.setId("list_" + UUID.randomUUID().toString());
    }
    if (list.getCreatedAt() == null) {
        list.setCreatedAt(Instant.now());
    }
    storage.put(list.getId(), list);
    rebuildIndex(); // Update the index
    return list;
}
```

**Test Your Implementation:**
```bash
# Create a suppression list
curl -X POST http://localhost:8080/api/suppression-lists \
  -H "Content-Type: application/json" \
  -d '{
    "advertiser_id": "adv_test",
    "name": "Test List",
    "identifiers": ["abc123...", "def456..."]
  }'

# Get all lists
curl http://localhost:8080/api/suppression-lists

# Get specific list
curl http://localhost:8080/api/suppression-lists/{id}
```

### Part 2: Ad Server Integration (50%)

**Core Challenge**: Implement efficient suppression checking

**Step 1: Build the Index**

Implement `loadSuppressionLists()` to create an efficient lookup structure:

```java
@PostConstruct
public void loadSuppressionLists() {
    // Load from mock data
    List<SuppressionList> lists = loadFromJson();

    // Build index: email_hash -> Set<advertiser_id>
    suppressionIndex = new HashMap<>();

    for (SuppressionList list : lists) {
        String advertiserId = list.getAdvertiserId();
        for (String identifier : list.getIdentifiers()) {
            suppressionIndex
                .computeIfAbsent(identifier, k -> new HashSet<>())
                .add(advertiserId);
        }
    }

    System.out.println("✓ Built index with " + suppressionIndex.size() + " identifiers");
}
```

**Why This Structure?**
- **O(1) lookup** by email hash
- **Memory efficient**: Each identifier stored once
- **Fast set operations**: HashSet for advertiser IDs

**Step 2: Implement Suppression Check**

```java
public SuppressionCheckResult checkSuppression(String emailHash) {
    long startTime = System.nanoTime();

    // Fast O(1) lookup in index
    Set<String> suppressedAdvertisers = suppressionIndex
        .getOrDefault(emailHash, Collections.emptySet());

    long duration = (System.nanoTime() - startTime) / 1_000_000; // Convert to ms

    return new SuppressionCheckResult(
        new HashSet<>(suppressedAdvertisers),  // Return copy
        duration
    );
}
```

**Step 3: Understand the Integration**

The flow is:
```
1. User visits page → Ad request received
2. SuppressionService.checkSuppression(email_hash) → Get suppressed advertisers
3. AdController passes suppressed advertisers to AdServer
4. AdServer filters out banners from suppressed advertisers
5. Selected banner returned to user
```

**Key Integration Point** in `AdController.java`:
```java
// Check suppression
SuppressionCheckResult result = suppressionService.checkSuppression(userEmailHash);

// Pass to ad server
Map<String, Object> customParams = new HashMap<>();
customParams.put("suppress_advertisers", result.getSuppressedAdvertisers());

// Ad server automatically filters
Banner banner = adServer.serveAd(placementId, customParams);
```

**Test Your Implementation:**
```bash
# Check suppression for a user
curl -X POST http://localhost:8080/api/check-suppression \
  -H "Content-Type: application/json" \
  -d '{
    "email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"
  }'

# Expected response:
# {
#   "suppressed_advertisers": ["adv_techcorp", "adv_luxurystore"],
#   "check_time_ms": 2
# }

# Serve ad with suppression
curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{
    "placement_id": "placement_001",
    "user_email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"
  }'
```

### Part 3: Advanced Feature (20%)

Choose **ONE** feature to implement:

#### Option 1: Time-Based Expiration

Add expiration logic:
```java
public class SuppressionList {
    private Instant expiresAt;  // Add this field
    // ... other fields
}

public SuppressionCheckResult checkSuppression(String emailHash) {
    Set<String> suppressedAdvertisers = new HashSet<>();

    // Filter out expired lists
    Instant now = Instant.now();
    for (String advertiserId : suppressionIndex.getOrDefault(emailHash, Set.of())) {
        if (!isExpired(advertiserId, now)) {
            suppressedAdvertisers.add(advertiserId);
        }
    }

    return new SuppressionCheckResult(suppressedAdvertisers, duration);
}
```

#### Option 2: Audit Logging

Log all suppression checks:
```java
private final List<AuditLog> auditLogs = new CopyOnWriteArrayList<>();

public SuppressionCheckResult checkSuppression(String emailHash) {
    // ... perform check

    // Log the check
    auditLogs.add(new AuditLog(
        Instant.now(),
        emailHash,
        suppressedAdvertisers,
        duration
    ));

    return result;
}

// Add endpoint to query logs
public List<AuditLog> getAuditLogs(Instant since) {
    return auditLogs.stream()
        .filter(log -> log.timestamp().isAfter(since))
        .collect(Collectors.toList());
}
```

#### Option 3: Caching Layer

Add LRU cache:
```java
private final LinkedHashMap<String, SuppressionCheckResult> cache =
    new LinkedHashMap<>(100, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > 100;  // Max 100 entries
        }
    };

public SuppressionCheckResult checkSuppression(String emailHash) {
    // Check cache first
    SuppressionCheckResult cached = cache.get(emailHash);
    if (cached != null && !isExpired(cached)) {
        return cached;
    }

    // Compute and cache
    SuppressionCheckResult result = computeSuppression(emailHash);
    cache.put(emailHash, result);
    return result;
}
```

## How the AdServer Works

**File**: `src/main/java/com/falcon/service/AdServer.java`

**⚠️ DO NOT MODIFY THIS FILE** - It represents an external ad server you cannot control.

### What It Does

1. **Loads Banners** at startup from `mock-data/ad_server_config.json`
2. **Filters Banners** based on `customParams["suppress_advertisers"]`
3. **Selects Banner** using weighted random selection
4. **Returns Result** or null if no eligible banners

### Key Method: `serveAd()`

```java
public Banner serveAd(String placementId, Map<String, Object> customParams) {
    // Extract suppressed advertisers from custom params
    Set<String> suppressedAdvertisers = extractSuppressedAdvertisers(customParams);

    // Filter out banners from suppressed advertisers
    List<Banner> eligible = allBanners.stream()
        .filter(b -> !suppressedAdvertisers.contains(b.getAdvertiserId()))
        .collect(Collectors.toList());

    // Weighted random selection
    return selectBannerWeighted(eligible);
}
```

### Integration Contract

**You must pass suppressed advertisers like this:**
```java
Map<String, Object> customParams = new HashMap<>();
customParams.put("suppress_advertisers", Set.of("adv_techcorp", "adv_gamestudio"));

Banner banner = adServer.serveAd("placement_001", customParams);
```

The ad server will automatically exclude any banners from those advertisers.

## Performance Requirements

### Part 2 Performance Targets

- **Suppression Check**: < 10ms per request
- **Index Building**: < 1 second for 1000s of lists
- **Memory**: Efficient for millions of identifiers

### Optimization Tips

1. **Use HashSet/HashMap**: O(1) lookups
2. **Pre-build index**: Don't load data on every request
3. **Immutable sets**: Return `Collections.unmodifiableSet()` for safety
4. **Concurrent access**: Use `ConcurrentHashMap` if needed

## Testing Strategy

### Unit Testing (Optional)

Create tests in `src/test/java/`:
```java
@SpringBootTest
class SuppressionServiceTest {
    @Autowired
    private SuppressionService service;

    @Test
    void testSuppressionCheck() {
        String emailHash = "a1b2c3d4...";
        SuppressionCheckResult result = service.checkSuppression(emailHash);

        assertTrue(result.getSuppressedAdvertisers().contains("adv_techcorp"));
        assertTrue(result.getCheckTimeMs() < 10);
    }
}
```

### Manual Testing

Use the provided test data:
```bash
# Test Case 1: User in TechCorp and LuxuryStore lists
curl -X POST http://localhost:8080/api/check-suppression \
  -H "Content-Type: application/json" \
  -d '{"email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"}'

# Expected: ["adv_techcorp", "adv_luxurystore"]

# Test Case 2: User not in any list
curl -X POST http://localhost:8080/api/check-suppression \
  -H "Content-Type: application/json" \
  -d '{"email_hash": "9999999999999999999999999999999999999999999999999999999999999999"}'

# Expected: []
```

## Common Issues and Solutions

### Issue: Application won't start

**Solution**: Check Java version
```bash
java -version  # Should be 17+
```

### Issue: Mock data not loading

**Solution**: Check file paths in `application.properties` or place files in `src/main/resources/`

### Issue: Endpoints returning 501 Not Implemented

**Solution**: You haven't implemented the service methods yet - that's expected! Implement them one by one.

### Issue: Performance is slow (> 10ms)

**Solutions**:
- Check if you're rebuilding the index on every request (BAD)
- Ensure you're using HashMap/HashSet (O(1) lookups)
- Consider adding caching

## Next Steps

1. **Implement Part 1**: Get CRUD working with your chosen storage
2. **Implement Part 2**: Build efficient index and suppression checking
3. **Test Integration**: Use curl to verify end-to-end flow
4. **Add One Feature**: Choose and implement Part 3 advanced feature
5. **Document**: Write ARCHITECTURE.md explaining your design

## Questions?

- Check the main `ASSIGNMENT_INSTRUCTIONS.md` for requirements
- Check `EVALUATION_CRITERIA.md` for grading details
- Check `mock-data/` for test data structures

Good luck! Focus on getting Part 2 working well - that's the core challenge. 🚀
