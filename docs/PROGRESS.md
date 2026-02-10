# Progress

## Done
- 2026-02-10: N3 Contracts spec pack ready: SNAPSHOT/CONTRACTS/ACCEPTANCE/BEHAVIOR_MATRIX/TEST_DATA_SPEC/QA_ROLE_PRESETS/payloads (`docs/screens/contracts/*`).
- 2026-02-10: Подготовлен пакет спецификаций Agent-Plan для Margin-first dev цикла: role model, dev bypass (`X-Dev-User`/`X-Dev-Roles` + `/api/me`), seed/dataMode plan, и `/dev` dashboard contract.
- 2026-02-09: Parsed Firebird DDL and documented schema inventory + Firebird→Postgres mapping.
- 2026-02-09: Scanned legacy Struts config, web.xml, and JSPs to build code map and feature inventory.
- 2026-02-09: Drafted initial domain analysis based on DDL and legacy UI/actions.
- 2026-02-09: Added Cursor Project Rules in `.cursor/rules/` (CONTINUITY workflow, bash-only, sources of truth, Modulith, Flyway, tests, docs).
- 2026-02-09: Dev validation: logs/ created; backend build and test pass with JAVA_HOME=JDK 21; integration test skips when Docker unavailable; backend run fails without Postgres; UI npm install and npm run dev OK; generate:api requires backend. docker-compose.yml fixed. DEPLOYMENT_GUIDE updated with prerequisites.
- 2026-02-09: **Cursor Rules enforcement check.** (1) Rules: `.cursor/rules/*.mdc` present (8 files). (2) In Cursor: Settings → Project Rules — rules should be visible there. (3) In a **new chat** ask: "Какие правила активны и что ты обязан сделать в начале любого шага?" — expected: first step is "прочитать CONTINUITY.md и при необходимости обновить". (4) Practical check: added Currency backend module (Modulith: api/application/domain/infrastructure), no direct cross-module dependencies, Flyway V2 from DDL, integration test, traceability in controller. If rules do not apply: check path `.cursor/rules/`, ensure `alwaysApply: true` in key rules, and Cursor version (some builds use `.cursor/` for rules — then duplicate .mdc there).
- 2026-02-09: Captured legacy Margin screen snapshot artifacts (spec, HAR placeholder, payload examples); screenshots pending capture.
- 2026-02-09: Cursor Rules enforcement check; added Currency backend module (Modulith, Flyway V2, integration test).
- 2026-02-09: **Full E2E dev1.** Docker Desktop + Postgres (docker compose up); backend: flyway-database-postgresql, Currency SMALLINT→Short, Flyway V3/V4 (Modulith event tables); backend run OK; /v3/api-docs, /api/countries, /api/currencies verified; UI generate:api and npm run dev OK. Logs: logs/dev1-*.
- 2026-02-09: UI: Vite proxy for /api; AG Grid 33 ModuleRegistry; Countries grid shows real DB data. **Stage: development (local E2E). Production: not deployed.**
- Margin screen (Reports → Margin): route /reports/margin and menu "Отчеты → Маржа" added; placeholder page (spec missing). docs/screens/margin/ created (SNAPSHOT stub, payloads/README, IMPLEMENTATION_NOTES.md). Backend margin module and full parity blocked until SNAPSHOT and payloads from Agent-Plan.
- 2026-02-09: Merge origin/main (conflicts in CONTINUITY.md, SNAPSHOT.md resolved); margin SNAPSHOT.md full spec + payloads + network.har committed; pushed main → origin/stage.
- 2026-02-09: Margin specs finalized with CONTRACTS + ACCEPTANCE + BEHAVIOR_MATRIX; SNAPSHOT linked to new artifacts.
- 2026-02-09: **Margin parity (fake data).** Backend: margin modulith (api/application/domain/infrastructure), endpoints /api/margin/data, generate, cleanAll, export/excel, lookups (users, departments, contractors, stuff-categories, routes); MarginFakeDataProvider 250 rows; MarginExcelExport (POI); MarginIntegrationTest. UI: MarginPage full screen (filters, 5 selectors + aspects, options, Сбросить/Сформировать/Excel, grid 28 cols, toolbar limit/pageSize/Обновить/Экспорт CSV, row styles itogLine/spc_group_delivery/haveUnblockedPrc, loading/error). IMPLEMENTATION_NOTES checklist DONE/PARTIAL; logs/* placeholders. Run backend with JDK 21 + Postgres; UI with npm run dev.
- 2026-02-09: **DB parity report (Agent-DB).** Postgres (Flyway) vs Firebird DDL: docs/db/PARITY_REPORT.md — PARTIAL; 2/96 domain tables; 6 blockers. logs/db-parity-20260209.log, logs/db-target-introspection.out.
- 2026-02-09: **QA parity report** (docs/screens/margin/QA_PARITY_REPORT.md): static check vs ACCEPTANCE/CONTRACTS/BEHAVIOR_MATRIX; 3 blockers (view_* not wired, onlyTotal rules, cleanAll get_not_block), 2 non-blocking diffs; dynamic/error UNCONFIRMED.

## Now
- (none)

## Done (dev-dashboard 2026-02-10)
- Dev Dashboard + Dev Identity + Data Mode: backend GET /api/dev/status, GET /api/me; CurrentUserProvider + X-Dev-User/X-Dev-Roles (@Profile("dev")); Flyway db/dev V10__dev_seed.sql, dataMode FAKE_SEEDED; UI /dev, DevDashboardPage, menu Development; DEPLOYMENT_GUIDE updated. Verification: logs/dev-dev-dashboard-20260210-1019.log — VERIFIED.

## Done (dev-debug 2026-02-09)
- Agent-Debug: проверка портов (5173/8080/5432), устранение блокеров сборки/запуска backend. Фиксы: MarginService import MarginExcelExport; MarginIntegrationTest скобка в jsonPath; перезапуск backend с JDK 21. API margin 2xx, proxy и страница margin 200. logs/dev-debug-20260209-1807.log — VERIFIED.

## Next
- Optional: в браузере открыть /dev и /reports/margin, проверить Console и Network.
- Implement missing schema objects via Flyway (per docs/db/PARITY_REPORT.md).
- Открыть в браузере /reports/margin, убедиться: Console 0 ошибок, Network /api/margin/* 2xx (рекомендуется).
- Agent-Dev fixes blockers from QA report (view_* wiring, onlyTotal rules, cleanAll get_not_block).
- Replace Margin fake data with real DB queries.
- Agent-Dev Iteration 2 Units (docs/NEXT_SLICES_PLAN.md).
- Deep-dive into DAO/service for business rules and traceability.

## Risks/Questions
- (none)
