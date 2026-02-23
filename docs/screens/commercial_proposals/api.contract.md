# commercial_proposals — API contract status (legacy reverse)

## VERIFIED (static)
- Endpoint: `POST /CommercialProposalsAction.do`.
- Supported dispatches: `input`, `filter`, `edit`, `cloneLikeNewVersion`, `cloneLikeOldVersion`, `block`, `checkPrice`, `generateExcel`.
- List data source: SQL id `select-commercial_proposals` (`dcl_commercial_proposal_filter(...)`).
- Side-effect writes:
  - `block` → `commercial_proposal-update-block`.
  - `checkPrice` → `commercial_proposal-update-checkPrice` + cleanup via `MessageDAO.deleteConditionForContractMessagesForEconomist`.

## UNCONFIRMED (runtime wire)
- Exact request payload names for all hidden/system fields (page/sort/session-dependent values).
- Exact HTTP headers/cookies and exact error payload rendering.
- Exact Excel response headers (`Content-Disposition`, charset, filename encoding) in deployed legacy runtime.

## SQL constraint alignment
- Primary table family: `DCL_COMMERCIAL_PROPOSAL` (+ filter function/procedures referenced by SQL map).
- Mandatory request values must satisfy DB-level NOT NULL/length constraints (e.g., `CPR_NUMBER VARCHAR(20) NOT NULL` on save flows handled in edit screen).
