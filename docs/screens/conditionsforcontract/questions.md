# conditionsforcontract — Questions / Coverage Status

## VERIFIED (static trace)
1. **Route and dispatch sequence**:
   - List endpoint `/ConditionsForContractAction.do`: `input`, `filter`, paging handlers, `markExecute`, `checkPrice`.
   - Form endpoint `/ConditionForContractAction.do`: `input`, `edit`, `clone`, `process`, `processForce`, `reload`, `importExcel`, `uploadTemplate`, `selectCP`, `returnFromSelectCP`, contractor/produce/attach flows, `ajaxChangeContract`, `ajaxGetReputation`.
   - Struts return flow from CP selector verified: `/SelectKPConditionForContractAction.do?dispatch=input` → forward `return` to `/ConditionForContractAction.do?dispatch=returnFromSelectCP`.
2. **DB object mapping (DAO/SQL id level)**:
   - List read: `select-conditions_for_contract`.
   - Save/edit: `condition_for_contract-insert`, `condition_for_contract-update`.
   - Flags: `execute_condition_for_contract`, `condition_for_contract-update-checkPrice`.
   - Produce rows: `delete_condition_for_contract_produces`, `insert_condition_for_contract_produce`, `select-condition_for_contract_produces`.
3. **Role/business checks**:
   - Execute action enabled for admin/lawyer.
   - Check price enabled for admin/economist.
   - Manager edit/clone constrained by same department.
   - `process` has explicit pre-save gate for `cfc_place` transition and show-message behavior.

## UNCONFIRMED (needs runtime HAR)
1. Exact payload contracts for all list/form dispatches in checklist C.
2. Exact response wire format for ajax (`ajaxChangeContract`, `ajaxGetReputation`) and print/upload flows.
3. Exact validation and system error response model seen by browser.

## BLOCKER report (runtime)
- Runtime capture is blocked in current workspace: no runnable packaged legacy webapp/bootstrap for `src/` to execute Struts actions and produce HAR evidence.
- Static trace completed to Struts + Action + DAO + SQL ids; runtime confirmations remain pending.

## How to verify next
1. Start legacy stack and capture HAR for checklist C operations.
2. Store raw request/response samples under `payloads/` (create folder for this screen if absent).
3. Update this file statuses from UNCONFIRMED to VERIFIED per dispatch.
