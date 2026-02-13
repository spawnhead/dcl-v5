#!/usr/bin/env bash
# Restart backend (Spring Boot on 8080). Run from repo root.
# Usage: bash scripts/restart-backend.sh
# Requires: JDK 21 (set JAVA_HOME if needed), docker for optional DB check.

set -e
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"
LOG_DIR="${REPO_ROOT}/logs"
mkdir -p "$LOG_DIR"
STAMP=$(date +%Y%m%d-%H%M)
LOG_FILE="${LOG_DIR}/restart-${STAMP}.log"

log() { echo "[$(date +%H:%M:%S)] $*" | tee -a "$LOG_FILE"; }

log "=== Restart backend (///Restart) ==="

# 1) JAVA_HOME / Java 21
if [ -z "$JAVA_HOME" ]; then
  export JAVA_HOME="/c/Program Files/Eclipse Adoptium/jdk-21.0.6.7-hotspot"
  export PATH="$JAVA_HOME/bin:$PATH"
  log "JAVA_HOME set to $JAVA_HOME"
fi
if ! java -version 2>&1 | tee -a "$LOG_FILE" | grep -q "21\."; then
  log "WARN: Java 21 not detected. Backend may fail. Check JAVA_HOME."
fi

# 2) Optional: ensure Postgres is up
if command -v docker >/dev/null 2>&1; then
  log "Docker: ensuring Postgres is up..."
  docker compose -f ops/docker-compose.yml up -d 2>&1 | tee -a "$LOG_FILE" || true
else
  log "Docker not found; skipping DB check."
fi

# 3) Free port 8080
if command -v lsof >/dev/null 2>&1; then
  PIDS=$(lsof -ti :8080 2>/dev/null || true)
  if [ -n "$PIDS" ]; then
    log "Killing process(es) on 8080: $PIDS"
    echo "$PIDS" | xargs kill -9 2>/dev/null || true
    sleep 2
  else
    log "Port 8080 is free."
  fi
else
  log "lsof not found; skipping port 8080 kill. If backend fails to bind, kill the process manually."
fi

# 4) Start backend (foreground; run in background from terminal or use nohup)
log "Starting backend (modern/backend, profile dev)..."
cd "$REPO_ROOT/modern/backend"
# Run in background so script can continue; redirect output to log
nohup ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev >> "$LOG_FILE" 2>&1 &
BACKEND_PID=$!
log "Backend started with PID $BACKEND_PID. Waiting for /v3/api-docs..."

# 5) Wait and verify
for i in {1..30}; do
  if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/v3/api-docs 2>/dev/null | grep -q 200; then
    log "PASS: Backend responded 200 on /v3/api-docs"
    echo "PASS" >> "$LOG_FILE"
    exit 0
  fi
  sleep 1
done

log "FAIL: Backend did not respond 200 within 30s. Check $LOG_FILE and process $BACKEND_PID"
echo "FAIL" >> "$LOG_FILE"
exit 1
