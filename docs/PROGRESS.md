# Progress

## Done
- 2026-02-09: Parsed Firebird DDL and documented schema inventory + Firebird→Postgres mapping.
- 2026-02-09: Scanned legacy Struts config, web.xml, and JSPs to build code map and feature inventory.
- 2026-02-09: Drafted initial domain analysis based on DDL and legacy UI/actions.
- 2026-02-09: Added Cursor Project Rules in `.cursor/rules/` (CONTINUITY workflow, bash-only, sources of truth, Modulith, Flyway, tests, docs).
- 2026-02-09: Dev validation: logs/ created; backend build and test pass with JAVA_HOME=JDK 21; integration test skips when Docker unavailable; backend run fails without Postgres; UI npm install and npm run dev OK; generate:api requires backend. docker-compose.yml fixed. DEPLOYMENT_GUIDE updated with prerequisites.
- 2026-02-09: Cursor Rules enforcement check; added Currency backend module (Modulith, Flyway V2, integration test).
- 2026-02-09: **Full E2E dev1.** Docker Desktop + Postgres (docker compose up); backend: flyway-database-postgresql, Currency SMALLINT→Short, Flyway V3/V4 (Modulith event tables); backend run OK; /v3/api-docs, /api/countries, /api/currencies verified; UI generate:api and npm run dev OK. Logs: logs/dev1-*.
- 2026-02-09: UI: Vite proxy for /api; AG Grid 33 ModuleRegistry; Countries grid shows real DB data. **Stage: development (local E2E). Production: not deployed.**

## Now
- (none)

## Next
- Agent-Dev Iteration 2 Units (docs/NEXT_SLICES_PLAN.md).
- Deep-dive into DAO/service for business rules and traceability.

## Risks/Questions
- (none)
