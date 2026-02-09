# Progress

## Done
- 2026-02-09: Parsed Firebird DDL and documented schema inventory + Firebird→Postgres mapping.
- 2026-02-09: Scanned legacy Struts config, web.xml, and JSPs to build code map and feature inventory.
- 2026-02-09: Drafted initial domain analysis based on DDL and legacy UI/actions.
- 2026-02-09: Added Cursor Project Rules in `.cursor/rules/` (CONTINUITY workflow, bash-only, sources of truth, Modulith, Flyway, tests, docs).
- 2026-02-09: Dev validation: logs/ created; backend build and test pass with JAVA_HOME=JDK 21; integration test skips when Docker unavailable; backend run fails without Postgres; UI npm install and npm run dev OK; generate:api requires backend. docker-compose.yml fixed. DEPLOYMENT_GUIDE updated with prerequisites.
- 2026-02-09: **Cursor Rules enforcement check.** (1) Rules: `.cursor/rules/*.mdc` present (8 files). (2) In Cursor: Settings → Project Rules — rules should be visible there. (3) In a **new chat** ask: "Какие правила активны и что ты обязан сделать в начале любого шага?" — expected: first step is "прочитать CONTINUITY.md и при необходимости обновить". (4) Practical check: added Currency backend module (Modulith: api/application/domain/infrastructure), no direct cross-module dependencies, Flyway V2 from DDL, integration test, traceability in controller. If rules do not apply: check path `.cursor/rules/`, ensure `alwaysApply: true` in key rules, and Cursor version (some builds use `.cursor/` for rules — then duplicate .mdc there).
- 2026-02-09: Captured legacy Margin screen snapshot artifacts (spec, HAR placeholder, payload examples); screenshots pending capture.

## Now
- (none)

## Next
- Deep-dive into `net.sam.dcl.dao` and `net.sam.dcl.service` to extract business rules.
- Trace Actions → DAO/SQL → tables for each domain.
- Full E2E: Docker Desktop → compose up → backend run → generate:api → UI dev.

## Risks/Questions
- (none)
