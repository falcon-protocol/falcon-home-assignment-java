# Suppression List System - Java Home Assignment

## Overview

Welcome to the Falcon Protocol Suppression List System assignment! This exercise simulates implementing a real-world advertising suppression list system in Java.

**Time Estimate**: 90-120 minutes (with AI tools like Claude, ChatGPT, Copilot, etc.)

**Important Note**: Focus on completing the parts in order and demonstrating clean architecture. Quality matters more than attempting every feature.

## Background: What is a Suppression List?

A suppression list is a collection of user identifiers (email hashes) that should **NOT** receive advertisements from a specific advertiser. This is crucial for:

- **Privacy compliance** (GDPR, CCPA)
- **User experience** (don't show acquisition ads to existing customers)
- **Brand safety** (exclude competitors' employees)
- **Campaign optimization** (exclude converted users)

### Key Characteristics

- **Advertiser-Specific**: Each advertiser maintains their own suppression lists
- **Dynamic Updates**: Lists are submitted by different advertisers at different times
- **Real-Time Requirements**: Suppression decisions must be made in milliseconds during ad serving
- **High-Volume Operations**: Lists can contain millions of identifiers

## Ad Network Context

### Advertiser Side
- **Advertiser** → **Campaign** → **Banner**
  - Advertisers create campaigns
  - Each campaign contains banners (ad creatives)
  - Banners are the actual ads shown to users

### Publisher Side
- **Publisher** → **Site** → **Placement**
  - Publishers own websites/apps
  - Each site has placements (ad slots)
  - Placements are where ads get displayed

### Ad Serving
- The ad server connects banners to placements
- When a user visits a placement, the server selects which banner to show
- **Your suppression service** tells the ad server which advertisers to exclude for each user

---

## Assignment Structure

This assignment has **3 parts** that build on each other:

1. **Part 1 (30%)**: Core suppression list storage and REST API (~30-35 minutes)
2. **Part 2 (50%)**: Ad server integration with efficient lookups (~45-50 minutes)
3. **Part 3 (20%)**: One advanced feature + production architecture (~20-25 minutes)

---

## Part 1: Core Suppression System (30%)

### Objective
Build a REST API to manage suppression lists with basic CRUD operations.

### Requirements
- Create a Spring Boot REST API with the following endpoints:
  - `POST /api/suppression-lists` - Create a new suppression list
  - `GET /api/suppression-lists` - Get all suppression lists
  - `GET /api/suppression-lists/{id}` - Get a specific list
  - `PUT /api/suppression-lists/{id}` - Update a list (add/remove identifiers)
  - `DELETE /api/suppression-lists/{id}` - Delete a list

- Each suppression list should contain:
  - `id` (unique identifier)
  - `advertiser_id` (which advertiser owns this list)
  - `name` (descriptive name)
  - `identifiers` (list of email hashes - SHA-256 64-character hex strings)
  - `created_at` (timestamp)

- Choose your storage approach:
  - In-memory (simplest)
  - H2 database (good practice)
  - JSON file persistence (pragmatic)
  - SQLite (lightweight)

### Sample Data Structure
```json
{
  "id": "list_001",
  "advertiser_id": "adv_techcorp",
  "name": "Existing Customers",
  "identifiers": [
    "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd",
    "f6e5d4c3b2a1987654321fedcba098765432109876543210fedcba0987654321"
  ],
  "created_at": "2024-01-15T10:30:00Z"
}
```

### Expected Deliverables
- Working REST API with all CRUD endpoints
- Storage mechanism of your choice
- Basic error handling (404s, validation)
- Simple test demonstrating the API works (manual or automated)

### Tips
- Use Spring Boot's `@RestController` and `@Service` layers
- Start simple - in-memory storage is fine
- Focus on clean code structure over complex features

---

## Part 2: Ad Server Integration (50%)

### Objective
Implement efficient suppression checking that integrates with the provided ad server.

### Requirements

**Core Functionality:**
- Implement `POST /api/check-suppression` endpoint that:
  - Takes a user's email hash
  - Returns which advertiser IDs should be suppressed for that user
  - Responds in < 10ms for realistic performance

- Build an efficient lookup index:
  - Structure: `email_hash` → `Set<advertiser_id>`
  - Load suppression lists at startup
  - Optimize for fast lookups (millions of identifiers)

**Ad Server Integration:**
- Integrate with the provided `AdServer` class (DO NOT MODIFY)
- The ad server expects suppressed advertisers in `customParams`:
  ```json
  {
    "suppress_advertisers": ["adv_techcorp", "adv_gamestudio"]
  }
  ```
- The ad server will automatically exclude banners from suppressed advertisers

**Performance Requirements:**
- Pre-build indexes at startup (don't load data on every request)
- Use appropriate data structures (HashSet, HashMap)
- Consider memory vs. speed trade-offs

### Sample Request/Response

**Check Suppression Request:**
```json
POST /api/check-suppression
{
  "email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"
}
```

**Response:**
```json
{
  "suppressed_advertisers": ["adv_techcorp", "adv_gamestudio"],
  "check_time_ms": 2
}
```

**Ad Request Flow:**
```json
POST /api/serve-ad
{
  "placement_id": "placement_001",
  "user_email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"
}
```

### Expected Deliverables
- Efficient suppression checking endpoint
- Index structure for fast lookups
- Integration with provided AdServer
- Working end-to-end ad serving with suppression
- Performance metrics in response

### Tips
- Build a `Map<String, Set<String>>` index: `emailHash` → `Set<advertiserId>`
- Load and index all lists at startup in `@PostConstruct`
- Use the boilerplate `AdServer` - it's already complete
- Think about how this would scale to millions of identifiers

---

## Part 3: Advanced Feature + Architecture (20%)

### Objective
Implement ONE advanced feature and document your production architecture thinking.

### Part A: Implement ONE Advanced Feature (10%)

Choose one feature to implement:

**Option 1: Time-Based Expiration**
- Add `expires_at` field to suppression lists
- Automatically exclude expired identifiers from suppression checks
- Add cleanup mechanism for expired lists

**Option 2: Audit Logging**
- Log all suppression check results
- Track: timestamp, email_hash, suppressed_advertisers, response_time
- Provide endpoint to query audit logs

**Option 3: Caching Layer**
- Implement LRU cache for recent suppression checks
- Cache results for 5 minutes
- Add cache hit/miss metrics

### Part B: Production Architecture Document (10%)

Write a brief document (300-500 words) explaining:

1. **Scalability**: How would you handle:
   - 100 million identifiers across all lists?
   - 10,000 suppression checks per second?
   - 1,000 different advertisers?

2. **Data Architecture**:
   - What database would you use in production? Why?
   - How would you partition/shard the data?
   - What indexes would you create?

3. **Reliability**:
   - What happens if the suppression service is down?
   - How do you deploy updates without downtime?
   - How do you ensure data consistency?

4. **Monitoring**:
   - What metrics would you track?
   - What alerts would you set up?
   - How do you measure suppression accuracy?

### Expected Deliverables
- Working implementation of ONE advanced feature
- `ARCHITECTURE.md` document with production considerations
- Clear explanation of trade-offs and decisions

---

## Technical Constraints

- **Java 17** with Spring Boot
- **Maven** for build management
- **Local development only** - no cloud infrastructure required
- **Use provided boilerplate** - focus on suppression logic, not ad server implementation
- Use any additional libraries you need

## Deliverables

Your submission should include:

1. **Working code** for all completed parts
2. **README.md** with setup and testing instructions
3. **ARCHITECTURE.md** (for Part 3) explaining your design and production thinking
4. **Brief comments** in code explaining key decisions
5. **(Optional) TOOL_USAGE.md** describing AI tools you used and how

## Evaluation Criteria

- **Functionality (40%)**: Does your code work correctly?
- **Architecture (30%)**: Is your design clean, scalable, and well-organized?
- **Problem Solving (20%)**: How do you handle edge cases and optimize performance?
- **Documentation (10%)**: Can others understand your approach?

## Getting Started

1. **Review the mock data** in `mock-data/` directory
2. **Examine the boilerplate** in `boilerplate/` directory
3. **Read the boilerplate README** for integration details
4. **Start with Part 1** - get CRUD working first
5. **Use AI tools** - we expect and encourage it!
6. **Test as you go** - use the provided test data

## Important Notes

- **Use AI Tools**: Claude, ChatGPT, Copilot - we expect it!
- **No Perfect Solution**: We want to see your problem-solving approach
- **Document Decisions**: Explain your reasoning and trade-offs
- **Focus on Core Features**: Better to have working basics than broken advanced features

## Time Management Suggestions

- **Part 1**: 30-35 minutes (CRUD + storage)
- **Part 2**: 45-50 minutes (integration + optimization)
- **Part 3**: 20-25 minutes (one feature + architecture doc)
- **Buffer**: 5-10 minutes for testing and documentation

Good luck! We're excited to see your approach to this real-world advertising technology challenge.
