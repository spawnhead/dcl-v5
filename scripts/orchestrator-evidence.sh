#!/bin/bash
# orchestrator-evidence.sh - Generate evidence pack draft for next screens
# Usage: ./scripts/orchestrator-evidence.sh > logs/orchestrator-evidence-draft.md
# Generated: 2026-02-12

set -euo pipefail

echo "# EVIDENCE_NEXT_SCREENS_DRAFT.md"
echo ""
echo "> Auto-generated draft on $(date -u +"%Y-%m-%dT%H:%M:%SZ")"
echo "> Manual review and completion required."
echo ""
echo "---"
echo ""

# A) Modern status snapshot
echo "## A) Modern status snapshot"
echo ""

SCREENS_DIR="docs/screens"
for screen_dir in "$SCREENS_DIR"/*/; do
    screen_name=$(basename "$screen_dir")
    echo "### $screen_name"
    
    # Check for spec files
    for spec in SNAPSHOT CONTRACTS ACCEPTANCE BEHAVIOR_MATRIX TEST_DATA_SPEC QA_ROLE_PRESETS; do
        if [[ -f "$screen_dir${spec}.md" ]]; then
            echo "- ${spec}.md: present"
        else
            echo "- ${spec}.md: MISSING"
        fi
    done
    
    # Check for BLOCKED files
    blocked_count=$(find "$screen_dir" -name "*.BLOCKED.md" 2>/dev/null | wc -l)
    if [[ $blocked_count -gt 0 ]]; then
        echo "- BLOCKED files: $blocked_count"
        find "$screen_dir" -name "*.BLOCKED.md" -exec echo "  - {}" \;
    fi
    
    # Last 2 logs (by grep in logs/)
    echo "- Recent logs:"
    grep -l "$screen_name" logs/*.md logs/*.log 2>/dev/null | tail -2 | while read -r log; do
        echo "  - $log"
    done || echo "  - (none)"
    
    echo ""
done

echo "---"
echo ""

# B) Legacy JSP universe
echo "## B) Legacy screen universe (src/main)"
echo ""

echo "### JSP entrypoints (src/main/webapp/jsp/*.jsp)"
ls -1 src/main/webapp/jsp/*.jsp 2>/dev/null | xargs -I{} basename {} | head -30
echo "... (total: $(ls -1 src/main/webapp/jsp/*.jsp 2>/dev/null | wc -l))"
echo ""

echo "### AJAX JSPs (src/main/webapp/ajax/*.jsp)"
ls -1 src/main/webapp/ajax/*.jsp 2>/dev/null | xargs -I{} basename {}
echo ""

echo "### Dialogs (src/main/webapp/dialogs/*.jsp)"
ls -1 src/main/webapp/dialogs/*.jsp 2>/dev/null | xargs -I{} basename {}
echo ""

echo "---"
echo ""

# C) Struts action summary
echo "## C) Struts action universe"
echo ""

STRUTS_CONFIG="src/main/webapp/WEB-INF/classes/resources/struts/struts-config.xml"
if [[ -f "$STRUTS_CONFIG" ]]; then
    echo "### Form beans count"
    grep -c "<form-bean " "$STRUTS_CONFIG" || echo "0"
    echo ""
    
    echo "### Action mappings count"
    grep -c "<action " "$STRUTS_CONFIG" || echo "0"
    echo ""
    
    echo "### Top actions by forwards"
    # Extract action paths with multiple forwards
    grep -E '<action |<forward ' "$STRUTS_CONFIG" | \
        awk '/<action /{path=$0; gsub(/.*path="\/?([^"]+)".*/,"\\1",path)} /<forward /{count[path]++} END{for(p in count) if(count[p]>3) print count[p], p}' | \
        sort -rn | head -15
    echo ""
else
    echo "struts-config.xml not found at $STRUTS_CONFIG"
fi

echo "---"
echo ""

# D) Blockers
echo "## D) Blockers evidence"
echo ""

echo "### Screens with BLOCKED.md files"
find docs/screens -name "*.BLOCKED.md" -exec echo "- {}" \; 2>/dev/null
echo ""

echo "---"
echo ""

# E) Recent logs summary
echo "## E) Recent log files"
echo ""

echo "### Last 20 log files"
ls -lt logs/*.md logs/*.log 2>/dev/null | head -20 | awk '{print "- " $NF}'
echo ""

echo "---"
echo ""
echo "End of draft. Manual completion required for:"
echo "- ACCEPTANCE status (PASS/FAIL/UNKNOWN)"
echo "- Candidate flows analysis"
echo "- Connectivity metrics"
echo "- Detailed blocker requirements"
