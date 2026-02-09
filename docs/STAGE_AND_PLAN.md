# Stage & Plan

## Current Stage
**Prototype / Early MVP Foundation**

### Signals of Stage (facts)
| Signal | Evidence | Implication |
| --- | --- | --- |
| Modern backend scaffold exists | `modern/backend` with Spring Boot + Modulith + Flyway + OpenAPI | Prototype infrastructure in place |
| Modern UI scaffold exists | `modern/ui` with React/Vite + AG Grid | UI prototype started |
| Tests exist but not executed | `modern/backend/src/test/java/.../CountryIntegrationTest.java` and notes in docs | Not yet validated in CI |
| No CI/CD config found | No `.github/workflows` / CI files in repo | Manual testing only |
| No README / release docs | No project README at repo root | Discovery/Prototype stage |

## Near-Term Goals (1–3 iterations)
- Implement Iteration 2 (Units slice) end-to-end (DB → API → UI → tests).
- Implement Iteration 3 (Currencies + Rates slice) with master-detail behavior.
- Validate procedure logic for slices to avoid business-rule drift.

## Next Steps Plan (prioritized)

| Priority | Task | Effort | Owner | Dependencies | Definition of Done (DoD) |
| --- | --- | --- | --- | --- | --- |
| P0 | Iteration 2: Units slice | M | Agent‑Dev | DDL for DCL_UNIT | Flyway migration + module + API + UI + integration test merged |
| P0 | Run backend tests in network-enabled env | M | Agent‑Dev | Maven deps accessible | `./mvnw test` passes locally or in CI |
| P1 | Iteration 3: Currencies + Rates slice | M | Agent‑Dev | DDL + procedure review | Master-detail API + UI grid + integration test |
| P1 | Generate OpenAPI client for UI | S | Agent‑Dev | Backend running | `modern/ui/src/api/generated/schema.ts` regenerated from `/v3/api-docs` |
| P1 | Add CI pipeline (build + tests) | M | Agent‑Dev | Repo access | CI runs `./mvnw test` + UI build |
| P2 | Iteration 4: Contractors + contacts slice | L | Agent‑Dev | DDL + procedures | Module + API + UI + tests |
| P2 | Extend traceability (DAO/service → tables) | M | Agent‑Plan | Access to legacy code | Updated docs with explicit mappings |

## Metrics of Progress
- Number of vertical slices completed (Country + next slices).
- Backend integration tests passing (Testcontainers).
- UI screens connected to real OpenAPI client.
- CI coverage: build + tests on main branch.

## Risks / Assumptions
- **Assumption:** Business logic in stored procedures is either minimal or can be replicated in app layer; must confirm by reviewing DDL definitions.
- **Risk:** No CI/CD increases regression risk; mitigate by adding workflows after Iteration 2.
- **Risk:** External dependency access blocked in current environment (Maven/NPM 403); plan validation elsewhere.
