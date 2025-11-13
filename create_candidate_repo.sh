#!/bin/bash

# Script to create a new candidate assignment repository from the template
# Usage: ./create_candidate_repo.sh <candidate-name> <github-username>

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if required arguments are provided
if [ "$#" -ne 2 ]; then
    echo -e "${RED}Error: Missing required arguments${NC}"
    echo "Usage: $0 <candidate-name> <github-username>"
    echo ""
    echo "Example: $0 jane-smith janesmith456"
    exit 1
fi

CANDIDATE_NAME=$1
GITHUB_USERNAME=$2
REPO_NAME="falcon-home-assignment-java-${CANDIDATE_NAME}"
ORG_NAME="falcon-protocol"

echo -e "${YELLOW}Creating Java assignment repository for candidate: ${CANDIDATE_NAME}${NC}"
echo "GitHub username: ${GITHUB_USERNAME}"
echo "Repository: ${ORG_NAME}/${REPO_NAME}"
echo ""

# Check if GitHub CLI is authenticated
if ! gh auth status &> /dev/null; then
    echo -e "${RED}Error: GitHub CLI is not authenticated${NC}"
    echo "Please run: gh auth login"
    exit 1
fi

# Create repository from template
echo -e "${YELLOW}Step 1: Creating repository from template...${NC}"
gh repo create "${ORG_NAME}/${REPO_NAME}" \
  --template "${ORG_NAME}/falcon-home-assignment-java" \
  --private \
  --description "Java home assignment (90-120 min) for ${CANDIDATE_NAME}"

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Repository created successfully${NC}"
else
    echo -e "${RED}✗ Failed to create repository${NC}"
    exit 1
fi

# Wait a moment for the repository to be fully created
sleep 2

# Grant candidate push access
echo -e "${YELLOW}Step 2: Granting access to candidate...${NC}"
gh api "repos/${ORG_NAME}/${REPO_NAME}/collaborators/${GITHUB_USERNAME}" \
  -X PUT \
  -f permission=push

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Access granted to ${GITHUB_USERNAME}${NC}"
else
    echo -e "${RED}✗ Failed to grant access${NC}"
    echo "You may need to manually invite the user from the repository settings"
fi

# Output summary
echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}Setup Complete!${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo "Repository URL: https://github.com/${ORG_NAME}/${REPO_NAME}"
echo ""
echo "Next steps:"
echo "1. The candidate should receive an invitation email"
echo "2. They can accept and access: https://github.com/${ORG_NAME}/${REPO_NAME}"
echo "3. They should clone: gh repo clone ${ORG_NAME}/${REPO_NAME}"
echo "4. Navigate to boilerplate/ and run: mvn spring-boot:run"
echo ""
echo -e "${YELLOW}Time estimate: 90-120 minutes${NC}"
echo "Parts: 1) CRUD (30%) 2) Ad Integration (50%) 3) Advanced Feature (20%)"
echo ""
echo -e "${YELLOW}After review, you can delete the repo with:${NC}"
echo "gh repo delete ${ORG_NAME}/${REPO_NAME} --yes"
echo ""
