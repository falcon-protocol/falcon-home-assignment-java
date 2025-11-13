# Suppression List System - Submission Template

> **Instructions**: Fill out this template to organize your submission. Replace the placeholder sections with your actual information. This helps us understand your approach and evaluate your work consistently.

---

## Candidate Information

**Name**: [Your Name]
**Email**: [Your Email]
**Date Completed**: [Date]
**Time Spent**: [Actual time in minutes]

---

## Parts Completed

Mark which parts you completed:

- [ ] **Part 1**: Core Suppression System (30%)
- [ ] **Part 2**: Ad Server Integration (50%)
- [ ] **Part 3**: Advanced Feature + Architecture (20%)

---

## Part 1: Core Suppression System

### Implementation Summary
> Briefly describe your CRUD implementation (2-3 sentences)

[Your response here]

### Storage Choice
> What storage mechanism did you use and why?

- [ ] In-memory
- [ ] H2 Database
- [ ] JSON file persistence
- [ ] SQLite
- [ ] Other: _____________

**Justification**: [Why did you choose this approach?]

### API Endpoints Implemented
List the endpoints you implemented:

- [ ] `POST /api/suppression-lists` - Create list
- [ ] `GET /api/suppression-lists` - Get all lists
- [ ] `GET /api/suppression-lists/{id}` - Get specific list
- [ ] `PUT /api/suppression-lists/{id}` - Update list
- [ ] `DELETE /api/suppression-lists/{id}` - Delete list

### Key Design Decisions
> What were your main design choices and why?

1. [Decision 1 and reasoning]
2. [Decision 2 and reasoning]
3. [Decision 3 and reasoning]

### Testing Evidence
> How did you test Part 1?

[Describe manual testing, automated tests, or Postman collection used]

---

## Part 2: Ad Server Integration

### Suppression Check Implementation
> Describe your suppression checking algorithm (3-4 sentences)

[Your response here]

### Index Structure
> What data structure did you use for the index and why?

**Structure**: [e.g., HashMap<String, Set<String>> mapping email_hash to advertiser IDs]

**Rationale**: [Why this structure? Time/space complexity?]

### Performance Metrics
> What performance did you achieve?

- **Average suppression check time**: _____ ms
- **Index build time at startup**: _____ ms
- **Memory usage estimate**: _____ MB (for X identifiers)

### Integration Approach
> How did you integrate with the provided AdServer?

[Explain how you pass suppressed advertisers to the ad server]

### Optimization Techniques
> What optimizations did you implement?

- [ ] Pre-built index at startup
- [ ] HashSet for O(1) lookups
- [ ] Lazy loading
- [ ] Caching
- [ ] Other: _____________

### Testing Evidence
> How did you test the ad server integration?

[Describe test scenarios you validated]

---

## Part 3: Advanced Feature + Architecture

### Advanced Feature Chosen
> Which feature did you implement?

- [ ] Time-Based Expiration
- [ ] Audit Logging
- [ ] Caching Layer
- [ ] Other: _____________

### Feature Implementation
> Describe your implementation (3-4 sentences)

[Your response here]

### Architecture Document
> Do you have an ARCHITECTURE.md file?

- [ ] Yes - see `ARCHITECTURE.md`
- [ ] No - see summary below

### Production Architecture Summary
> Briefly describe your production architecture thinking

**Scalability**:
[How would you handle 100M identifiers and 10K requests/sec?]

**Database Choice**:
[What database would you use in production and why?]

**Reliability Strategy**:
[What happens if the service is down? Deployment strategy?]

**Monitoring Approach**:
[What metrics would you track?]

---

## Technology Stack

### Core Technologies
- **Language**: Java 17
- **Framework**: Spring Boot [version]
- **Build Tool**: Maven
- **Database/Storage**: [Your choice]

### Additional Libraries Used
> List any additional dependencies you added

1. [Library name] - [Purpose]
2. [Library name] - [Purpose]

---

## AI Tool Usage

### Tools Used
> Which AI tools did you use and how?

- [ ] ChatGPT - [How you used it]
- [ ] Claude - [How you used it]
- [ ] GitHub Copilot - [How you used it]
- [ ] Other: _____________

### Most Helpful AI Assistance
> What was the most valuable help from AI tools?

[Your response here]

### What You Validated/Changed
> What AI suggestions did you review, modify, or reject?

[Your response here]

---

## Setup Instructions

### Prerequisites
```bash
# List required software and versions
- Java 17+
- Maven 3.6+
- [Any other requirements]
```

### Build and Run
```bash
# Provide exact commands to run your project

# 1. Clone/navigate to project
cd [your-project-directory]

# 2. Build
mvn clean install

# 3. Run
mvn spring-boot:run

# 4. Test (if applicable)
mvn test
```

### API Base URL
```
http://localhost:8080
```

### Sample API Calls
> Provide example curl commands to test your implementation

```bash
# Create a suppression list
curl -X POST http://localhost:8080/api/suppression-lists \
  -H "Content-Type: application/json" \
  -d '{ ... }'

# Check suppression
curl -X POST http://localhost:8080/api/check-suppression \
  -H "Content-Type: application/json" \
  -d '{ ... }'

# Serve ad with suppression
curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{ ... }'
```

---

## Testing Approach

### Manual Testing
> How did you manually test your implementation?

[Describe your testing process]

### Automated Tests
> Did you write any automated tests?

- [ ] Yes - run with `mvn test`
- [ ] No

**Test Coverage**: [If yes, describe what you tested]

### Edge Cases Considered
> What edge cases did you think about?

1. [Edge case 1]
2. [Edge case 2]
3. [Edge case 3]

---

## Challenges and Solutions

### Main Challenges
> What were the biggest challenges you faced?

1. **Challenge**: [Description]
   **Solution**: [How you solved it]

2. **Challenge**: [Description]
   **Solution**: [How you solved it]

### What Would You Do Differently?
> With more time or in a real production setting, what would you change?

[Your response here]

---

## Time Breakdown

### Actual Time Spent
> How long did each part take you?

- **Part 1**: _____ minutes
- **Part 2**: _____ minutes
- **Part 3**: _____ minutes
- **Documentation**: _____ minutes
- **Testing/Debugging**: _____ minutes
- **Total**: _____ minutes

### Time Management Reflection
> Did you stay within 90-120 minutes? What took longer than expected?

[Your response here]

---

## Code Organization

### Project Structure
```
your-project/
├── src/
│   └── main/
│       └── java/
│           └── com/falcon/
│               ├── controller/
│               ├── service/
│               ├── model/
│               └── [other packages]
├── README.md
├── ARCHITECTURE.md (if Part 3)
├── pom.xml
└── mock-data/
```

### Key Files to Review
> Guide us to the most important files

1. **[File path]** - [What it does]
2. **[File path]** - [What it does]
3. **[File path]** - [What it does]

---

## Additional Notes

### Assumptions Made
> What assumptions did you make during implementation?

1. [Assumption 1]
2. [Assumption 2]

### Known Limitations
> What limitations or incomplete features should we be aware of?

1. [Limitation 1]
2. [Limitation 2]

### Highlights
> What are you most proud of in this submission?

[Your response here]

---

## Questions for Discussion

> What questions or topics would you like to discuss in the interview?

1. [Question or topic 1]
2. [Question or topic 2]

---

## Submission Checklist

Before submitting, ensure you have:

- [ ] All code compiles and runs
- [ ] README.md with setup instructions
- [ ] This SUBMISSION_TEMPLATE.md completed
- [ ] ARCHITECTURE.md (if you completed Part 3)
- [ ] Comments in complex code sections
- [ ] Tested with provided mock data
- [ ] Removed any sensitive information
- [ ] Clear commit messages (if using git)

---

**Thank you for your submission! We look forward to discussing your approach.**
