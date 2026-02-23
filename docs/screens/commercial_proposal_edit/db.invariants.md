# commercial_proposal_edit — DB invariants (enforced only)

## VERIFIED (static)
- Save/load lifecycle uses `CommercialProposalDAO` and SQL ids:
  - `commercial_proposal-load`, `commercial_proposal-insert`, `commercial_proposal-update`.
- Dependent produces are loaded/saved through dedicated SQL ids (`select-commercial_proposal_produces` and related produce operations in DAO/action flow).
- Edit form operations ultimately persist into `DCL_COMMERCIAL_PROPOSAL` (+ dependent produce/transport/message data objects).

## Table-level constraints (known)
- `DCL_COMMERCIAL_PROPOSAL` mandatory fields include `CPR_ID`, `CPR_NUMBER`, `CPR_DATE`, `CTR_ID`, audit timestamps/users.
- Trigger names observed in DDL review: `DCL_COMMERCIAL_PROPOSAL_BI0`, `DCL_COMMERCIAL_PROPOSAL_AI0`, `DCL_COMMERCIAL_PROPOSAL_BU0`.

## UNCONFIRMED
- Full FK/check/trigger/procedure list for every dependent table used in edit subflows still requires dedicated DDL cross-reference with line citations.
