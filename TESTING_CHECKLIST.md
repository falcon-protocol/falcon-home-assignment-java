# Testing Checklist for Java Home Assignment

This document provides a comprehensive checklist to verify the assignment works correctly.

## Prerequisites

Before testing, ensure you have:
- ✅ Java 17 or higher installed (`java -version`)
- ✅ Maven 3.6+ installed (`mvn -version`)
- ✅ Git installed
- ✅ A text editor or IDE (IntelliJ IDEA, VS Code, Eclipse)

## Part 1: Verify Project Structure

```bash
cd falcon-home-assignment-java

# Check all required files exist
ls -la README.md ASSIGNMENT_INSTRUCTIONS.md EVALUATION_CRITERIA.md
ls -la mock-data/
ls -la boilerplate/pom.xml
ls -la boilerplate/src/main/java/com/falcon/
```

**Expected**: All files should exist, no missing files.

## Part 2: Verify Maven Build

```bash
cd boilerplate/

# Clean and compile
mvn clean compile

# Expected output should end with:
# [INFO] BUILD SUCCESS
```

**What to check:**
- ✅ No compilation errors
- ✅ All Java files compile successfully
- ✅ Dependencies download correctly
- ✅ Build time reasonable (< 2 minutes)

## Part 3: Run the Application

```bash
# Still in boilerplate/ directory
mvn spring-boot:run

# Wait for startup...
# Expected output should include:
# ====================================
# Falcon Suppression System Started!
# ====================================
```

**What to check:**
- ✅ Application starts without errors
- ✅ Port 8080 is available (or change in application.properties)
- ✅ AdServer loads banners (should see: "✓ AdServer loaded 6 banners")
- ✅ SuppressionService initializes (should see: "Loading suppression lists...")

## Part 4: Test Endpoints

Keep the application running and open a new terminal.

### Test 1: Health Check
```bash
curl http://localhost:8080/api/health

# Expected:
# {"status":"UP","service":"Falcon Suppression System"}
```

### Test 2: Get Banners (Debug Endpoint)
```bash
curl http://localhost:8080/api/banners

# Expected: JSON with 6 banners from 3 advertisers
```

### Test 3: Stats Endpoint
```bash
curl http://localhost:8080/api/stats

# Expected: JSON with statistics (may be mostly 0s before implementation)
```

### Test 4: CRUD Endpoints (Will return 501 Not Implemented)
```bash
# These should return 501 until candidate implements them
curl http://localhost:8080/api/suppression-lists

# Expected:
# {"error":"This endpoint is not implemented yet..."}
```

### Test 5: Suppression Check (Will return 501 Not Implemented)
```bash
curl -X POST http://localhost:8080/api/check-suppression \
  -H "Content-Type: application/json" \
  -d '{"email_hash":"a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"}'

# Expected:
# {"error":"This endpoint is not implemented yet..."}
```

### Test 6: Ad Serving (Should work with warning)
```bash
curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{
    "placement_id": "placement_001",
    "user_email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"
  }'

# Expected: Should return a banner (any banner, suppression not working yet)
# Console should show: "⚠ Warning: Suppression check not implemented"
```

## Part 5: Verify Mock Data Loading

Check application console output for:

```
✓ AdServer loaded 6 banners
Loading suppression lists...
✓ Loaded 5 suppression lists
⚠ WARNING: Index building not implemented yet!
```

**What to check:**
- ✅ 6 banners loaded from ad_server_config.json
- ✅ 5 suppression lists loaded from sample_suppression_lists.json
- ✅ Warning about index not implemented (this is expected)

## Part 6: Code Review Checklist

### Check Model Classes
```bash
# Navigate through the code
cat boilerplate/src/main/java/com/falcon/model/SuppressionList.java
cat boilerplate/src/main/java/com/falcon/model/Banner.java
```

**Verify:**
- ✅ All fields have getters/setters
- ✅ Classes are properly documented
- ✅ JSON annotations present (@JsonProperty)

### Check Controllers
```bash
cat boilerplate/src/main/java/com/falcon/controller/SuppressionController.java
cat boilerplate/src/main/java/com/falcon/controller/AdController.java
```

**Verify:**
- ✅ All endpoints defined with @PostMapping/@GetMapping etc.
- ✅ Error handling for 501 Not Implemented
- ✅ Request validation present

### Check Services
```bash
cat boilerplate/src/main/java/com/falcon/service/AdServer.java
cat boilerplate/src/main/java/com/falcon/service/SuppressionService.java
```

**Verify:**
- ✅ AdServer is complete (has implementation)
- ✅ SuppressionService has TODO markers
- ✅ Both services have @Service annotation

## Part 7: Verify Mock Data

```bash
# Check JSON files are valid
cat mock-data/sample_suppression_lists.json | python -m json.tool
cat mock-data/ad_server_config.json | python -m json.tool
cat mock-data/test_ad_requests.json | python -m json.tool
```

**Verify:**
- ✅ All JSON files are valid
- ✅ sample_suppression_lists.json has 5 lists
- ✅ ad_server_config.json has 6 banners, 3 placements
- ✅ test_ad_requests.json has 5 test cases

## Part 8: Test Data Integrity

### Verify Suppression Lists
Check that lists contain the right advertisers:
- List 1 & 4: adv_techcorp
- List 2 & 5: adv_gamestudio
- List 3: adv_luxurystore

### Verify Test Cases
The test_ad_requests.json should have:
- Test 1: User suppressed by TechCorp AND LuxuryStore
- Test 2: User suppressed by GameStudio only
- Test 3: User not suppressed by anyone
- Test 4: User suppressed by TechCorp (Competitors list)
- Test 5: User suppressed by GameStudio (Privacy Opt-Outs)

## Part 9: Documentation Review

```bash
# Check all documentation exists and is readable
cat README.md
cat ASSIGNMENT_INSTRUCTIONS.md
cat EVALUATION_CRITERIA.md
cat SUBMISSION_TEMPLATE.md
cat boilerplate/README.md
```

**Verify:**
- ✅ Clear instructions in all files
- ✅ No typos or broken links
- ✅ Time estimates are accurate (90-120 min)
- ✅ Grading rubric is clear

## Part 10: Create Candidate Repo Test

Test the setup script:

```bash
# Test script exists and is executable
ls -la create_candidate_repo.sh
./create_candidate_repo.sh

# Should show usage instructions
```

**To fully test** (only if you want to create a test repo):
```bash
./create_candidate_repo.sh test-candidate test-github-username
```

This will:
- Create a private repo from the template
- Grant access to the user
- Show next steps

## Part 11: Performance Baseline

Before candidates optimize, document baseline performance:

```bash
# Start app
mvn spring-boot:run

# In another terminal, time the ad serving
time curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{"placement_id": "placement_001", "user_email_hash": "abc123"}'
```

**Expected:**
- App startup: < 30 seconds
- Ad serving (without suppression): < 100ms

## Issues and Solutions

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Maven dependencies won't download
**Solution**: Check internet connection, clear Maven cache:
```bash
rm -rf ~/.m2/repository
mvn clean install
```

### Issue: Java version mismatch
**Solution**: Ensure Java 17+ is active:
```bash
# Check version
java -version

# On macOS with multiple Java versions:
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### Issue: Mock data not loading
**Solution**: Check file paths are correct or copy to resources:
```bash
cp -r mock-data/ boilerplate/src/main/resources/
```

## Success Criteria

✅ All checks in this document pass
✅ Application compiles without errors
✅ Application starts and runs
✅ All endpoints respond (even if returning 501)
✅ Mock data loads correctly
✅ Documentation is clear and complete
✅ Setup script works

## Final Verification Command

Run this comprehensive test:

```bash
cd boilerplate/

# Build and run tests
mvn clean verify

# Start application
mvn spring-boot:run &

# Wait for startup
sleep 10

# Quick smoke test
curl http://localhost:8080/api/health
curl http://localhost:8080/api/banners
curl http://localhost:8080/api/stats

# Stop application
pkill -f spring-boot:run

echo "✓ All basic tests passed!"
```

---

**Note**: Most CRUD and suppression check endpoints will return 501 Not Implemented until the candidate implements them. This is expected and correct behavior for the boilerplate.
