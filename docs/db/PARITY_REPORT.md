# DB Parity Report: Postgres (Flyway) vs Firebird (baseline DDL)

**Date:** 2026-02-09  
**Baseline (source of truth):** `db/Lintera_dcl-5_schema.ddl` (Firebird)  
**Target:** Postgres 16, schema created by Flyway migrations in `modern/backend/src/main/resources/db/migration/*.sql`  
**Connection:** `ops/docker-compose.yml` → localhost:5432, DB=`dcl`, user=`dcl`

---

## 1) Summary

| Metric | Value |
|--------|--------|
| **Overall status** | **PARTIAL** |
| **Tables** | 2 / 96 domain tables present in Postgres (dcl_country, dcl_currency); 94 missing |
| **Columns** | For migrated tables: 100% parity (types/nulls mapped). Rest N/A (tables missing). |
| **PK** | 2 / 94 domain tables with PK in Postgres (2 migrated); 92 domain PKs missing. 3 system PKs present (event_publication, event_publication_archive, flyway_schema_history). |
| **UK** | 0 / 14+ unique constraints in Postgres for domain tables (Firebird has 14 UNIQUE INDEXes on domain tables); 0 domain UK in target. |
| **FK** | 0 / 0 (baseline declares no FK; target has none). |
| **Indexes** | 2 PK indexes for domain tables; 0 non-PK domain indexes. Baseline has 35 indexes (14 UNIQUE, 21 non-unique). |
| **Blockers count** | **6** (see Section 5) |

**Coverage (domain only):**  
- Tables: 2/96 (≈2%)  
- PK: 2/94 (≈2%)  
- UK: 0/14  
- Indexes (non-PK): 0/34  
- Sequences/identity: 2/85 generators mapped to identity (for the 2 tables).  
- Triggers: 0/127 (replaced by identity for migrated tables = MAPPED_EQUIVALENT; rest MISSING).  
- Views: 0/23 (MISSING).  
- Procedures: 0/231 (MISSING).

---

## 2) Baseline inventory (from Firebird DDL)

### 2.1 Tables (96)

DCL_1C_NUMBER_HISTORY, DCL_ACCOUNT, DCL_ACTION, DCL_ACTION_ROLE, DCL_ASM_LIST_PRODUCE, DCL_ASSEMBLE, DCL_ATTACHMENT, DCL_BLANK, DCL_BLANK_IMAGE, DCL_CATALOG_NUMBER, DCL_CATEGORY, DCL_CFC_LIST_PRODUCE, DCL_CFC_MESSAGE, DCL_COMMERCIAL_PROPOSAL, DCL_COND_FOR_CONTRACT, DCL_CONTACT_PERSON, DCL_CONTACT_PERSON_USER, DCL_CONTRACT, DCL_CONTRACTOR, DCL_CONTRACTOR_REQUEST, DCL_CONTRACTOR_USER, DCL_CONTRACT_CLOSED, DCL_CON_LIST_SPEC, DCL_CON_MESSAGE, **DCL_COUNTRY**, **DCL_CURRENCY**, DCL_CPR_LIST_PRODUCE, DCL_CPR_TRANSPORT, DCL_CRQ_ORD_LINK (no PK), DCL_CRQ_PRINT, DCL_CRQ_STAGE, DCL_CTC_LIST, DCL_CTC_PAY, DCL_CTC_SHP, DCL_CURRENCY_RATE, DCL_CUSTOM_CODE, DCL_CUS_CODE_HISTORY, DCL_DELIVERY_REQUEST, DCL_DEPARTMENT, DCL_DLR_LIST_PRODUCE, DCL_FIELD_COMMENT, DCL_FILES_PATH, DCL_INF_MESSAGE, DCL_INSTRUCTION, DCL_INSTRUCTION_TYPE, DCL_LANGUAGE, DCL_LOG, DCL_LPS_LIST_MANAGER, DCL_MONTAGE_ADJUSTMENT, DCL_MONTAGE_ADJUSTMENT_H, DCL_OPR_LIST_EXECUTED, DCL_ORDER, DCL_ORD_LIST_PAYMENT, DCL_ORD_LIST_PAY_SUM, DCL_ORD_LIST_PRODUCE, DCL_ORD_MESSAGE, DCL_OUTGOING_LETTER, DCL_PAYMENT, DCL_PAY_LIST_SUMM, DCL_PAY_MESSAGE, DCL_PRC_CTR_FOR_CALCSTATE, DCL_PRC_LIST_PRODUCE, DCL_PRODUCE, DCL_PRODUCE_COST, DCL_PRODUCE_COST_CUSTOM, DCL_PRODUCE_LANGUAGE, DCL_PRODUCTION_TERM, DCL_PURCHASE_PURPOSE, DCL_PURPOSE, DCL_RATE_NDS, DCL_READY_FOR_SHIPPING, DCL_REPUTATION, DCL_RESTS_IN_MINSK_TEMPORARY (no PK), DCL_ROLE, DCL_ROUTE, DCL_SELLER, DCL_SETTING, DCL_SHIPPING, DCL_SHP_DOC_TYPE, DCL_SHP_LIST_PRODUCE, DCL_SPC_LIST_PAYMENT, DCL_SPECIFICATION_IMPORT, DCL_SPI_LIST_PRODUCE, DCL_STUFF_CATEGORY, DCL_TERM_INCO, DCL_TEST, DCL_TIMEBOARD, DCL_TMB_LIST_WORK, DCL_UNIT, DCL_UNIT_LANGUAGE, DCL_USER, DCL_USER_LANGUAGE, DCL_USER_LINK, DCL_USER_ROLE, DCL_USER_SETTING, DCL_YEAR_NUM.

### 2.2 Key constraints (baseline)

- **Primary keys:** 94 tables with PK (89 single-column PK constraints + composite PKs: DCL_ACTION_ROLE(ACT_ID, ROL_ID), DCL_CTC_PAY(LPS_ID, LCC_ID), DCL_CTC_SHP(SHP_ID, LCC_ID), DCL_PRODUCE_LANGUAGE(PRD_ID, LNG_ID), DCL_UNIT_LANGUAGE(LNG_ID, UNT_ID), DCL_USER_LANGUAGE(USR_ID, LNG_ID), DCL_USER_ROLE(USR_ID, ROL_ID), DCL_YEAR_NUM(DCL_YEAR, DCL_TABLE)). Two tables without PK: DCL_CRQ_ORD_LINK, DCL_RESTS_IN_MINSK_TEMPORARY.
- **Unique constraints (from UNIQUE INDEX):** 14: DCL_ACTION_ACT_ACTION, DCL_BLANK_IDX_NAME_TYPE, DCL_CTN_INDEX_PRD_STF, DCL_CATEGORY_IDX1, DCL_CONTRACTOR_UNP_IDX, DCL_CRT_CUR_ID_DATE_IDX, DCL_FIELD_COMMENT_KEY, DCL_FLP_TABLE_NAME_IDX, DCL_RATE_RTN_DATE_FROM, DCL_SETTING_NAME, DCL_TMB_USR_ID_TMB_DATE, DCL_USER_SETTING_UN, plus composite unique indexes.
- **Foreign keys:** None declared in DDL.

### 2.3 Sequences/generators (baseline)

85 generators (see `docs/DB_SCHEMA_SUMMARY.md`). For DCL_COUNTRY: GEN_DCL_COUNTRY_ID. For DCL_CURRENCY: GEN_DCL_CURRENCY_ID.

### 2.4 Triggers (baseline)

127 triggers. For DCL_COUNTRY: DCL_COUNTRY_BI0, DCL_COUNTRY_BU0. For DCL_CURRENCY: DCL_CURRENCY_BIO. Most BI0 triggers perform GEN_ID(gen, 1) for PK assignment.

### 2.5 Views (baseline)

23 views (e.g. DCL_CUSTOM_CODE_V, DCL_OCCUPIED_*_V, DCL_LNT_*, DCL_SPEC_IN_CTC_V, DCL_SHP_USR_DEP_V).

### 2.6 Procedures (baseline)

231 stored procedures (per `docs/DB_FIREBIRD_TO_POSTGRES_MAPPING.md`). Not enumerated here; triage required per procedure.

---

## 3) Target inventory (Postgres)

### 3.1 Tables

| Schema | Table | Type | Owner |
|--------|--------|------|-------|
| public | dcl_country | table | dcl |
| public | dcl_currency | table | dcl |
| public | event_publication | table | dcl |
| public | event_publication_archive | table | dcl |
| public | flyway_schema_history | table | dcl |

**Domain tables (from baseline):** dcl_country, dcl_currency (names lowercased from DCL_COUNTRY, DCL_CURRENCY).  
**System tables (expected extra):** event_publication, event_publication_archive (Spring Modulith), flyway_schema_history (Flyway).

### 3.2 Columns (domain tables only)

**dcl_country:** cut_id (integer, identity, PK), cut_create_date (timestamp not null), usr_id_create (integer not null), cut_edit_date (timestamp not null), usr_id_edit (integer not null), cut_name (varchar(50) not null).  
**dcl_currency:** cur_id (integer, identity, PK), cur_name (varchar(10) not null), cur_no_round (smallint), cur_sort_order (smallint).

### 3.3 Constraints (target)

- dcl_country_pkey: PRIMARY KEY (cut_id)
- dcl_currency_pkey: PRIMARY KEY (cur_id)
- event_publication_pkey, event_publication_archive_pkey, flyway_schema_history_pk (system)

No unique constraints other than PK. No FK.

### 3.4 Indexes (target)

- dcl_country_pkey, dcl_currency_pkey (PK indexes)
- event_publication_pkey, event_publication_archive_pkey, flyway_schema_history_pk, flyway_schema_history_s_idx (system)

### 3.5 Sequences / identity

- dcl_country_cut_id_seq → dcl_country.cut_id (GENERATED BY DEFAULT AS IDENTITY)
- dcl_currency_cur_id_seq → dcl_currency.cur_id (GENERATED BY DEFAULT AS IDENTITY)

### 3.6 Triggers (target)

No custom triggers in public schema. ID generation handled by identity columns (MAPPED_EQUIVALENT to Firebird generator + BI0 trigger for the two migrated tables).

### 3.7 Routines (target)

No user-defined functions or procedures in public schema.

---

## 4) Parity findings

### 4.1 Tables

| Category | Detail |
|----------|--------|
| **MISSING** | 94 domain tables from baseline are absent in Postgres (all except DCL_COUNTRY, DCL_CURRENCY). |
| **EXTRA (expected)** | event_publication, event_publication_archive (Modulith), flyway_schema_history (Flyway). **Допустимо.** |

**Migrated 1:1 (name mapping lower case):** DCL_COUNTRY → dcl_country, DCL_CURRENCY → dcl_currency.

### 4.2 Columns

| Table | Status | Notes |
|-------|--------|--------|
| dcl_country | **MAPPED_EQUIVALENT** | All 6 columns present; types match (INTEGER→integer, TIMESTAMP→timestamp without time zone, VARCHAR(50)→varchar(50)). CUT_ID generation: Firebird generator+trigger → Postgres identity. |
| dcl_currency | **MAPPED_EQUIVALENT** | All 4 columns present; CUR_NO_ROUND/CUR_SORT_ORDER → cur_no_round/cur_sort_order (SMALLINT→smallint). CUR_ID: identity. |

No missing columns, no differing types/nullability/defaults for the two migrated tables.

### 4.3 Constraints

| Type | Baseline | Target | Status |
|------|----------|--------|--------|
| **PK** | 94 domain tables with PK | 2 domain tables with PK (dcl_country, dcl_currency) | **MISSING** 92 domain PKs (tables not yet migrated). For migrated tables: **MAPPED_EQUIVALENT** (PK_DCL_COUNTRY→dcl_country_pkey, PK_DCL_CURRENCY→dcl_currency_pkey). |
| **UK** | 14 unique indexes on domain tables | 0 domain UK | **MISSING** all 14 (no domain tables with UK migrated except PK). |
| **FK** | 0 | 0 | **N/A** (baseline has no FK). |

ON DELETE/ON UPDATE: not applicable (no FK in baseline).

### 4.4 Indexes

| Category | Detail |
|----------|--------|
| **MISSING** | All 34 non-PK indexes from baseline (on tables not yet in Postgres). For DCL_COUNTRY and DCL_CURRENCY the baseline has no separate indexes (only PK), so no missing indexes for the two migrated tables. |
| **MAPPED_EQUIVALENT** | PK indexes for dcl_country and dcl_currency. |

### 4.5 Sequences / identity

| Baseline | Target | Status |
|---------|--------|--------|
| GEN_DCL_COUNTRY_ID + DCL_COUNTRY_BI0 | dcl_country_cut_id_seq / identity on cut_id | **MAPPED_EQUIVALENT** |
| GEN_DCL_CURRENCY_ID + DCL_CURRENCY_BIO | dcl_currency_cur_id_seq / identity on cur_id | **MAPPED_EQUIVALENT** |
| 83 other generators | Not present | **MISSING** (expected until those tables are migrated). |

### 4.6 Triggers / procedures

| Object type | Baseline | Target | Status |
|-------------|----------|--------|--------|
| **Triggers** | 127 (ID assignment and other logic) | 0 custom | For dcl_country/dcl_currency: **MAPPED_EQUIVALENT** (identity replaces BI0). For rest: **MISSING** (expected). |
| **Procedures** | 231 | 0 | **MISSING** (triage and migration not yet done; may be replaced by application layer). |
| **Views** | 23 | 0 | **MISSING** (to be recreated or replaced by queries when needed). |

---

## 5) Blockers (must fix for full parity)

1. **94 domain tables missing** — Flyway migrations exist only for Country and Currency; remaining 94 tables must be added stepwise for parity.
2. **No unique constraints** on domain tables in Postgres — baseline has 14 UNIQUE INDEXes; when migrating those tables, equivalent UNIQUE constraints/indexes must be added.
3. **No views** — 23 Firebird views are absent; critical views (e.g. for reports/lookups) must be recreated or replaced by application queries.
4. **231 procedures not migrated** — business-critical procedures must be either reimplemented as Postgres functions/procedures or as application services, with decisions documented.
5. **Two baseline tables without PK** — DCL_CRQ_ORD_LINK and DCL_RESTS_IN_MINSK_TEMPORARY; when migrating, decide whether to add surrogate PK or keep as link/temp table without PK and document.
6. **Index coverage** — 34 non-PK indexes from baseline are missing; when each table is migrated, matching indexes (and unique where applicable) should be added for performance and uniqueness.

---

## 6) Suggested migrations (optional; no code changes in repo)

- **V5__…** — Next vertical slice: pick one domain (e.g. DCL_CURRENCY_RATE or DCL_UNIT) and add table + identity + PK from DDL; add any UK/index from baseline for that table.
- **V6__…** — Add remaining reference/simple tables in dependency order (e.g. DCL_LANGUAGE, DCL_UNIT, DCL_RATE_NDS), each with generator→sequence/identity and PK; add UNIQUE indexes where baseline has UNIQUE INDEX.
- **Views** — After base tables exist, add Flyway migrations that create Postgres VIEWs for each Firebird view that is still required (e.g. DCL_OCCUPIED_COUNTRY_V if used by Margin or other screens).
- **Procedures** — Triage 231 procedures; for each that must remain in DB, add Flyway migration with `CREATE FUNCTION`/`CREATE PROCEDURE` (PL/pgSQL); for the rest, document "replaced by application service" and add tests.

---

*Introspection output: `logs/db-target-introspection.out`. Connection: localhost:5432, db=dcl, user=dcl (from ops/docker-compose.yml and application.yml).*
