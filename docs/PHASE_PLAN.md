# Documentation Phase Plan (Production-Ready)

## MVP (must-have for first release)
- commercial_proposals
- commercial_proposal_edit
- conditionsforcontract
- contracts
- contract_create
- contract_import_cp
- contract_spec_create
- contractor_create
- contractor_edit
- contractors
- countries
- currencies
- incotermslist
- purchasepurposes
- users

## Phase 1 (post-MVP business expansion)
- orders
- order_edit
- margin
- margindev

## Phase 2 (remaining legacy scope)
- All archived screen packs under `docs/archive/screens_baseline/`.

## Rules of readiness
For each screen before implementation:
1. No baseline placeholders in `spec.md`.
2. `api.contract.md` has request/response/error models.
3. `db.invariants.md` mapped to exact DB objects.
4. Runtime evidence present (HAR/payload samples) or explicit blocker with verification plan.
5. `questions.md` empty or moved to resolved section.
