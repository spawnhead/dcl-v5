Goal (incl. success criteria):
- Finalize Margin screen specs (CONTRACTS + ACCEPTANCE [+ MATRIX]) based on existing snapshot artifacts to enable 1:1 implementation without guessing.

Constraints/Assumptions:
- Do not modify legacy code in `src/` unless necessary.
- Use Java 21, Spring Boot 3.5.x, Spring Modulith, Flyway, Postgres.
- Start each step by reading/updating this file.

Key decisions:
- **DCL_CONTRACT_FILTER (2026-02-11):** Реализация в application layer; Postgres FUNCTION не создаётся. Детали: docs/db/DCL_CONTRACT_FILTER_DECISION.md.
- **N3 reopened (2026-02-11):** Parity gap — missing 2 buttons: «Импорт из КП» (ContractsAction.do?dispatch=selectCP&minsk_store=1), «Создать» (ContractAction.do?dispatch=input). Legacy: Contracts.jsp lines 123–128. Plan patch specs → Dev implement → QA re-verify.
- N3 selected for current Agent-Plan cycle: Contracts list screen spec pack.
- First vertical slice domain: Country reference data (`DCL_COUNTRY`).
- Build tool: Maven.
- **N2 screen (after Margin):** Orders list (Заказы — список заказов). Justification: maximum complexity among list screens (many filters, dependent lookups, role-based edit/block, grid from `dcl_order_filter`), high business value; full traceability in src (OrdersAction, OrdersForm, Orders.jsp, select-orders SQL, OrderDAO, validation.xml, xml-permissions). Alternative candidates: Contracts list, Shippings list, Payments list, Produce Cost report.

State:
- Full E2E validated (2026-02-09): Docker Desktop + Postgres (docker compose up) + backend (JAVA_HOME=JDK 21, spring-boot:run) + OpenAPI /v3/api-docs + UI generate:api + npm run dev (Vite 5173/5174). Backend requires JDK 21 and flyway-database-postgresql for Postgres 16; Modulith event_publication/event_publication_archive tables via Flyway V3/V4. Actuator/health not exposed by default.
- Stage: development (local E2E). Production: not deployed; no production environment or release process yet.

Done:
- 2026-02-12: Agent-Dev TASK-0025A Global UX feedback Cursor Rule: 080-ux-feedback-global.mdc — Skeleton/Spin на загрузке; message.loading/success/error на async/mutations; запрет пустого feedback после Save; ссылки на shared layer. logs/dev-cursor-rule-ux-feedback-20260212-1820.md.
- 2026-02-12: Agent-Dev TASK-0025 Global UX feedback: shared lib (feedback, api error normalization), ScreenLoader (Skeleton), applies to N3a — open Skeleton, save Message.loading→success/error. logs/dev-global-ux-feedback-20260212-1815.md — VERIFIED.
- 2026-02-12: Agent-Debug TASK-0024 N3a Save not persisting: list использовал fake data; ContractListProvider + Postgres для getData; save → list показывает новый договор. logs/debug-n3a-save-not-persisting-20260212-1850.md — VERIFIED.
- 2026-02-12: Agent-Debug TASK-0023 N3a contractor validation: Form.Item+Space.Compact не передавал value в Select. Fix: nested Form.Item noStyle. logs/debug-n3a-contractor-validation-20260212-1800.md — VERIFIED.
- 2026-02-11: Agent-Dev TASK-0022 Dev dashboard UI: Data mode заменён на «DB Source: Live DB (Postgres)» + «Seed dataset: V21»; backend seedDataset из Flyway; FAKE_SEEDED не отображается. logs/dev-ui-devmode-livedb-20260211-1630.md — VERIFIED.
- 2026-02-11: Agent-Debug TASK-0021 Clean restart after Postgres-only: DB/backend/UI перезапущены; Flyway V20/V21 в flyway_schema_history; curl open/save 200; browser /contracts/new, /contractors/new открываются. logs/debug-restart-postgres-only-n3a-n3a1-20260211-1620.md — VERIFIED.
- 2026-02-11: Agent-Dev TASK-0020 Postgres-only N3a/N3a1: contractor create + contract create читают и пишут из Postgres; V20 dcl_reputation, V21 dev seed; ContractorCreateService/ContractCreateService на JPA; LastCreatedContractorHolder удалён; newContractorId via SELECT. logs/dev-postgres-only-n3a-n3a1-20260211-1615.md — VERIFIED.
- 2026-02-12: Agent-Debug TASK-0019 N3a1+N3a contractor/contract save flow: Root cause — setSearchParams({}) в loadOpen вызывал refetch без newContractorId и сбрасывал contractor. Fix: удалён setSearchParams; contractor остаётся выбранным после return. logs/debug-n3a-contractor-contract-save-flow-20260212-1750.md — VERIFIED.
- 2026-02-12: Agent-QA TASK-0016 N3a1+N3a2 tab parity: **PASS (API)**. N3a1: open 200, 5 вкладок, save invalid 400, save valid 200. N3a2: open 200, 2 вкладки (Главная/Претензии), save invalid 400, save valid 200. Console 0 в браузере — ручная проверка. Лог: logs/qa-n3a1-n3a2-tabs-parity-20260212-1630.md.
- 2026-02-12: Agent-QA TASK-0015 N3a manual browser PASS (unblock TASK-0010): **PENDING_MANUAL**. Процедура ручной проверки в logs/qa-n3a-save-valid-manual-20260212-1620.md. Окружение доступно; автозаполнение не выполнено (snapshot без refs). N3a → PASS только после ручного подтверждения (save 200, редирект, Console 0).
- 2026-02-12: Agent-Debug TASK-0014 N3a/N3a1/N3a2 smoke-check: clean restart DB/backend/UI; все 3 страницы (/contracts/new, /contractors/new, /contracts/draft/specifications/new) открываются; open endpoints 200. logs/debug-n3a-n3a1-n3a2-smoke-20260212-1720.md — VERIFIED.
- 2026-02-12: Agent-Dev TASK-0013 N3a1+N3a2 full parity: contractor_create 5 табов, contract_spec_create 2 таба (Главная/Претензии); backend tabs + complaint; UI полные формы. Лог: logs/dev-n3a1-n3a2-full-parity-20260212-1505.md — VERIFIED.
- 2026-02-12: Agent-Debug TASK-0011 N3a1 contractor_create Save flow: LastCreatedContractorHolder + open(newContractorId) добавляет нового контрагента в lookup; ContractCreatePage передаёт newContractorId в open; Save → redirect → contractor доступен и выбран. logs/debug-contractor-create-save-flow-20260212-1700.md — VERIFIED.
- 2026-02-12: Agent-QA TASK-0010 N3a Save valid rerun (browser + Console 0): **BLOCKED**. API save 200 подтверждён (curl UTF-8). Браузерная проверка (fill → Сохранить → редирект, Console 0) не выполнена — MCP snapshot не вернул refs для формы. PASS возможен только после ручной проверки в DevTools. Лог: logs/qa-n3a-save-valid-rerun-20260212-1615.md.
- 2026-02-12: Agent-Debug TASK-0009 N3a Save valid 400→200: root cause — Invalid UTF-8 (payload в CP1251); curl с UTF-8 payload → 200; CONTRACTS.md payload encoding; logs/debug-n3a-save-valid-20260212-1600.md — VERIFIED.
- 2026-02-11: Agent-QA TASK-0008 N3a contract_create + child flows full re-verify: **FAIL**. Save valid (POST /api/contracts/create/save) возвращает 400 Bad Request при теле из payloads/save-request.json; Open/Save invalid/N3a1/N3a2/N3a3 (list/upload/delete) — 2xx по CONTRACTS. Console не проверялся (ручная проверка). Лог: logs/qa-n3a-contract-create-full-20260211-1115.md.
- 2026-02-12: Agent-Dev TASK-0006 N3a missing blocks: плейсхолдеры на /contracts/new убраны; таблица Спецификации + кнопка «Создать спецификацию» (N3a2); блок Прикреплённые файлы + «Прикрепить» (N3a3); кнопка «Добавить» у контрагента (N3a1). Backend: draft spec open/save, draft attachments list/upload/delete (session), contractors create open/save. UI: ContractSpecCreatePage, ContractAttachmentsPage, ContractorCreatePage; маршруты и return flow по CONTRACTS. Лог: logs/dev-n3a-missing-blocks-20260212-1345.md — VERIFIED.
- 2026-02-12: Agent-Debug TASK-0007: Clean restart + visual smoke-check N3a. Окружение: DB + backend (8080) + UI (5173). Все 4 страницы (/contracts/new, /contractors/new?returnTo=contract, /contracts/draft/specifications/new, /contracts/draft/attachments) открываются; нет placeholder; API 200. logs/debug-n3a-visual-smoke-20260212-1410.md — VERIFIED.
- 2026-02-12: Agent-Plan TASK-0005: contractor_create, contract_spec_create, contract_attachments — полные spec packs (SNAPSHOT, CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX, TEST_DATA_SPEC, QA_ROLE_PRESETS, payloads); N3a contract_create §6 Navigation. Лог: logs/plan-n3a-missing-blocks-specpack-20260212-1200.md.
- 2026-02-11: Agent-Plan TASK-0003 N3a parity audit: SNAPSHOT/ACCEPTANCE/BEHAVIOR_MATRIX/CONTRACTS пропатчены; stub packs contractor_create, contract_spec_create, contract_attachments. Plan gap: кнопка «Добавить»; Dev gaps: grid Спецификации, блок Прикреплённые файлы. Лог: logs/plan-contract-create-parity-audit-20260211-2300.md.
- 2026-02-11: Agent-Plan N3b contract_import_cp spec pack: CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX, TEST_DATA_SPEC, QA_ROLE_PRESETS, payloads/network.har.BLOCKED.md. Traceability: SelectFromGridAction, CommercialProposalsAction, ContractAction.importCP. Лог: logs/plan-contract-import-cp-20260211-2100.md.
- 2026-02-11: Agent-QA N3a contract_create: BLOCKED. GET /api/contracts/create/open возвращает 404 (backend на 8080 без N3a create endpoints в момент прогона). Код и payloads соответствуют CONTRACTS. Отчёт: logs/qa-contract-create-20260211-1945.md. Рекомендация: перезапуск backend и повторный QA.
- 2026-02-11: Agent-Debug TASK-0001: N3a contract_create «Создать» navigation — VERIFIED. Причина 404: backend (PID 41972) запущен без N3a. Фикс: перезапуск backend. Клик «Создать» → /contracts/new, форма «Создание договора», open 200. logs/debug-contract-create-navigation-20260211-2200.md, docs/AGENT_TASK_REPORTS.md.
- 2026-02-11: Agent-Dev N3a contract create: GET /api/contracts/create/open, POST /api/contracts/create/save с валидацией (required, con_final_date при !conReusable&&seller.id=1, maxlength). UI /contracts/new — ContractCreatePage (форма 1:1 по SNAPSHOT, Отмена/Сохранить, редирект после save, вывод ошибок). Лог: logs/dev-contract-create-20260211-1135.md — VERIFIED.
- 2026-02-11: Agent-Grok TASK-0002: N3a contract_create UI layout polished (horizontal form, sections, widths, alignment). Log: logs/grok-contract-create-ui-layout-20260211-0900.md — VERIFIED.
- 2026-02-11: Agent-Debug TASK-0004: Grok UI changes not visible — причина: правки не были в рабочей копии (rg: no matches). Повторно применены: labelCol/wrapperCol, Divider «Основные поля», секции Спецификации/Прикреплённые файлы. VERIFIED в браузере. logs/debug-grok-ui-not-visible-20260211-2320.md.
- 2026-02-11: Agent-Plan N3a contract_create spec pack complete: SNAPSHOT (форма 1:1), CONTRACTS (open/save), ACCEPTANCE (7 сценариев), BEHAVIOR_MATRIX, TEST_DATA_SPEC, QA_ROLE_PRESETS, payloads (open, save request/response). Восстановлено из legacy ContractAction/Contract.jsp/ContractForm/validation.xml. Лог: logs/plan-contract-create-20260211-1900.md.
- 2026-02-11: Agent-Dev Contracts DB migrations: Flyway V12–V19 (dcl_department, dcl_language, dcl_seller, dcl_user, dcl_user_language, dcl_contractor, dcl_contract, dcl_con_list_spec). High-priority parity gap закрыт. DCL_CONTRACT_FILTER: application layer (docs/db/DCL_CONTRACT_FILTER_DECISION.md). Лог: logs/dev-contracts-db-migrations-20260211-1109.md — VERIFIED.
- 2026-02-11: Agent-QA N3 Contracts buttons re-verify: PASS. Кнопки «Импорт из КП» и «Создать» в gridBottom, порядок 1:1, клики → /contracts/import-cp и /contracts/new. canCreate из /api/me (admin/economist/lawyer). Отчёт: logs/qa-contracts-buttons-20260211-1855.md.
- 2026-02-11: Agent-Dev N3 buttons parity: кнопки «Импорт из КП» и «Создать» перенесены в gridBottom (под гридом), порядок слева направо; «Создать» видна только при ролях admin/economist/lawyer (/api/me); клики → /contracts/import-cp и /contracts/new. Лог: logs/dev-contracts-buttons-20260211-1805.md — VERIFIED.
- 2026-02-11: Agent-Plan N3 spec patch (buttons): SNAPSHOT §3.2 исправлен («Импорт из КП», placement gridBottom, traceability); ACCEPTANCE §8–9 (Click «Создать», «Импорт из КП»); BEHAVIOR_MATRIX Verify-столбец; CONTRACTS §0 Navigation; созданы docs/screens/contract_create/, contract_import_cp/ (N3a/N3b SNAPSHOT). Лог: logs/plan-contracts-buttons-patch-20260211-1700.md.
- 2026-02-11: Agent-Orchestrator N3 parity gap fixed: reopened N3, spec patch (ACCEPTANCE §13–14, BEHAVIOR_MATRIX), Dev implemented «Импорт из КП» и «Создать» кнопки + routes /contracts/new, /contracts/import-cp. Orchestrator log: logs/orchestrator-contracts-parity-gap-20260211-1600.md. Browser: /contracts, /contracts/new, /contracts/import-cp load OK.
- 2026-02-11: Agent-QA N3 Contracts list parity: PASS (до обнаружения gap по кнопкам). Backend запущен (port 8080 освобождён, Flyway OK); GET lookups, POST data/page/cleanAll — 2xx, JSON по CONTRACTS и payloads; сценарии ACCEPTANCE/BEHAVIOR_MATRIX проверены. Отчёт: logs/qa-contracts-20260211-0940.md. Console — ручная проверка (Preserve log) рекомендована.
- 2026-02-10: N3 Contracts list реализован 1:1: backend (GET lookups, POST data/page/cleanAll, FAKE_SEEDED 60 строк по TEST_DATA_SPEC), UI (маршрут /contracts, меню «Договора», фильтры, грид 15 стр/стр, next/prev, crossed-cell). Сборка и тесты backend — OK. Browser-verify не выполнен: backend не запускается из‑за Flyway (миграции 10, dev seed marker). Лог: logs/dev-contracts-20260210-1740.md — NOT VERIFIED.
- 2026-02-10: Orders N2 parity gaps fixed: contractor_for_id filter (provider+service+tests), order_by=ord_number asc/desc (provider+tests), UI sort dropdown; browser-verified. logs/dev-orders-parity-fix-20260210-1532.log — VERIFIED.
- 2026-02-10: Agent-Plan N2 spec pack (Orders list): docs/screens/orders/ created — SNAPSHOT.md, CONTRACTS.md, ACCEPTANCE.md, BEHAVIOR_MATRIX.md, payloads/README.md; traceability to OrdersAction, OrdersForm, Orders.jsp, select-orders → DCL_ORDER_FILTER; UNCONFIRMED + "How to verify" in CONTRACTS; payloads/list-request.json, list-response.json (example shapes). PROGRESS.md updated. Agent-Plan verification pass: spec pack complete for 1:1 implementation.
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
- 2026-02-11: Added Cursor rule 075-mandatory-task-reporting.mdc: обязательный TASK ID (TASK-0001..) и отчет в docs/AGENT_TASK_REPORTS.md; в начале — Agent/Start, в конце — End/Done/Files/Artifacts/Status; при отсутствии ID — генерация по grep; Done только при заполненной секции и артефакте; anti-loop guard. Создан docs/AGENT_TASK_REPORTS.md.
- 2026-02-11: Added Cursor rule 076-agent-role-explicit.mdc: каждый промпт для агента MUST начинаться с явной роли ROLE: <Concrete role> (Seniority; Focus); формат и примеры (QA, Backend, UI/UX, Debug, Spec Analyst); при отсутствии ROLE — задача неполная.

Now:
- (none)

Done:
- 2026-02-10 Agent-Plan: Margin testability pack. CONTRACTS.md — UNCONFIRMED сведены к минимуму, добавлен раздел "How to verify" с точными шагами проверки в legacy (dep_id, export, serverList filter, initial empty grid). Созданы docs/screens/margin/TEST_DATA_SPEC.md (детерминированные seed: справочники, 25–40 строк margin data, маячки для onlyTotal/itog_*/get_not_block/view_*/пагинация), docs/screens/margin/QA_ROLE_PRESETS.md (admin, manager, manager_chief, economist — X-Dev-* заголовки и ожидания на Margin). SEED_DATA_PLAN.md дополнен секцией "Margin parity dataset" со ссылкой на TEST_DATA_SPEC. QA может проверять Margin по ACCEPTANCE/BEHAVIOR_MATRIX без угадываний.
- 2026-02-09 Agent-DB: DB parity report produced (Postgres vs Firebird baseline DDL). docs/db/PARITY_REPORT.md, logs/db-parity-20260209.log, logs/db-target-introspection.out. Status PARTIAL; 6 blockers (94 tables missing, no UK/views/procedures). Migrated tables dcl_country/dcl_currency: MAPPED_EQUIVALENT.
Done (margin-parity 2026-02-10):
- Margin parity: TEST_DATA_SPEC dataset (35 rows deterministic, lookups dev_admin/departments/contractors/routes/stuff); empty session on initial and after cleanAll; blockers view_*, onlyTotal rules, cleanAll get_not_block подтверждены в коде; QA_ROLE_PRESETS через X-Dev-* и /api/me. logs/dev-margin-parity-20260210-1200.log — VERIFIED (API).
Done (dev-specs-align 2026-02-10):
- Dev infra aligned to specs: dataMode по DCL_SETTING (DEV_SEED_VERSION, margin-v1); V11__init_dcl_setting.sql, R__dev_seed_marker.sql; DEV_BYPASS defaults admin, optional X-Dev-Department-*; /api/me contract (name, department, chiefDepartment, authMode); DevStatusResponse по DEV_DASHBOARD_SPEC (profile, serverTime, db.product/version, appliedMigrationsCount); UI /dev блоки + Повторить + CTA EMPTY; MarginController currentUser() hook; logs/dev-align-dev-specs-20260210-1120.log — VERIFIED.
Done (dev-dashboard 2026-02-10):
- Dev Dashboard + Dev Identity + Data Mode: GET /api/dev/status (appName, version, activeProfiles, javaVersion, db, flyway, dataMode, authMode), GET /api/me; CurrentUserProvider + DevCurrentUserFilter (X-Dev-User, X-Dev-Roles), @Profile("dev"); Flyway db/dev V10__dev_seed.sql (dev_seed_marker), dataMode FAKE_SEEDED; UI /dev, menu Development, DevDashboardPage; DEPLOYMENT_GUIDE dev profile, X-Dev-* headers, dataMode. logs/dev-dev-dashboard-20260210-1019.log — VERIFIED (API + UI 200).
Done (dev-debug 2026-02-09):
- Проверка портов 5173/8080/5432 (заняты целевыми сервисами).
- Устранены блокеры: MarginService — добавлен import MarginExcelExport; MarginIntegrationTest — исправлена скобка в jsonPath; backend перезапущен с JDK 21. API margin отвечает 2xx; лог logs/dev-debug-20260209-1807.log — VERIFIED.

Next:
- QA browser-check N3a missing blocks (при backend 8080 + UI 5173): сценарии Add contractor, Create spec, Attach file.
- ~~Исправить POST /api/contracts/create/save (400 на валидном теле)~~ — TASK-0009: причина UTF-8 encoding; curl 200 при UTF-8 payload; CONTRACTS дополнен; повторный QA TASK-0008 для PASS.
- Подключить ContractsService к JPA-репозиториям (при dataMode != FAKE) после появления таблиц V12–V19.
- Agent-Dev implements N2 (Orders list) 1:1 per docs/screens/orders/ (SNAPSHOT, CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX).
- Dev реализует Contracts 1:1 по спекам (`docs/screens/contracts/*`).
- Dev implement Orders parity.
- Agent-Dev implements `CurrentUser` + dev-only header bypass + `/api/me` per `docs/security/DEV_BYPASS.md`.
- Agent-Dev implements dev seed Flyway repeatables (`db/dev`) + `/api/dev/status` dataMode according to `docs/db/SEED_DATA_PLAN.md`.
- Agent-Dev builds `/dev` dashboard per `docs/dev/DEV_DASHBOARD_SPEC.md` and verifies Margin scenarios for admin/manager/economist roles (see `docs/screens/margin/QA_ROLE_PRESETS.md`).
- Agent-Dev implements margin module + UI 1:1 using specs; seed/data per `docs/screens/margin/TEST_DATA_SPEC.md` and `docs/db/SEED_DATA_PLAN.md` (Margin parity dataset).
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
- docs/screens/orders/*, modern/backend/**/orders/**, modern/ui/src/features/orders/**
- docs/screens/contracts/*, docs/screens/contract_create/, docs/screens/contract_import_cp/, modern/backend/**/contracts/**, modern/ui/src/features/contracts/** (SNAPSHOT, CONTRACTS, ACCEPTANCE, BEHAVIOR_MATRIX, N3a/N3b)
- docs/security/ROLE_MODEL.md, docs/security/DEV_BYPASS.md, docs/db/SEED_DATA_PLAN.md, docs/dev/DEV_DASHBOARD_SPEC.md
- docs/screens/margin/CONTRACTS.md, docs/screens/margin/ACCEPTANCE.md, docs/screens/margin/BEHAVIOR_MATRIX.md
- docs/screens/margin/TEST_DATA_SPEC.md, docs/screens/margin/QA_ROLE_PRESETS.md
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
- .cursor/rules/070-browser-verification.mdc, .cursor/rules/071-no-user-verification.mdc, .cursor/rules/072-no-blank-screens.mdc, .cursor/rules/073-fixed-dev-ports.mdc, .cursor/rules/074-java-21-mandatory.mdc, .cursor/rules/075-mandatory-task-reporting.mdc, .cursor/rules/076-agent-role-explicit.mdc, .cursor/rules/080-ux-feedback-global.mdc
- docs/AGENT_TASK_REPORTS.md
- logs/dev-e2e-verify-*.log, logs/dev-ui-smoke-*.log, logs/dev-ports-*.log, logs/dev-java-gate-*.log
- CONTINUITY.md, docs/PROGRESS.md

Update 2026-02-11 (TASK-0012):
- DONE: Expanded N3a1/N3a2 spec packs to full parity (tabs, fields, readonly/required/defaults, acceptance/matrix/contracts/test-data/role-presets, HAR BLOCKED instructions). Log: logs/plan-n3a1-n3a2-full-parity-spec-20260211-1140.md.
- GAP ATTRIBUTION: primary issue was spec gap; no new dev gap confirmed in this planning cycle.
- NEXT: Dev/QA execute against updated acceptance matrices; capture legacy HAR for UNCONFIRMED wire details.
