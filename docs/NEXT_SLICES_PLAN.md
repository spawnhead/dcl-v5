# Next Vertical Slices Plan

## 1) Candidate list (ranked shortlist, 5–10)

> Note: The DDL declares **no FK constraints**. Dependency notes below reflect the DDL fact and any implied dependencies are marked as assumptions only.【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】

1) **Units (DCL_UNIT)**
- Tables: `DCL_UNIT`.【F:docs/DB_SCHEMA_SUMMARY.md†L91-L97】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_UNIT_BI0` trigger; procedures `DCL_UNIT_INSERT`, `DCL_UNIT_PACK`.【F:docs/DB_SCHEMA_SUMMARY.md†L341-L342】【F:docs/DB_SCHEMA_SUMMARY.md†L591-L593】
- Legacy entry points: Units/Unit screens & DAO (`UnitsAction`, `UnitAction`, `UnitDAO`) in traceability map.【F:docs/FEATURE_INVENTORY.md†L64-L65】
- Minimal slice ops: list/get/create units.

2) **Routes (DCL_ROUTE)**
- Tables: `DCL_ROUTE`.【F:docs/DB_SCHEMA_SUMMARY.md†L81-L83】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_ROUTE_BI0` trigger (ID assignment); procedures not explicitly listed for routes in summary (assume check in DDL).【F:docs/DB_SCHEMA_SUMMARY.md†L321-L322】
- Legacy entry points: Routes/Route screens & DAO (`RoutesAction`, `RouteAction`, `RouteDAO`).【F:docs/FEATURE_INVENTORY.md†L64-L65】
- Minimal slice ops: list/get/create routes.

3) **Currencies + Rates (DCL_CURRENCY, DCL_CURRENCY_RATE)**
- Tables: `DCL_CURRENCY`, `DCL_CURRENCY_RATE`.【F:docs/DB_SCHEMA_SUMMARY.md†L41-L43】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_CURRENCY_BIO`, `DCL_CURRENCY_RATE_BI0`; procedures `DCL_CURRENCY_RATE_FOR_DATE`, `DCL_CURRENCY_RATE_MIN_DATE` (date-driven logic).【F:docs/DB_SCHEMA_SUMMARY.md†L263-L264】【F:docs/DB_SCHEMA_SUMMARY.md†L434-L436】
- Legacy entry points: `CurrenciesAction`, `CurrencyAction`, `CurrencyRatesAction`, `CurrencyRateAction` and DAOs `CurrencyDAO`, `CurrencyRateDAO`.【F:docs/FEATURE_INVENTORY.md†L63-L64】
- Minimal slice ops: list/get/create currency + list rates by currency/date.

4) **Departments (DCL_DEPARTMENT)**
- Tables: `DCL_DEPARTMENT`.【F:docs/DB_SCHEMA_SUMMARY.md†L45-L47】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_DEPARTMENT_BI0` trigger; `DCL_DEPARTMENT_FILTER` procedure indicates query logic. 【F:docs/DB_SCHEMA_SUMMARY.md†L271-L272】【F:docs/DB_SCHEMA_SUMMARY.md†L445-L446】
- Legacy entry points: reference data screens list includes departments (entry points exist in legacy UI).【F:docs/FEATURE_INVENTORY.md†L44-L48】
- Minimal slice ops: list/get/create departments.

5) **Sellers (DCL_SELLER)**
- Tables: `DCL_SELLER`.【F:docs/DB_SCHEMA_SUMMARY.md†L81-L84】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_SELLER_BI0` trigger (ID assignment).【F:docs/DB_SCHEMA_SUMMARY.md†L321-L323】
- Legacy entry points: reference data screens list includes sellers. 【F:docs/FEATURE_INVENTORY.md†L44-L48】
- Minimal slice ops: list/get/create sellers.

6) **Stuff categories (DCL_STUFF_CATEGORY)**
- Tables: `DCL_STUFF_CATEGORY`.【F:docs/DB_SCHEMA_SUMMARY.md†L91-L92】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_STUFF_CATEGORY_BI0` trigger; `DCL_STUFF_CATEGORY_FILTER` procedure. 【F:docs/DB_SCHEMA_SUMMARY.md†L335-L336】【F:docs/DB_SCHEMA_SUMMARY.md†L583-L584】
- Legacy entry points: reference data screens list includes stuff categories. 【F:docs/FEATURE_INVENTORY.md†L44-L48】
- Minimal slice ops: list/get/create categories.

7) **Rates NDS (DCL_RATE_NDS)**
- Tables: `DCL_RATE_NDS`.【F:docs/DB_SCHEMA_SUMMARY.md†L75-L78】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_RATE_NDS_BI0` trigger. 【F:docs/DB_SCHEMA_SUMMARY.md†L316-L317】
- Legacy entry points: NDS rates listed in reference data screens. 【F:docs/FEATURE_INVENTORY.md†L44-L48】
- Minimal slice ops: list/get/create NDS rate.

8) **Shipping doc types (DCL_SHP_DOC_TYPE)**
- Tables: `DCL_SHP_DOC_TYPE`.【F:docs/DB_SCHEMA_SUMMARY.md†L85-L87】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_SHP_DOC_TYPE_BI0` trigger. 【F:docs/DB_SCHEMA_SUMMARY.md†L325-L327】
- Legacy entry points: shipping document types listed in shipping features. 【F:docs/FEATURE_INVENTORY.md†L25-L29】
- Minimal slice ops: list/get/create doc type.

9) **Contractors & contacts (DCL_CONTRACTOR + DCL_CONTACT_PERSON)**
- Tables: `DCL_CONTRACTOR`, `DCL_CONTACT_PERSON`, `DCL_CONTACT_PERSON_USER` (association).【F:docs/DB_SCHEMA_SUMMARY.md†L23-L27】
- FK load: **none declared** in DDL (0 FK).【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- Triggers/procs: `DCL_CONTRACTOR_BI0` / `DCL_CONTACT_PERSON_BI0` triggers; procedures exist for contractor insert/load/filter and contact-person insert. 【F:docs/DB_SCHEMA_SUMMARY.md†L241-L243】【F:docs/DB_SCHEMA_SUMMARY.md†L409-L415】
- Legacy entry points: Contractors & Contractor Requests traceability map (`ContractorsAction`, `ContractorAction`, `ContractorDAO`, `ContractorRequestDAO`).【F:docs/FEATURE_INVENTORY.md†L66-L68】
- Minimal slice ops: list/get/create contractor + list contact persons by contractor.

## 2) Scoring summary (low/medium/high)

| Candidate | Dependency load | DB logic load | UI surface | Data risk | Pattern validation | Business coverage | Reuse value |
| --- | --- | --- | --- | --- | --- | --- | --- |
| Units | low | low (BI0 + simple procs) | low | low | high | medium | medium |
| Routes | low | low | low | low | medium | medium | medium |
| Currencies + Rates | medium (master-detail) | medium (rate procs) | medium | medium | high | high | high |
| Departments | low | low | low | low | medium | medium | medium |
| Sellers | low | low | low | low | medium | medium | medium |
| Stuff categories | low | medium (filter proc) | low | low | medium | medium | medium |
| Rates NDS | low | low | low | low | medium | medium | medium |
| Shipping doc types | low | low | low | low | medium | medium | medium |
| Contractors & contacts | medium | medium (insert/filter procs) | medium | medium | high | high | high |

**Scoring basis:** DDL has no FKs; dependency load is primarily table count and implied relationships only.【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】 DB logic load is based on presence of triggers/procedures listed in schema summary (e.g., BI0 triggers and domain procedures).【F:docs/DB_SCHEMA_SUMMARY.md†L240-L345】【F:docs/DB_SCHEMA_SUMMARY.md†L420-L615】

## 3) Selected roadmap (Iteration 2–4)

**Iteration 2 (lowest dependency):** **Units (DCL_UNIT)**
- Goal: repeat Country slice pattern with another simple reference table to stabilize migrations and API/UI template. 【F:docs/DB_SCHEMA_SUMMARY.md†L91-L97】

**Iteration 3 (medium complexity, master-detail):** **Currencies + Rates (DCL_CURRENCY + DCL_CURRENCY_RATE)**
- Goal: introduce master-detail endpoints + date-filtered queries, while staying within reference data domain. 【F:docs/DB_SCHEMA_SUMMARY.md†L41-L43】【F:docs/DB_SCHEMA_SUMMARY.md†L434-L436】

**Iteration 4 (optional, cross-entity):** **Contractors & contacts (DCL_CONTRACTOR + DCL_CONTACT_PERSON)**
- Goal: introduce multi-entity aggregate with association table and more UI surface. 【F:docs/DB_SCHEMA_SUMMARY.md†L23-L27】

## 4) Dev tasks per iteration (Agent‑Dev specs)

### Iteration 2: Units slice
**Scope tables**
- `DCL_UNIT` with fields mapped 1:1 to Postgres (confirm columns in DDL).【F:docs/DB_SCHEMA_SUMMARY.md†L91-L97】
- Triggers/procs to note: `DCL_UNIT_BI0`, `DCL_UNIT_INSERT`, `DCL_UNIT_PACK`.【F:docs/DB_SCHEMA_SUMMARY.md†L341-L342】【F:docs/DB_SCHEMA_SUMMARY.md†L591-L593】

**Flyway**
- Create `V2__init_unit.sql` with `dcl_unit` table and identity ID (replace generator + BI0 trigger).
- Add indexes/constraints present in DDL (none listed for unit in schema summary; verify in DDL).

**Backend module**
- Module `unit` (`api/application/domain/infrastructure`).
- Entity `Unit` + repository + service mirroring Country pattern.

**API**
- `POST /api/units` (create)
- `GET /api/units` (list)
- `GET /api/units/{id}` (get)

**UI**
- Single AG Grid screen “Units” with columns from `dcl_unit` (id, name/code, created/edited metadata as per DDL).

**Tests**
- 1 integration test with Testcontainers: create + list.
- 2–3 unit tests for validation (e.g., name required, length limits) if DDL indicates constraints.

**Traceability**
- Legacy entry points: `UnitsAction`, `UnitAction`, `UnitDAO` (see feature traceability).【F:docs/FEATURE_INVENTORY.md†L64-L65】

---

### Iteration 3: Currencies + Rates slice
**Scope tables**
- `DCL_CURRENCY`, `DCL_CURRENCY_RATE`.【F:docs/DB_SCHEMA_SUMMARY.md†L41-L43】
- Triggers/procs: `DCL_CURRENCY_BIO`, `DCL_CURRENCY_RATE_BI0`, `DCL_CURRENCY_RATE_FOR_DATE`, `DCL_CURRENCY_RATE_MIN_DATE`.【F:docs/DB_SCHEMA_SUMMARY.md†L263-L264】【F:docs/DB_SCHEMA_SUMMARY.md†L434-L436】

**Flyway**
- `V3__init_currency.sql` with `dcl_currency` + `dcl_currency_rate` tables.
- Use identity IDs; document any rate selection logic from procedures as app-layer methods.

**Backend module**
- Module `currency` with master-detail relationship; service methods for `getRateForDate` (if needed) matching legacy procedure intent.

**API**
- `POST /api/currencies`, `GET /api/currencies`, `GET /api/currencies/{id}`
- `POST /api/currencies/{id}/rates` (create rate)
- `GET /api/currencies/{id}/rates?date=` (list or get effective rate)

**UI**
- AG Grid screen for currencies, with side panel/grid for rates by selected currency.

**Tests**
- Integration test: create currency + rate, fetch rate by date.
- Unit tests for rate date selection logic (if implemented in app layer).

**Traceability**
- Legacy entry points: `CurrenciesAction`, `CurrencyAction`, `CurrencyRatesAction`, `CurrencyRateAction`, DAOs `CurrencyDAO`, `CurrencyRateDAO`.【F:docs/FEATURE_INVENTORY.md†L63-L64】

---

### Iteration 4 (optional): Contractors & contacts slice
**Scope tables**
- `DCL_CONTRACTOR`, `DCL_CONTACT_PERSON`, `DCL_CONTACT_PERSON_USER` (association).【F:docs/DB_SCHEMA_SUMMARY.md†L23-L27】
- Triggers/procs: contractor/contact-person BI0 triggers; contractor insert/load/filter procedures. 【F:docs/DB_SCHEMA_SUMMARY.md†L241-L243】【F:docs/DB_SCHEMA_SUMMARY.md†L409-L415】

**Flyway**
- `V4__init_contractor.sql` with tables above; add unique constraints per DDL if present (none listed in summary for these tables).

**Backend module**
- Module `contractor` with `Contractor` aggregate and `ContactPerson` child entity.

**API**
- `POST /api/contractors`, `GET /api/contractors`, `GET /api/contractors/{id}`
- `POST /api/contractors/{id}/contacts`, `GET /api/contractors/{id}/contacts`

**UI**
- AG Grid for contractors + nested grid for contact persons.

**Tests**
- Integration test: create contractor + contact person and list by contractor.
- Unit tests for name/identifier validation if enforced by DDL or legacy forms.

**Traceability**
- Legacy entry points: `ContractorsAction`, `ContractorAction`, `ContractorDAO` and contractor requests for related workflows. 【F:docs/FEATURE_INVENTORY.md†L66-L68】

## 5) Risks and assumptions
- **Assumption (UNCONFIRMED):** No FK constraints means relationships are enforced in app logic; confirm via DAO queries and legacy procedures before adding FK in migrations.【F:docs/DB_SCHEMA_SUMMARY.md†L713-L723】
- **Risk:** Some procedures (e.g., currency rate selection) embed business logic; must be inspected in DDL to avoid functional drift. 【F:docs/DB_SCHEMA_SUMMARY.md†L434-L436】
