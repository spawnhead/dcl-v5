#!/bin/sh
set -euo pipefail
openapi-typescript http://localhost:8080/v3/api-docs --output src/api/generated/schema.ts
