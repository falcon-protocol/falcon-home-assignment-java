# Manual Testing Required

⚠️ **Important**: This system does not have Java 17 and Maven installed, so automated compilation testing could not be completed.

## Required Testing Steps

Before using this assignment with candidates, please complete the following tests on a system with **Java 17+** and **Maven 3.6+**:

### 1. Compilation Test

```bash
cd boilerplate/
mvn clean compile
```

**Expected Result**: BUILD SUCCESS with no compilation errors

**If this fails:**
- Check for any Java syntax errors
- Verify all imports are correct
- Ensure pom.xml dependencies are valid

### 2. Application Startup Test

```bash
mvn spring-boot:run
```

**Expected Result**:
```
====================================
Falcon Suppression System Started!
====================================
✓ AdServer loaded 6 banners
Loading suppression lists...
✓ Loaded 5 suppression lists
⚠ WARNING: Index building not implemented yet!
```

**If this fails:**
- Check port 8080 is available
- Verify mock data files are accessible
- Check application.properties configuration

### 3. Endpoint Smoke Tests

With application running:

```bash
# Health check - should return status UP
curl http://localhost:8080/api/health

# Banners - should return 6 banners
curl http://localhost:8080/api/banners

# Stats - should return statistics
curl http://localhost:8080/api/stats

# CRUD endpoints - should return 501 Not Implemented (this is correct!)
curl http://localhost:8080/api/suppression-lists

# Ad serving - should return a banner
curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{"placement_id":"placement_001","user_email_hash":"abc123"}'
```

### 4. Mock Data Loading Test

Check console output shows:
- ✅ 6 banners loaded successfully
- ✅ 5 suppression lists loaded successfully
- ✅ Warning about index not implemented (expected)

### 5. Code Structure Verification

Run these quick checks:

```bash
# Count Java files
find boilerplate/src -name "*.java" | wc -l
# Expected: 12 files

# Check for compilation issues
grep -r "TODO" boilerplate/src/main/java/com/falcon/service/SuppressionService.java
# Expected: Should find TODO markers for implementation
```

### 6. Integration Test

Test the full flow with a test user:

```bash
# This should work even before implementation (just won't suppress)
curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{
    "placement_id": "placement_001",
    "user_email_hash": "a1b2c3d4e5f6789abcdef123456789abcdef123456789abcdef123456789abcd"
  }'
```

**Expected**: Returns a banner (any banner, suppression not working yet is OK)

### 7. Template Repo Test

Test creating a candidate repo:

```bash
./create_candidate_repo.sh test-candidate test-username
```

**Expected**:
- Creates private repo successfully
- Grants access to user
- Shows success message with repo URL

### 8. Performance Baseline

Time the ad serving:

```bash
time curl -X POST http://localhost:8080/api/serve-ad \
  -H "Content-Type: application/json" \
  -d '{"placement_id":"placement_001","user_email_hash":"test"}'
```

**Expected**: < 100ms response time

## Common Issues to Check

### Port Conflicts
If port 8080 is in use:
```bash
lsof -ti:8080 | xargs kill
# Or change port in application.properties
```

### Maven Cache Issues
If dependencies fail:
```bash
rm -rf ~/.m2/repository
mvn clean install
```

### Java Version
Ensure Java 17 is active:
```bash
java -version  # Should show version 17+
```

## Checklist

Complete this checklist before deploying to candidates:

- [ ] Boilerplate compiles without errors (`mvn clean compile`)
- [ ] Application starts successfully (`mvn spring-boot:run`)
- [ ] All endpoints respond (even with 501 for unimplemented)
- [ ] AdServer loads 6 banners correctly
- [ ] SuppressionService loads 5 lists correctly
- [ ] Mock data is valid JSON
- [ ] Health check endpoint works
- [ ] Ad serving endpoint works
- [ ] Setup script works (`create_candidate_repo.sh`)
- [ ] Documentation is clear and complete
- [ ] README has accurate instructions
- [ ] TESTING_CHECKLIST.md is comprehensive

## Next Steps After Testing

1. ✅ Fix any compilation errors found
2. ✅ Update documentation if needed
3. ✅ Commit and push fixes
4. ✅ Test creating a candidate repo
5. ✅ Run through the assignment yourself (optional but recommended)
6. 🚀 Ready to assign to candidates!

## Quick Test Script

Save this as `quick_test.sh` and run it:

```bash
#!/bin/bash
set -e

echo "Testing Java Home Assignment..."

cd boilerplate/

echo "1. Compiling..."
mvn clean compile -q

echo "2. Starting application..."
mvn spring-boot:run &
APP_PID=$!

echo "3. Waiting for startup..."
sleep 15

echo "4. Testing health endpoint..."
curl -s http://localhost:8080/api/health | grep "UP" || echo "FAIL"

echo "5. Testing banners endpoint..."
curl -s http://localhost:8080/api/banners | grep "adv_" || echo "FAIL"

echo "6. Stopping application..."
kill $APP_PID

echo "✓ All basic tests passed!"
```

---

**Status**: ⚠️ Awaiting manual testing with Java 17 + Maven

**Repository**: https://github.com/falcon-protocol/falcon-home-assignment-java
