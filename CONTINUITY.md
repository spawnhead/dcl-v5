Goal (incl. success criteria):
- Phase 2 scaffold: Spring Boot 3.5.x backend with Modulith, JPA, Flyway, OpenAPI; Postgres via docker-compose; initial vertical slices planned after Country.

Constraints/Assumptions:
- Do not modify legacy code in `src/` unless necessary.
- Use Java 21, Spring Boot 3.5.x, Spring Modulith, Flyway, Postgres.
- Start each step by reading/updating this file.

Key decisions:
- First vertical slice domain: Country reference data (`DCL_COUNTRY`).
- Build tool: Maven.

State:
- Roadmap for Iterations 2–4 drafted; awaiting Agent-Dev execution.

Done:
- Completed Phase 1 QC (procedures, PK/UK, feature traceability).
- Scaffolded modern backend with Country aggregate, endpoints, Flyway migration, and Testcontainers integration test.
- Added Postgres docker-compose and deployment guide.
- Started modern UI with AG Grid consuming generated OpenAPI types.
- Planned next 2–3 vertical slices with candidate scoring and Dev tasks.

Now:
- Planning next slices (completed) and handing off to Agent-Dev.

Next:
- Agent-Dev executes Iteration 2 (Units slice).

Open questions (UNCONFIRMED if needed):
- UNCONFIRMED: Are any procedures critical for Unit/Currency/Contractor slices beyond ID assignment and filters?

Working set (files/ids/commands):
- docs/NEXT_SLICES_PLAN.md
- docs/DB_SCHEMA_SUMMARY.md
- docs/FEATURE_INVENTORY.md
- docs/PROGRESS.md
