# commercial_proposals — DB invariants (enforced only)

## VERIFIED (static)
- Grid/list is backed by SQL id `select-commercial_proposals` calling `dcl_commercial_proposal_filter(...)`.
- State-changing operations:
  - `block` uses `commercial_proposal-update-block` (updates `DCL_COMMERCIAL_PROPOSAL.cpr_block`).
  - `checkPrice` uses `commercial_proposal-update-checkPrice` (sets check-price fields and block marker).

## Table-level constraints (known)
- Core table: `DCL_COMMERCIAL_PROPOSAL` with required fields including `CPR_ID`, `CPR_NUMBER`, `CPR_DATE`, `CTR_ID` and audit columns.
- Trigger set referenced in prior DDL review: `DCL_COMMERCIAL_PROPOSAL_BI0`, `DCL_COMMERCIAL_PROPOSAL_AI0`, `DCL_COMMERCIAL_PROPOSAL_BU0`.
- Procedure references used by this screen: `DCL_COMMERCIAL_PROPOSAL_FILTER`.

## UNCONFIRMED
- Exhaustive FK/check list for all dependent objects remains to be line-correlated from DDL.
