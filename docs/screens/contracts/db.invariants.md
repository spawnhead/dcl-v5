# contracts — DB invariants (enforced only)

## VERIFIED (code/SQL-id trace)
- Main objects in this screen flow:
  - Contract: `contract-load`, `contract-insert`, `contract-update`, `contract-delete`.
  - Contract list filter: `select-contracts` (`dcl_contract_filter(...)`).
  - Specifications/payments: `select-specifications`, `specification-insert`, `specification-update`, `delete_specifications`, `delete_specification_payments`, `insert_specification_payment`.
- Save flow explicitly commits after specification persistence to avoid deadlocks around attachment-triggered deletes.

## UNCONFIRMED (DDL detail depth)
- This doc does not yet enumerate every FK/check/trigger name from DDL for all dependent tables.

## Verification next step
1. Correlate each SQL id above to exact DDL table/trigger/procedure definitions with line-level references.
2. Record enforced constraints list here and close UNCONFIRMED.
