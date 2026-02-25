# OPEN_QUESTIONS_MASTER

Consolidated unresolved items for ACTIVE documentation scope (MVP + Phase 1).

## commercial_proposal_edit
- 1. **Route/action/dispatch catalog verified**:
- 2. **DAO/SQL trace verified**:
- 3. **Business and UI post-effects verified in code**:
- ## UNCONFIRMED (needs runtime HAR)
- 1. Exact request body shape for each AJAX dispatch in real browser session.
- 2. Exact server error model for validation failures in `process` and attachment/print failures.
- 3. Exact response payload format for each AJAX endpoint (plain text fragments vs HTML partial variants across environments).
- ## BLOCKER report (runtime)
- 1. Bring up legacy servlet runtime; run checklist B dispatches end-to-end.
- 2. Save HAR + extracted request/response examples per dispatch (including AJAX).
- 3. Record validation/system error samples (bad date, bad number, forced DB failure) and finalize error model.

## commercial_proposals
- 1. **Route/action chain verified**:
- 2. **DB side-effects verified (code + SQL id level)**:
- 3. **Role restrictions verified**:
- ## UNCONFIRMED (needs runtime HAR)
- 1. Exact wire format per dispatch (full request headers/cookies, hidden fields, pagination params in real browser posts).
- 2. Exact HTML error rendering contract (status code + DOM location + localized message text).
- 3. Exact binary response headers for `generateExcel` in production legacy container.
- ## BLOCKER report (runtime)
- 1. Capture HAR with content for all required dispatches from checklist A.
- 2. Export one raw request + one raw response sample per dispatch into `payloads/`.
- 3. Reconcile parameter names with `CommercialProposalsForm` + SQL placeholders, then mark runtime rows VERIFIED.

## conditionsforcontract
- 1. **Route and dispatch sequence**:
- 2. **DB object mapping (DAO/SQL id level)**:
- 3. **Role/business checks**:
- ## UNCONFIRMED (needs runtime HAR)
- 1. Exact payload contracts for all list/form dispatches in checklist C.
- 2. Exact response wire format for ajax (`ajaxChangeContract`, `ajaxGetReputation`) and print/upload flows.
- 3. Exact validation and system error response model seen by browser.
- ## BLOCKER report (runtime)
- 1. Start legacy stack and capture HAR for checklist C operations.
- 2. Store raw request/response samples under `payloads/` (create folder for this screen if absent).
- 3. Update this file statuses from UNCONFIRMED to VERIFIED per dispatch.

## contract_create
- # contract_create — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## contract_import_cp
- # contract_import_cp — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## contract_spec_create
- # contract_spec_create — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## contractor_create
- # contractor_create — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## contractor_edit
- # contractor_edit — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## contractors
- # contractors — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## contracts
- 1. **Route/action chain**:
- 2. **DAO/SQL trace**:
- 3. **Role restrictions/UI behavior**:
- ## UNCONFIRMED
- 1. Full runtime error wire-format for all failing paths in list+create/edit flows is still partially unconfirmed.
- 2. Attachment/spec edge-case payloads for every sub-dispatch are not fully archived as separate samples.
- ## BLOCKERS
- 1. Extend HAR capture to all D checklist operations (especially restore/back/spec attachment subflows).
- 2. Add per-dispatch request/response samples and error cases into `payloads/`.
- 3. Close remaining UNCONFIRMED points in `CONTRACTS.md` and `BEHAVIOR_MATRIX.md`.

## countries
- # countries — Questions / UNCONFIRMED
- 1. UNCONFIRMED: exact entry Struts mapping and dispatch sequence for this screen.
- 2. UNCONFIRMED: exact DB objects (tables/procedures/triggers) used by this screen.
- 3. UNCONFIRMED: runtime payload/error texts.

## currencies
- # currencies — Questions / UNCONFIRMED
- 1. UNCONFIRMED: exact entry Struts mapping and dispatch sequence for this screen.
- 2. UNCONFIRMED: exact DB objects (tables/procedures/triggers) used by this screen.
- 3. UNCONFIRMED: runtime payload/error texts.

## incotermslist
- # incotermslist — Questions / UNCONFIRMED
- 1. UNCONFIRMED: exact entry Struts mapping and dispatch sequence for this screen.
- 2. UNCONFIRMED: exact DB objects (tables/procedures/triggers) used by this screen.
- 3. UNCONFIRMED: runtime payload/error texts.

## margin
- # margin — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## margindev
- # margindev — Questions / UNCONFIRMED
- 1. UNCONFIRMED: exact entry Struts mapping and dispatch sequence for this screen.
- 2. UNCONFIRMED: exact DB objects (tables/procedures/triggers) used by this screen.
- 3. UNCONFIRMED: runtime payload/error texts.

## order_edit
- # order_edit — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## orders
- # orders — Questions / UNCONFIRMED
- 1. UNCONFIRMED: complete route/action/DAO trace.
- 2. UNCONFIRMED: enforced DB rules for this screen.
- 3. UNCONFIRMED: runtime payloads/errors.

## purchasepurposes
- # purchasepurposes — Questions / UNCONFIRMED
- 1. UNCONFIRMED: exact entry Struts mapping and dispatch sequence for this screen.
- 2. UNCONFIRMED: exact DB objects (tables/procedures/triggers) used by this screen.
- 3. UNCONFIRMED: runtime payload/error texts.

## users
- # users — Questions / UNCONFIRMED
- 1. UNCONFIRMED: exact entry Struts mapping and dispatch sequence for this screen.
- 2. UNCONFIRMED: exact DB objects (tables/procedures/triggers) used by this screen.
- 3. UNCONFIRMED: runtime payload/error texts.

