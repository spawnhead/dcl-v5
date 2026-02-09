Goal (incl. success criteria):
- Capture legacy screen snapshot for "Отчеты -> Маржа" with full parity artifacts (SNAPSHOT.md, HAR, payloads, screenshots) to enable 1:1 reproduction.

Constraints/Assumptions:
- Do not modify legacy code in `src/` unless necessary.
- Use Java 21, Spring Boot 3.5.x, Spring Modulith, Flyway, Postgres.
- Start each step by reading/updating this file.

Key decisions:
- First vertical slice domain: Country reference data (`DCL_COUNTRY`).
- Build tool: Maven.

State:
- Full E2E validated (2026-02-09): Docker Desktop + Postgres (docker compose up) + backend (JAVA_HOME=JDK 21, spring-boot:run) + OpenAPI /v3/api-docs + UI generate:api + npm run dev (Vite 5173/5174). Backend requires JDK 21 and flyway-database-postgresql for Postgres 16; Modulith event_publication/event_publication_archive tables via Flyway V3/V4. Actuator/health not exposed by default.
- Stage: development (local E2E). Production: not deployed; no production environment or release process yet.

Done:
- Completed Phase 1 QC (procedures, PK/UK, feature traceability).
- Scaffolded modern backend with Country aggregate, endpoints, Flyway migration, and Testcontainers integration test.
- Added Postgres docker-compose and deployment guide.
- Started modern UI with AG Grid consuming generated OpenAPI types.
- 2026-02-09: Added Cursor Project Rules (`.cursor/rules/*.mdc`) enforcing CONTINUITY workflow, bash-only, sources of truth, Modulith, Flyway, tests, docs discipline.
- 2026-02-09 Dev run: logs/ (dev-env-diagnostics, dev-backend-build, dev-backend-test, dev-backend-run, dev-ui-install, dev-ui-run, dev-db-up, dev-db-ps). Backend: build OK with JAVA_HOME=JDK 21; tests OK (CountryIntegrationTest skipped when Docker unavailable); run fails without Postgres. UI: npm install OK; generate:api requires backend on :8080; npm run dev OK (Vite 5173). Docker Desktop was not running; docker-compose fixed (removed version). DEPLOYMENT_GUIDE updated with prerequisites.
- 2026-02-09: Cursor Rules enforcement check. Verified `.cursor/rules/*.mdc` present; 000-continuity-always requires "read CONTINUITY.md" at start. Practical check: added Currency module (api/application/domain/infrastructure), no cross-module refs, Flyway V2__init_currency.sql from DDL, integration test, traceability comment in controller.
- 2026-02-09: Margin legacy snapshot captured (HAR + spec; screenshots pending capture).
- 2026-02-09 E2E dev1: docker info OK; docker compose -f ops/docker-compose.yml up -d OK; Postgres 16 up. Backend: added flyway-database-postgresql (Postgres 16 support), Currency noRound/sortOrder SMALLINT→Short + DTO conversion, Flyway V3/V4 (event_publication, event_publication_archive). Backend starts and serves /v3/api-docs, GET/POST /api/countries, GET/POST /api/currencies verified. UI: npm install, npm run generate:api, npm run dev OK. Logs: logs/dev1-*.
- 2026-02-09 UI: Vite proxy (/api, /v3, /swagger-ui → :8080); AG Grid 33 ModuleRegistry + AllCommunityModule, rowData fix. Countries grid displays real data from Postgres (CountryRepository.findAll()). Stage recorded: dev only, production not yet.
- Margin screen (Отчеты → Маржа): route /reports/margin and menu added; SNAPSHOT.md and payloads absent — parity implementation blocked. Created docs/screens/margin/ (SNAPSHOT stub, payloads/README, IMPLEMENTATION_NOTES.md), UI placeholder page with blocker message. Backend margin module and full UI deferred until Agent-Plan provides spec.
- 2026-02-09: Merged origin/main; resolved CONTINUITY.md and SNAPSHOT.md; committed margin snapshot (full spec, HAR, payloads); pushed main → origin/stage.

Now:
- (none)

Next:
- Agent-Dev 1 implements new Margin screen parity using SNAPSHOT.md.
- Agent-Dev executes Iteration 2 Units (см. docs/NEXT_SLICES_PLAN.md).
- Deep-dive into DAO/service layers for business rules and traceability.

Open questions (UNCONFIRMED if needed):
- Are any procedures critical for Country domain beyond ID assignment?
- Margin screen: SNAPSHOT.md and payloads were missing; spec must be added for 1:1 parity implementation.

Working set (files/ids/commands):
- docs/screens/margin/SNAPSHOT.md
- docs/screens/margin/network.har
- docs/screens/margin/payloads/**
- docs/screens/margin/screenshots/README.md
- docs/PROGRESS.md
- ops/docker-compose.yml
- modern/backend (JAVA_HOME=JDK 21; ./mvnw test; ./mvnw spring-boot:run)
- modern/ui (npm install; npm run generate:api; npm run dev), react-router-dom, features/margin, features/countries
- docs/screens/margin/ (SNAPSHOT.md stub, payloads/README, IMPLEMENTATION_NOTES.md)
- docs/DEPLOYMENT_GUIDE.md, docs/PROGRESS.md
- logs/dev1-*, logs/dev-margin-* (when spec exists)
