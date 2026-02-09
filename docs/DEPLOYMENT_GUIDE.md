# Deployment Guide (local)

1. Start Postgres:
   ```bash
   docker compose -f ops/docker-compose.yml up -d
   ```
2. Run Flyway + tests:
   ```bash
   cd modern/backend
   ./mvnw test
   ```
3. Start the backend:
   ```bash
   cd modern/backend
   ./mvnw spring-boot:run
   ```
4. Verify OpenAPI:
   ```bash
   curl http://localhost:8080/v3/api-docs
   ```
5. (Optional) Swagger UI:
   ```bash
   open http://localhost:8080/swagger-ui.html
   ```
6. Start the UI (after OpenAPI is available):
   ```bash
   cd modern/ui
   npm install
   npm run generate:api
   npm run dev
   ```
