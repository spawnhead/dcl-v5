# Progress

## Done
- 2026-02-09: Parsed Firebird DDL and documented schema inventory + Firebird→Postgres mapping.
- 2026-02-09: Scanned legacy Struts config, web.xml, and JSPs to build code map and feature inventory.
- 2026-02-09: Drafted initial domain analysis based on DDL and legacy UI/actions.
- 2026-02-09: Added Cursor Project Rules in `.cursor/rules/` (CONTINUITY workflow, bash-only, sources of truth, Modulith, Flyway, tests, docs).
- 2026-02-09: Dev validation: logs/ created; backend build and test pass with JAVA_HOME=JDK 21; integration test skips when Docker unavailable; backend run fails without Postgres; UI npm install and npm run dev OK; generate:api requires backend. docker-compose.yml fixed. DEPLOYMENT_GUIDE updated with prerequisites.

## Now
- (none)

## Next
- Deep-dive into `net.sam.dcl.dao` and `net.sam.dcl.service` to extract business rules.
- Trace Actions → DAO/SQL → tables for each domain.
- Full E2E: Docker Desktop → compose up → backend run → generate:api → UI dev.

## Risks/Questions
- (none)
