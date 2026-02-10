Goal (incl. success criteria):
- Finalize Margin screen specs (CONTRACTS + ACCEPTANCE [+ MATRIX]) based on existing snapshot artifacts to enable 1:1 implementation without guessing.

Constraints/Assumptions:
- Do not modify legacy code in `src/` unless necessary.
- Use Java 21, Spring Boot 3.5.x, Spring Modulith, Flyway, Postgres.
- Start each step by reading/updating this file.

Key decisions:
- N3 selected for current Agent-Plan cycle: Contracts list screen spec pack.
- First vertical slice domain: Country reference data (`DCL_COUNTRY`).
- Build tool: Maven.

State:
- Full E2E validated (2026-02-09): Docker Desktop + Postgres (docker compose up) + backend (JAVA_HOME=JDK 21, spring-boot:run) + OpenAPI /v3/api-docs + UI generate:api + npm run dev (Vite 5173/5174). Backend requires JDK 21 and flyway-database-postgresql for Postgres 16; Modulith event_publication/event_publication_archive tables via Flyway V3/V4. Actuator/health not exposed by default.
- Stage: development (local E2E). Production: not deployed; no production environment or release process yet.

Done:
- 2026-02-10: Agent-Plan prepared full N3 Contracts spec pack in `docs/screens/contracts/` (SNAPSHOT/CONTRACTS/ACCEPTANCE/BEHAVIOR_MATRIX/TEST_DATA_SPEC/QA_ROLE_PRESETS + payloads + screenshots README), with BLOCKED HAR capture notes and HOW TO VERIFY steps.
- 2026-02-10: Agent-Plan prepared spec package for Margin-first dev cycle: `docs/security/ROLE_MODEL.md`, `docs/security/DEV_BYPASS.md`, `docs/db/SEED_DATA_PLAN.md`, `docs/dev/DEV_DASHBOARD_SPEC.md`; defined role model, dev bypass headers + `/api/me`, seed marker/dataMode, and `/dev` dashboard contract.
- 2026-02-10: Done - Plan validation for Orders pack complete (`docs/screens/orders/*`): contracts/acceptance/matrix tightened to legacy 1:1, top-5 parity risks documented with verification steps, payload samples expanded for filters/sort/pagination/reload.
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
- 2026-02-09: Margin specs finalized (CONTRACTS + ACCEPTANCE + BEHAVIOR_MATRIX) and SNAPSHOT updated with links.
- 2026-02-09: Margin parity implemented (fake data): backend margin modulith (api/application/domain/infrastructure), GET/POST /api/margin/data, generate, cleanAll, export/excel, lookups; UI full screen (filters, 28-column grid, toolbar, row styles, loading/error) per ACCEPTANCE + BEHAVIOR_MATRIX; IMPLEMENTATION_NOTES checklist; logs/* placeholders.
- 2026-02-09: QA parity report produced (docs/screens/margin/QA_PARITY_REPORT.md): 3 blockers (view_* not wired, onlyTotal rules, cleanAll get_not_block), 2 non-blocking diffs, UNCONFIRMED for dynamic/error text.
- 2026-02-09: Margin QA blockers fixed: (1) view_* checkboxes in UI, viewFlags state init from response.view, columnDefs hide from viewFlags, Generate sends view; (2) onlyTotal auto-uncheck when !hasOneSelector (useEffect), onlyTotal checked → itog_by_spec unchecked; (3) cleanAll onSuccess setGetNotBlock(false). IMPLEMENTATION_NOTES and logs/dev-margin-blockers-fix-notes.log updated.
- 2026-02-09: Added Cursor rule 070-browser-verification.mdc: mandatory smoke-check in browser after UI/API changes; VERIFIED/NOT VERIFIED log in logs/dev-browser-check-*.log; procedure bash-only.
- 2026-02-09: Added Cursor rule 071-no-user-verification.mdc: Agent-Dev сам проверяет E2E в браузере; запрещено просить пользователя; доказательства в logs/dev-e2e-verify-*.log; Done только при VERIFIED или BLOCKED с причиной.
- 2026-02-09: Added Cursor rule 072-no-blank-screens.mdc: запрет пустых/сломанных страниц; smoke-check после UI-правок (Console, DOM, "page not blank"); лог logs/dev-ui-smoke-*.log; при FAIL — фикс или revert; fallback Loading/Error в UI.
- 2026-02-09: Margin real progress loader (no fake): useMarginProgress + MarginProgress UI; initial (5 lookups steps), generate (request/response/render), export (XHR onprogress + steps); downloadWithProgress.ts; log dev-margin-progress-loader-20260209.log; IMPLEMENTATION_NOTES updated.
- 2026-02-09: Added Cursor rule 073-fixed-dev-ports.mdc: порты фиксированы UI=5173, BE=8080, DB=5432; запрет тихого переезда; при занятом порте — lsof, kill или BLOCKED; vite.config.ts strictPort: true; лог logs/dev-ports-*.log.
- 2026-02-09: Added Cursor rule 074-java-21-mandatory.mdc: backend только JDK 21; перед build/test/run — which java, java -version, JAVA_HOME; Java 8/11/17 = BLOCKED; лог logs/dev-java-gate-*.log; Done только при PASS + mvnw test + spring-boot:run на :8080.

Now:
- (none)

Done:
- 2026-02-09 Agent-DB: DB parity report produced (Postgres vs Firebird baseline DDL). docs/db/PARITY_REPORT.md, logs/db-parity-20260209.log, logs/db-target-introspection.out. Status PARTIAL; 6 blockers (94 tables missing, no UK/views/procedures). Migrated tables dcl_country/dcl_currency: MAPPED_EQUIVALENT.
Done (dev-dashboard 2026-02-10):
- Dev Dashboard + Dev Identity + Data Mode: GET /api/dev/status (appName, version, activeProfiles, javaVersion, db, flyway, dataMode, authMode), GET /api/me; CurrentUserProvider + DevCurrentUserFilter (X-Dev-User, X-Dev-Roles), @Profile("dev"); Flyway db/dev V10__dev_seed.sql (dev_seed_marker), dataMode FAKE_SEEDED; UI /dev, menu Development, DevDashboardPage; DEPLOYMENT_GUIDE dev profile, X-Dev-* headers, dataMode. logs/dev-dev-dashboard-20260210-1019.log — VERIFIED (API + UI 200).
Done (dev-debug 2026-02-09):
- Проверка портов 5173/8080/5432 (заняты целевыми сервисами).
- Устранены блокеры: MarginService — добавлен import MarginExcelExport; MarginIntegrationTest — исправлена скобка в jsonPath; backend перезапущен с JDK 21. API margin отвечает 2xx; лог logs/dev-debug-20260209-1807.log — VERIFIED.

Next:
- Dev реализует Contracts 1:1 по спекам (`docs/screens/contracts/*`).
- Dev implement Orders parity.
- Agent-Dev implements `CurrentUser` + dev-only header bypass + `/api/me` per `docs/security/DEV_BYPASS.md`.
- Agent-Dev implements dev seed Flyway repeatables (`db/dev`) + `/api/dev/status` dataMode according to `docs/db/SEED_DATA_PLAN.md`.
- Agent-Dev builds `/dev` dashboard per `docs/dev/DEV_DASHBOARD_SPEC.md` and verifies Margin scenarios for admin/manager/economist roles.
- Agent-Dev implements margin module + UI 1:1 using these specs.
- Implement missing schema objects via Flyway migrations (as per docs/db/PARITY_REPORT.md blockers).
- Re-run QA (QA_PARITY_REPORT) for Margin screen.
- Replace fake margin data with real DB queries + preserve contracts.
- Agent-Dev executes Iteration 2 Units (см. docs/NEXT_SLICES_PLAN.md).
- Deep-dive into DAO/service layers for business rules and traceability.

Open questions (UNCONFIRMED if needed):
- Orders list: capture real legacy HAR for `/OrdersAction.do` scenarios (input/filter/reload/grid/block) to confirm exact wire payloads beyond code-derived examples.
- Margin: confirm whether action-level permissions (`DCL_ACTION_ROLE`/`dcl_user_actions`) affect Generate/Excel behavior beyond URL role gating.
- Margin: validate SQL-level row restrictions for manager vs admin/economist on identical filters (live DB check).


Working set (files/ids/commands):
- docs/screens/contracts/payloads/network.har.BLOCKED.md
- docs/screens/contracts/payloads/grid-fetch.response.json
- docs/screens/contracts/payloads/grid-fetch.request.json
- docs/screens/contracts/payloads/lookups.response.json
- docs/screens/contracts/payloads/lookups.request.json
- docs/screens/contracts/payloads/initial-load.response.json
- docs/screens/contracts/payloads/initial-load.request.json
- docs/screens/contracts/payloads/README.md
- docs/screens/contracts/SNAPSHOT.md
- docs/screens/contracts/CONTRACTS.md
- docs/screens/contracts/ACCEPTANCE.md
- docs/screens/contracts/BEHAVIOR_MATRIX.md
- docs/screens/contracts/TEST_DATA_SPEC.md
- docs/screens/contracts/QA_ROLE_PRESETS.md
- docs/screens/contracts/payloads/*.json
- docs/screens/contracts/screenshots/README.md
- docs/security/ROLE_MODEL.md
- docs/security/DEV_BYPASS.md
- docs/db/SEED_DATA_PLAN.md
- docs/dev/DEV_DASHBOARD_SPEC.md
- docs/screens/margin/CONTRACTS.md
- docs/screens/margin/ACCEPTANCE.md
- docs/screens/margin/BEHAVIOR_MATRIX.md
- docs/screens/margin/SNAPSHOT.md
- docs/PROGRESS.md
- docs/DEPLOYMENT_GUIDE.md, logs/dev-dev-dashboard-*.log
- db/Lintera_dcl-5_schema.ddl
- modern/backend/src/main/resources/db/migration/*.sql
- ops/docker-compose.yml
- docs/db/PARITY_REPORT.md (to be created)
- logs/db-schema-*.sql, logs/db-parity-*.log, logs/db-target-introspection.sql, logs/db-target-introspection.out
- modern/backend: ./mvnw test (JAVA_HOME=JDK 21, Docker for Testcontainers), ./mvnw spring-boot:run (Postgres up)
- modern/ui: npm install, npm run generate:api (backend on :8080), npm run dev → /reports/margin
- docs/screens/margin/IMPLEMENTATION_NOTES.md, logs/dev-margin-*, logs/dev-browser-check-*.log
- .cursor/rules/070-browser-verification.mdc, .cursor/rules/071-no-user-verification.mdc, .cursor/rules/072-no-blank-screens.mdc, .cursor/rules/073-fixed-dev-ports.mdc, .cursor/rules/074-java-21-mandatory.mdc
- logs/dev-e2e-verify-*.log, logs/dev-ui-smoke-*.log, logs/dev-ports-*.log, logs/dev-java-gate-*.log
- CONTINUITY.md, docs/PROGRESS.md
