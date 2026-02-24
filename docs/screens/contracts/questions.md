# contracts — Questions / Coverage Status

## VERIFIED (static + existing runtime evidence)
1. **Route/action chain**:
   - List: `/ContractsAction.do` (`input`, `filter`, paging via `processBefore`, `restore`, `selectCP`).
   - Form: `/ContractAction.do` (`input`, `importCP`, `edit`, `process`, `back`, spec/attach flows).
   - CP import flow verified in Struts: `/SelectCPContractsAction.do?dispatch=input` → forward `return` to `/ContractAction.do?dispatch=importCP`.
2. **DAO/SQL trace**:
   - List read via `select-contracts` (`dcl_contract_filter(...)`).
   - Form load/save via `contract-load`, `contract-insert`, `contract-update`, plus specification SQL (`select-specifications`, `specification-insert/update`, `delete_specifications`, payments SQL).
3. **Role restrictions/UI behavior**:
   - List edit lock for manager outside allowed departments.
   - Form read-only modes by role (manager/lithuania/logistic constraints).
   - `process` returns `back` on success and `show` on validation/save failure.

## UNCONFIRMED
1. Full runtime error wire-format for all failing paths in list+create/edit flows is still partially unconfirmed.
2. Attachment/spec edge-case payloads for every sub-dispatch are not fully archived as separate samples.

## BLOCKERS
- No new blocker for basic contracts list capture (existing `payloads/network.har` already present), but completeness for all scope-D subflows still requires additional targeted captures in a runnable legacy environment.

## How to verify next
1. Extend HAR capture to all D checklist operations (especially restore/back/spec attachment subflows).
2. Add per-dispatch request/response samples and error cases into `payloads/`.
3. Close remaining UNCONFIRMED points in `CONTRACTS.md` and `BEHAVIOR_MATRIX.md`.
