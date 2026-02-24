# conditionsforcontract — DB invariants (enforced only)

## VERIFIED (code/SQL-id trace)
- Main persistence object: `DCL_CONDITION_FOR_CONTRACT`.
- DAO SQL ids used by this screen flow:
  - `condition_for_contract-load`, `condition_for_contract-insert`, `condition_for_contract-update`;
  - `condition_for_contract-update-checkPrice`, `execute_condition_for_contract`;
  - `select-condition_for_contract_produces`, `insert_condition_for_contract_produce`, `delete_condition_for_contract_produces`.
- List query id: `select-conditions_for_contract` (filter-level read object for screen grid).

## UNCONFIRMED (DDL-level completeness)
- Full FK/check/trigger catalog for every dependent table in this flow is not fully enumerated in this doc yet.
- Requires dedicated DDL object pass correlated with every SQL id argument list.

## Verification next step
1. Parse DDL objects for `DCL_CONDITION_FOR_CONTRACT*` and dependent produce/message tables.
2. Attach exact constraint/trigger lines and close UNCONFIRMED rows.
