# Suppression List System - Evaluation Criteria

## Overview

This document outlines how the Java home assignment will be evaluated. The assignment is designed for 90-120 minutes and consists of 3 parts with different weights.

## Scoring Philosophy

- **Quality over Speed**: Better to have fewer parts working well than all parts broken
- **Thought Process**: We value understanding of trade-offs and clear decision-making
- **Production Thinking**: Consider scalability and maintainability in your design
- **AI Tool Usage**: We expect and encourage use of AI tools - be transparent about what you used

## Part 1: Core Suppression System (30%)

### Excellent (90-100%)
- ✅ All CRUD endpoints implemented and working correctly
- ✅ Clean REST API design with proper HTTP methods and status codes
- ✅ Well-structured service/controller layers following Spring Boot best practices
- ✅ Storage mechanism chosen and implemented correctly (in-memory/H2/file/SQLite)
- ✅ Proper error handling (404s, 400s with meaningful messages)
- ✅ Clean, readable code with appropriate separation of concerns
- ✅ Basic validation (e.g., checking for duplicate IDs)
- ✅ Working test demonstrating functionality

### Good (75-89%)
- ✅ Most CRUD endpoints working correctly
- ✅ Functional REST API with reasonable design
- ✅ Storage mechanism implemented
- ✅ Basic error handling in place
- ⚠️ Some edge cases or validation missing
- ✅ Code is functional and reasonably organized

### Satisfactory (60-74%)
- ✅ Basic CRUD operations present (at least Create and Read)
- ✅ API endpoints respond correctly for happy path
- ✅ Storage mechanism exists
- ⚠️ Limited error handling
- ⚠️ Some code organization issues
- ⚠️ Missing some functionality or validation

### Needs Improvement (0-59%)
- ❌ Missing core CRUD functionality
- ❌ API doesn't work or has major bugs
- ❌ No clear storage implementation
- ❌ Poor code structure or won't compile

### Key Evaluation Points
1. **API Design** (25%): REST conventions, proper HTTP methods, clear endpoints
2. **Data Model** (20%): Appropriate structure for suppression lists
3. **Storage Implementation** (20%): Working persistence mechanism
4. **Code Quality** (20%): Clean, organized, readable code
5. **Error Handling** (15%): Proper validation and error responses

---

## Part 2: Ad Server Integration (50%)

### Excellent (90-100%)
- ✅ Efficient suppression check endpoint responding in < 10ms
- ✅ Well-designed index structure (HashMap with Sets for advertisers)
- ✅ Index built at startup, not on every request
- ✅ Proper integration with provided AdServer (correct custom params format)
- ✅ End-to-end ad serving with suppression working correctly
- ✅ Performance metrics included in response
- ✅ Handles edge cases (unknown email hashes, empty lists)
- ✅ Clear documentation of algorithm and data structures used
- ✅ Multiple advertisers per identifier handled correctly

### Good (75-89%)
- ✅ Suppression check endpoint working
- ✅ Reasonable index structure for lookups
- ✅ Integration with AdServer functional
- ✅ Decent performance (< 50ms)
- ⚠️ Some optimization opportunities missed
- ✅ Basic edge case handling

### Satisfactory (60-74%)
- ✅ Basic suppression check working
- ✅ Simple lookup mechanism implemented
- ✅ Ad server integration attempted
- ⚠️ Performance not optimized (> 50ms or reloads data)
- ⚠️ Limited edge case handling
- ⚠️ Integration may have minor issues

### Needs Improvement (0-59%)
- ❌ Suppression check doesn't work correctly
- ❌ No efficient index structure
- ❌ Poor integration with ad server
- ❌ Major performance issues or correctness bugs

### Key Evaluation Points
1. **Algorithm Efficiency** (30%): Appropriate data structures, fast lookups
2. **Index Design** (25%): Smart indexing strategy that scales
3. **Integration** (20%): Correct usage of AdServer API
4. **Performance** (15%): Response times, startup optimization
5. **Correctness** (10%): Accurate suppression decisions for all cases

---

## Part 3: Advanced Feature + Architecture (20%)

### Excellent (90-100%)
- ✅ One advanced feature implemented completely and correctly
- ✅ Feature adds real value and is production-ready
- ✅ Comprehensive architecture document (ARCHITECTURE.md)
- ✅ Realistic scalability analysis with specific numbers
- ✅ Clear database choice with justification
- ✅ Thoughtful reliability and deployment strategy
- ✅ Relevant monitoring metrics identified
- ✅ Shows deep understanding of production constraints

### Good (75-89%)
- ✅ Advanced feature implemented and working
- ✅ Good architecture document with key considerations
- ✅ Reasonable scalability thinking
- ✅ Database choice explained
- ⚠️ Some production details could be deeper

### Satisfactory (60-74%)
- ✅ Advanced feature partially implemented
- ✅ Basic architecture document present
- ⚠️ Limited scalability analysis
- ⚠️ Superficial production considerations

### Needs Improvement (0-59%)
- ❌ Advanced feature not working or missing
- ❌ Minimal or missing architecture document
- ❌ Poor understanding of production requirements

### Advanced Feature Options & Grading

**Time-Based Expiration:**
- Excellent: Auto-excludes expired identifiers, cleanup mechanism, tested
- Good: Expiration logic works, manual cleanup needed
- Satisfactory: Basic expiration check, no cleanup

**Audit Logging:**
- Excellent: Complete logging, queryable logs, useful metrics
- Good: Logs all checks with key info, basic querying
- Satisfactory: Simple logging without querying

**Caching Layer:**
- Excellent: LRU cache implemented, metrics tracked, configurable TTL
- Good: Basic cache working, hit/miss tracking
- Satisfactory: Simple cache without metrics

### Key Evaluation Points
1. **Feature Implementation** (50%): Completeness and correctness of chosen feature
2. **Scalability Analysis** (20%): Realistic thinking about millions of identifiers
3. **Architecture Depth** (20%): Database choices, partitioning, consistency
4. **Production Readiness** (10%): Monitoring, deployment, reliability considerations

---

## Overall Assessment Criteria

### Code Quality (25%)
- Clean, readable, maintainable code
- Appropriate use of Spring Boot patterns
- Good separation of concerns (controller/service/model)
- Meaningful variable and method names
- Error handling and edge cases

### Architecture & Design (25%)
- Smart data structure choices
- Efficient algorithms
- Scalable design patterns
- Clear separation between ad server and suppression logic

### Problem Solving (25%)
- Understanding of the core problem (advertiser-specific suppression)
- Efficient lookup strategy
- Performance optimization thinking
- Handling of edge cases

### Documentation (15%)
- Clear README with setup instructions
- Architecture document (Part 3)
- Code comments for complex logic
- Explanation of design decisions

### Completeness (10%)
- How many parts completed and to what quality
- Working end-to-end functionality
- Testing evidence

---

## Submission Requirements

### Required Deliverables
1. **Working Code**: All implemented parts must compile and run
2. **README.md**: Clear setup instructions with prerequisites
3. **ARCHITECTURE.md** (Part 3): Production architecture thinking
4. **(Optional) TOOL_USAGE.md**: AI tools used and how they helped

### Bonus Points (up to 10% extra)
- **Testing**: Unit or integration tests included
- **Documentation**: Exceptionally clear explanations
- **Innovation**: Creative solutions to problems
- **Performance**: Benchmarking or performance analysis included

---

## Red Flags (Automatic Deductions)

### Major Issues (-20 to -50%)
- Code doesn't compile or run
- Missing core functionality from attempted parts
- Critical bugs in suppression logic (wrong advertisers suppressed)
- No README or setup instructions

### Minor Issues (-5 to -15%)
- Poor code organization
- Missing error handling
- Inconsistent code style
- Incomplete README

---

## Sample Scoring Examples

### Example 1: All Parts, Good Quality (85%)
- Part 1: 27/30 (excellent CRUD, minor validation issue)
- Part 2: 43/50 (good integration, could optimize index building)
- Part 3: 16/20 (caching implemented well, good architecture doc)
- **Total: 86%**

### Example 2: Focused Excellence (88%)
- Part 1: 29/30 (nearly perfect CRUD implementation)
- Part 2: 47/50 (excellent index, < 5ms response time, great integration)
- Part 3: 15/20 (good architecture doc, feature partially implemented)
- Bonus: +3 for unit tests
- **Total: 91%**

### Example 3: Solid Fundamentals (75%)
- Part 1: 24/30 (good CRUD, some endpoints missing validations)
- Part 2: 38/50 (working integration, slower performance, simple index)
- Part 3: 13/20 (basic feature, adequate architecture doc)
- **Total: 75%**

---

## Interview Discussion Topics

Based on your submission, we'll discuss:

1. **Design Decisions**: Why did you choose this data structure/approach?
2. **Trade-offs**: What alternatives did you consider and why?
3. **Scaling**: How would this handle 100M identifiers and 10K requests/sec?
4. **Production**: What would you change before deploying this?
5. **AI Tools**: How did AI tools help your process? What did you validate or change?
6. **Time Management**: What would you do differently with more time?

---

## Time Allocation Guide

To succeed in 90-120 minutes:

- **Part 1** (30-35 min): Focus on getting CRUD working, pick simple storage
- **Part 2** (45-50 min): This is the core - efficient index is critical
- **Part 3** (20-25 min): Pick the easiest advanced feature for you
- **Buffer** (5-10 min): Testing, README, final polish

**Pro Tips:**
- Start with in-memory storage for Part 1 if unsure about databases
- Build the index structure FIRST in Part 2, then add endpoints
- For Part 3, pick the feature you're most comfortable with
- Document as you go - don't leave it all for the end

Remember: We're evaluating your **engineering approach and problem-solving**, not perfection. Show us how you think!
