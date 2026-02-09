#!/bin/sh
set -euo pipefail
# Run from modern/ui; use npx so openapi-typescript is found from node_modules
npx openapi-typescript http://localhost:8080/v3/api-docs --output src/api/generated/schema.ts
