# Suppression List System - Java Home Assignment

## Overview

This repository contains the Falcon Protocol Suppression List System home assignment in Java. This is a focused technical assessment (90-120 minutes) designed to evaluate software engineering skills in the context of advertising technology and real-time systems.

## What's Included

### 📋 Assignment Materials
- **`ASSIGNMENT_INSTRUCTIONS.md`** - Complete assignment instructions and requirements
- **`EVALUATION_CRITERIA.md`** - Detailed grading rubric and expectations
- **`SUBMISSION_TEMPLATE.md`** - Template for organizing your submission

### 🗂️ Mock Data (`mock-data/`)
- **`sample_suppression_lists.json`** - Sample suppression lists for testing
- **`ad_server_config.json`** - Ad server configuration with banners
- **`test_ad_requests.json`** - Test cases for ad server integration

### 🚀 Spring Boot Boilerplate (`boilerplate/`)
- **Complete Maven project** with Spring Boot setup
- **`AdServer.java`** - Fully functional ad server (DO NOT MODIFY)
- **`SuppressionService.java`** - Your implementation space for suppression logic
- **`pom.xml`** - All dependencies configured
- **Model classes** - Banner, AdRequest, AdResponse, etc.
- **README.md** - Detailed integration guide

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA, VS Code, Eclipse)

### 1. Read the Instructions
```bash
# Start here - read the complete assignment
cat ASSIGNMENT_INSTRUCTIONS.md
```

### 2. Review Mock Data
```bash
# Examine the sample suppression lists
cat mock-data/sample_suppression_lists.json
```

### 3. Examine the Boilerplate
```bash
cd boilerplate/
cat README.md

# Run the boilerplate to see it working
mvn spring-boot:run
```

### 4. Start Implementing
- Begin with Part 1: CRUD operations
- Move to Part 2: Ad server integration
- Finish with Part 3: Advanced feature + architecture

## Assignment Structure

This is a **3-part assignment** (90-120 minutes with AI tools):

1. **Part 1 (30%)**: Core suppression system with REST API (~30-35 min)
2. **Part 2 (50%)**: Ad server integration with efficient lookups (~45-50 min)
3. **Part 3 (20%)**: One advanced feature + production architecture (~20-25 min)

## Key Philosophy

- **Time-Boxed**: Strict 90-120 minute timeframe
- **Quality Focus**: Complete fewer parts well rather than all parts poorly
- **Show Your Thinking**: Document your decisions and trade-offs
- **Use AI Tools**: We expect and encourage use of Claude, ChatGPT, Copilot, etc.
- **Production Mindset**: Think about scalability and real-world constraints

## Context: Suppression Lists in Advertising

Suppression lists are **advertiser-specific** collections of user identifiers (email hashes) that should not receive ads from that particular advertiser. They are crucial for:

- **Privacy Compliance** (GDPR, CCPA)
- **User Experience** (don't show acquisition ads to existing customers)
- **Brand Safety** (exclude competitors' employees)
- **Campaign Optimization** (exclude users who already converted)

### The Core Challenge

Each advertiser submits their own suppression lists at different times. Your system must efficiently determine which advertisers should be suppressed for any given user during real-time ad serving (< 10ms).

## Ad Network Hierarchy

```
Advertiser → Campaign → Banner
Publisher → Site → Placement

Ad Server connects Banners to Placements based on:
- Targeting rules
- Bid prices
- Suppression lists (your code!)
- Custom parameters
```

## Technical Constraints

- **Java 17** with Spring Boot framework
- **Maven** for dependency management
- **Local Development**: No cloud infrastructure required
- **Complete Boilerplate Provided**: Focus on suppression logic, not building ad server
- **Scalable Architecture**: Design should indicate production readiness

## Success Criteria

We're evaluating:
- **Functionality (40%)**: Does your code work as specified?
- **Architecture (30%)**: Is your design scalable and maintainable?
- **Problem Solving (20%)**: How do you optimize for performance?
- **Documentation (10%)**: Can others understand your approach?

## Time Management Tips

### Recommended Breakdown
- **Part 1**: 30-35 minutes (CRUD + storage choice)
- **Part 2**: 45-50 minutes (this is the core challenge!)
- **Part 3**: 20-25 minutes (pick easiest advanced feature)
- **Buffer**: 5-10 minutes (testing, README, polish)

### Pro Tips
- Start with in-memory storage for Part 1 if unsure
- Build the index structure FIRST in Part 2
- Pick the advanced feature you're most comfortable with
- Document as you go, don't save it for the end

## Getting Help

If you have questions during the assignment:
1. Make reasonable assumptions and document them
2. Focus on demonstrating your problem-solving approach
3. Explain your trade-offs and decision-making process

## Submission Structure

We recommend organizing your submission like this:
```
your-submission/
├── README.md                    # Setup instructions
├── ARCHITECTURE.md              # Design document (Part 3)
├── TOOL_USAGE.md               # (Optional) AI tools used
├── src/                        # Your implementation
│   └── main/
│       └── java/
│           └── com/falcon/
├── pom.xml                     # Maven configuration
└── mock-data/                  # Test data
```

## Important Notes

- **Use AI Tools**: Claude, ChatGPT, GitHub Copilot - we expect it!
- **No Perfect Solution**: We want to see your engineering approach
- **Document Everything**: Explain your reasoning in code and docs
- **Test Thoroughly**: Use the provided mock data
- **Think Production**: Consider scalability and reliability

## What Makes a Strong Submission?

✅ **Working code** that compiles and runs
✅ **Clear README** with setup instructions
✅ **Efficient algorithms** for Part 2 (< 10ms response)
✅ **Production thinking** in architecture document
✅ **Clean code** with good structure
✅ **AI tool transparency** - tell us what you used

## Common Pitfalls to Avoid

❌ Trying to implement everything - focus on quality
❌ Ignoring performance in Part 2 - this is critical
❌ Over-engineering Part 1 - simple is better
❌ Skipping documentation - we need to understand your thinking
❌ Not testing with provided data

## Example Timeline

**0-35 min**: Part 1 - CRUD API working with chosen storage
**35-85 min**: Part 2 - Efficient suppression check + ad integration
**85-105 min**: Part 3 - One feature implemented OR architecture doc
**105-120 min**: Testing, README, final polish

## Questions?

Check the `ASSIGNMENT_INSTRUCTIONS.md` for detailed requirements.
Check the `EVALUATION_CRITERIA.md` to understand how we'll grade.
Check the `boilerplate/README.md` for integration details.

Good luck! We're excited to see your approach to this real-world advertising technology challenge. 🚀
