# Continuity Ledger

## 1. Project Overview
- Legacy Java ERM application in `src/` (Struts/JSP, DAOs, services).
- Modernization scaffold in `modern/backend` (Spring Boot Modulith) and `modern/ui` (React/Vite).
- DB source of truth: `db/Lintera_dcl-5_schema.ddl` with Phase 1 documentation in `docs/`.

## 2. Current Stage & Rationale
- **Stage:** Prototype / early MVP foundation.
- **Rationale:** modern backend/UI scaffolds exist, initial Country slice implemented, but no CI/CD and no executed tests or deployment evidence.

## 3. Current Focus (This Week)
- Align planning artifacts with current repo state.
- Prepare Iteration 2 execution handoff (Units slice).

## 4. Architecture Snapshot (high-level)
- **Legacy:** Struts + JSP + DAO/Service in `src/main`.
- **Modern backend:** Spring Boot 3.5.x Modulith + JPA + Flyway + OpenAPI in `modern/backend`.
- **Modern UI:** React 19 + Vite + Ant Design + AG Grid in `modern/ui`.
- **Ops:** Postgres docker-compose in `ops/docker-compose.yml`.

## 5. Key Decisions (ADR links if any)
- Vertical slice pattern: Country reference data as baseline module.
- Build tool: Maven for backend.
- Iteration roadmap: Units → Currencies+Rates → Contractors (see `docs/NEXT_SLICES_PLAN.md`).

## 6. Environments (dev/stage/prod) + how to run
- **Dev:** Local only.
  - Postgres: `docker compose -f ops/docker-compose.yml up -d`
  - Backend: `cd modern/backend && ./mvnw spring-boot:run`
  - OpenAPI: `http://localhost:8080/v3/api-docs`
  - UI: `cd modern/ui && npm install && npm run dev`
- **Stage/Prod:** Not defined.

## 7. CI/CD Status + Required Checks
- No CI/CD config found in repository.
- Required checks (manual for now): `./mvnw test` and UI build (`npm run build`) once network access allows dependency install.

## 8. Open Issues / Known Bugs
- External dependency downloads blocked in current environment (Maven Central / npm registry 403).
- OpenAPI types generation in UI is stubbed until backend runs locally.

## 9. Backlog Priorities (Top 10)
1. Iteration 2: Units slice (Flyway + backend + UI + tests).
2. Iteration 3: Currencies + Rates slice (master-detail + date query).
3. Iteration 4: Contractors & contacts slice.
4. Run backend tests with Testcontainers in a network-enabled environment.
5. Generate OpenAPI client from live backend and remove placeholder schema.
6. Add CI/CD pipeline (build + tests).
7. Extend traceability: DAO/service → tables for selected domains.
8. Confirm procedure logic for currency rate selection and other reference data.
9. Add FK constraints only after DAO verification.
10. Expand UI navigation beyond single screen.

## 10. Risks & Mitigations
- **Risk:** Business rules embedded in procedures/triggers may be missed.
  - **Mitigation:** inspect procedure definitions during each slice.
- **Risk:** No CI/CD; regressions undetected.
  - **Mitigation:** add workflows after Iteration 2.

## 11. Ownership & Contacts
- Not specified in repo (UNCONFIRMED).

## 12. Glossary / Domain Notes
- **Vertical slice:** DB migration + backend API + UI screen for one domain.
- **Reference data:** lookup tables such as countries, units, currencies.
