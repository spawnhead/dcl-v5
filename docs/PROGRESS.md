# Progress

## Done
- 2026-02-11: **Agent-Plan TASK-0012 N3a1+N3a2 full parity spec expansion.** Полный 1:1 spec pack для `contractor_create` (5 вкладок) и `contract_spec_create` (2 вкладки, включая «Претензии») с расширением SNAPSHOT/CONTRACTS/ACCEPTANCE/BEHAVIOR_MATRIX/TEST_DATA_SPEC/QA_ROLE_PRESETS + HAR BLOCKED steps; явно зафиксирован spec gap vs dev gap. Лог: logs/plan-n3a1-n3a2-full-parity-spec-20260211-1140.md.
- 2026-02-12: **Agent-Debug TASK-0009 (N3a Save valid 400→200).** Root cause: Invalid UTF-8 (payload в CP1251); curl с UTF-8 payload → 200; CONTRACTS.md payload encoding; logs/debug-n3a-save-valid-20260212-1600.md — VERIFIED.
- 2026-02-12: **Agent-Debug TASK-0007 (N3a visual smoke).** Clean restart DB/backend/UI; все 4 страницы (/contracts/new, /contractors/new, /contracts/draft/specifications/new, /contracts/draft/attachments) открываются; нет placeholder; API 200. logs/debug-n3a-visual-smoke-20260212-1410.md — VERIFIED.
- 2026-02-12: **Agent-Dev TASK-0006 N3a missing blocks.** Плейсхолдеры убраны; таблица Спецификации + «Создать спецификацию» (N3a2); блок Прикреплённые файлы + «Прикрепить» (N3a3); кнопка «Добавить» у контрагента (N3a1). Backend: draft spec, draft attachments (session), contractors create. UI: ContractSpecCreatePage, ContractAttachmentsPage, ContractorCreatePage. Лог: logs/dev-n3a-missing-blocks-20260212-1345.md — VERIFIED.
- 2026-02-12: **Agent-Plan TASK-0005 N3a missing blocks.** Полные spec packs: contractor_create (N3a1), contract_spec_create (N3a2), contract_attachments (N3a3); N3a contract_create §6 Navigation. Лог: logs/plan-n3a-missing-blocks-specpack-20260212-1200.md.
- 2026-02-11: **Agent-Debug TASK-0004 (Grok UI not visible).** Правки Grok не были в файле; повторно применены (labelCol, Divider, секции Спецификации/Прикреплённые файлы). VERIFIED в браузере. logs/debug-grok-ui-not-visible-20260211-2320.md.
- 2026-02-11: **Agent-Plan TASK-0003 N3a parity audit.** Plan gap: кнопка «Добавить» у contractor; Dev gaps: таблица Спецификации, блок Прикреплённые файлы. Спеки пропатчены; stub packs contractor_create, contract_spec_create, contract_attachments. Лог: logs/plan-contract-create-parity-audit-20260211-2300.md.
- 2026-02-11: **Agent-Debug TASK-0001 (N3a contract_create).** Клик «Создать» не открывал форму — причина: backend 404 на create/open. Фикс: перезапуск backend. VERIFIED: /contracts → Создать → /contracts/new, форма «Создание договора», open 200. logs/debug-contract-create-navigation-20260211-2200.md, docs/AGENT_TASK_REPORTS.md.
- 2026-02-11: **Agent-Plan N3b contract_import_cp spec pack.** CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX, TEST_DATA_SPEC, QA_ROLE_PRESETS, payloads/network.har.BLOCKED.md. Traceability: SelectFromGridAction, CommercialProposalsAction, ContractAction.importCP. Лог: logs/plan-contract-import-cp-20260211-2100.md.
- 2026-02-11: **Agent-Dev N3a contract create.** GET /api/contracts/create/open, POST /api/contracts/create/save (валидация 1:1); UI /contracts/new — ContractCreatePage (форма по SNAPSHOT, Отмена/Сохранить, редирект, field errors). Лог: logs/dev-contract-create-20260211-1135.md — VERIFIED.
- 2026-02-11: **Agent-Plan N3a contract_create spec pack complete.** SNAPSHOT/CONTRACTS/ACCEPTANCE/BEHAVIOR_MATRIX/TEST_DATA_SPEC/QA_ROLE_PRESETS + payloads. Восстановлено из legacy. Лог: logs/plan-contract-create-20260211-1900.md.
- 2026-02-11: **Agent-Plan N3 spec patch (buttons).** SNAPSHOT/ACCEPTANCE/BEHAVIOR_MATRIX/CONTRACTS обновлены; созданы docs/screens/contract_create/, contract_import_cp/ (N3a/N3b). Лог: logs/plan-contracts-buttons-patch-20260211-1700.md.
- 2026-02-11: **N3 Contracts parity gap — кнопки «Импорт из КП» и «Создать».** Agent-Orchestrator: N3 reopened; specs (ACCEPTANCE §13–14, BEHAVIOR_MATRIX) дополнены; кнопки добавлены в ContractsPage; маршруты /contracts/new, /contracts/import-cp (placeholder). Лог: logs/orchestrator-contracts-parity-gap-20260211-1600.md.
- 2026-02-10: **N3 Contracts list реализован (backend + UI).** Модуль contracts: GET lookups, POST data/page/cleanAll; FAKE_SEEDED 60 строк по TEST_DATA_SPEC; UI /contracts, меню «Договора», фильтры, грид 15 стр., next/prev, crossed-cell. Сборка и тесты backend — OK. Browser-verify не выполнен: backend не стартует из‑за Flyway (миграции 10, dev seed marker). Лог: [logs/dev-contracts-20260210-1740.md](logs/dev-contracts-20260210-1740.md) — NOT VERIFIED.
- 2026-02-10: **Orders N2 parity gaps fixed + browser verified.** contractor_for_id filter, order_by=ord_number asc/desc, UI sort dropdown; verification log: [logs/dev-orders-parity-fix-20260210-1532.log](logs/dev-orders-parity-fix-20260210-1532.log).
- 2026-02-10: **N2 spec pack ready (Orders list).** Agent-Plan: chosen N2 = Orders (Заказы); docs/screens/orders/ created: SNAPSHOT.md, CONTRACTS.md, ACCEPTANCE.md, BEHAVIOR_MATRIX.md, payloads/README.md. Traceability to OrdersAction, OrdersForm, Orders.jsp, select-orders → DCL_ORDER_FILTER, editCloneChecker, blockChecker, style-checker. CONTINUITY Key decisions updated. Verified: payloads/list-request.json, list-response.json (example shapes); CONTRACTS "How to verify" for contract/spec UNCONFIRMED.
- 2026-02-10: **Margin testability pack ready.** Agent-Plan: CONTRACTS.md — UNCONFIRMED сведены к минимуму, добавлен раздел "How to verify" (dep_id, export, serverList filter, initial empty grid). Созданы TEST_DATA_SPEC.md (детерминированные seed: справочники, 25–40 строк margin data, маячки для onlyTotal/itog_*/get_not_block/view_*/пагинация), QA_ROLE_PRESETS.md (admin, manager, manager_chief, economist — X-Dev-* заголовки). SEED_DATA_PLAN.md — секция "Margin parity dataset" со ссылкой на TEST_DATA_SPEC. QA может проверять Margin по ACCEPTANCE/BEHAVIOR_MATRIX без угадываний.
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
- 2026-02-11: N3b contract_import_cp spec pack complete. Ready for Agent-Dev implementation.

## Done (margin-parity 2026-02-10)
- Margin parity: детерминированный dataset 35 строк (TEST_DATA_SPEC), пустая сессия при первой загрузке и после Сбросить всё; блокеры view_*, onlyTotal, cleanAll+get_not_block исправлены; lookups по TEST_DATA_SPEC/QA_ROLE_PRESETS. logs/dev-margin-parity-20260210-1200.log — VERIFIED.

## Done (dev-specs-align 2026-02-10)
- Dev infra aligned to ROLE_MODEL, DEV_BYPASS, SEED_DATA_PLAN, DEV_DASHBOARD_SPEC: dataMode по DCL_SETTING (DEV_SEED_VERSION); V11__init_dcl_setting, R__dev_seed_marker; X-Dev-Department-*; /api/me (name, department, chiefDepartment, authMode); DevStatusResponse (profile, serverTime, db.product/version); UI /dev блоки + Повторить; MarginController currentUser() hook. logs/dev-align-dev-specs-20260210-1120.log — VERIFIED.

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
