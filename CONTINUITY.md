Goal (incl. success criteria):
- Phase 2 scaffold: Spring Boot 3.5.x backend with Modulith, JPA, Flyway, OpenAPI; Postgres via docker-compose; initial vertical slice (Country) with 2-3 endpoints, integration test, and UI screen using generated OpenAPI client.

Constraints/Assumptions:
- Do not modify legacy code in `src/` unless necessary.
- Use Java 21, Spring Boot 3.5.x, Spring Modulith, Flyway, Postgres.
- Start each step by reading/updating this file.

Key decisions:
- First vertical slice domain: Country reference data (`DCL_COUNTRY`).
- Build tool: Maven.

State:
- Phase 2 scaffold validated: backend builds and tests pass (with JDK 21; Docker optional for integration test). UI installs and dev server runs. Postgres/backend run blocked without Docker Desktop.

Done:
- Completed Phase 1 QC (procedures, PK/UK, feature traceability).
- Scaffolded modern backend with Country aggregate, endpoints, Flyway migration, and Testcontainers integration test.
- Added Postgres docker-compose and deployment guide.
- Started modern UI with AG Grid consuming generated OpenAPI types.
- 2026-02-09: Added Cursor Project Rules (`.cursor/rules/*.mdc`) enforcing CONTINUITY workflow, bash-only, sources of truth, Modulith, Flyway, tests, docs discipline.
- 2026-02-09 Dev run: logs/ (dev-env-diagnostics, dev-backend-build, dev-backend-test, dev-backend-run, dev-ui-install, dev-ui-run, dev-db-up, dev-db-ps). Backend: build OK with JAVA_HOME=JDK 21; tests OK (CountryIntegrationTest skipped when Docker unavailable); run fails without Postgres. UI: npm install OK; generate:api requires backend on :8080; npm run dev OK (Vite 5173). Docker Desktop was not running; docker-compose fixed (removed version). DEPLOYMENT_GUIDE updated with prerequisites.

Now:
- (none)

Next:
- Start Docker Desktop → docker compose up → backend spring-boot:run → npm run generate:api → npm run dev for full E2E.
- Deep-dive into DAO/service layers for business rules and traceability.
- Expand vertical slice with update/delete and validated FK constraints.

Open questions (UNCONFIRMED if needed):
- Are any procedures critical for Country domain beyond ID assignment?

Working set (files/ids/commands):
- modern/backend/**
- modern/ui/**
- ops/docker-compose.yml
- docs/DEPLOYMENT_GUIDE.md
- .cursor/rules/000-continuity-always.mdc
- .cursor/rules/010-bash-only.mdc
- .cursor/rules/020-sources-of-truth.mdc
- .cursor/rules/030-architecture-modulith.mdc
- .cursor/rules/040-db-migrations-flyway.mdc
- .cursor/rules/050-tests-and-repro.mdc
- .cursor/rules/060-docs-discipline.mdc
- .cursor/rules/999-mdc-format.mdc
